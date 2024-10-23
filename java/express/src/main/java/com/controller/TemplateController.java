package com.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.config.CommonSheetWriteHandler;
import com.entity.AccountionRecord;
import com.service.RabbitMqSender;
import com.service.UserService;
import com.utils.R;
import com.vo.ImportDataVo;
import com.vo.TemplateVo;
import com.entity.Classify;
import com.service.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @date 2024/10/23 17:21
 *
 */


@RestController
public class TemplateController {


    @Autowired
    private RabbitMqSender rabbitMqSender;
    @Autowired
    private UserService userService;


    @Autowired
    private ClassifyService classifyService;
    @GetMapping("/downloadTemplate")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        try {
            String fileName = URLEncoder.encode("记账导入模板", String.valueOf(StandardCharsets.UTF_8)).replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            //获取分类
            List<Classify> list = classifyService.lambdaQuery().eq(Classify::getDelete,0).list();
            //转成指定结构
            List<String> idNameList = list.stream()
                    .map(classify -> classify.getId() + "-" + classify.getName())
                    .collect(Collectors.toList());
            //创建一个选择框集合
            Map<Integer, String[]> mapDropDown = new HashMap<>(1);
            //放进去
            mapDropDown.put(1,idNameList.toArray(new String[0]));
            EasyExcel.write(response.getOutputStream(), TemplateVo.class)
                    .head(TemplateVo.class)
                    .registerWriteHandler(new CommonSheetWriteHandler(mapDropDown))
                    .sheet("记账导入模板")
                    .doWrite(new ArrayList<>());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/importData")
    public R importData(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return R.error("上传文件为空");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            List<ImportDataVo> dataList = EasyExcel.read(file.getInputStream())
                    .head(ImportDataVo.class)
                    .sheet()
                    .doReadSync();
            if(CollectionUtils.isEmpty(dataList)){
                return R.error("表格中数据不能为空");
            }
            List<AccountionRecord> accountionRecordList = new ArrayList<>();
            int i = 0;
            for (ImportDataVo importDataVo : dataList) {
                i++;
                if(importDataVo.getPrice() == null){
                    return R.error("第"+i+"行数据金额为空");
                }
                if(StringUtils.isEmpty(importDataVo.getDate())){
                    return R.error("第"+i+"行数据日期为空");
                }
                try {
                    Date parse = sdf.parse(importDataVo.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                    return R.error("第"+i+"行数据日期格式错误，格式应为 年-月-日");
                }
                if(StringUtils.isEmpty(importDataVo.getCategory())){
                    return R.error("第"+i+"行数据分类为空");
                }
                String[] split = importDataVo.getCategory().split("-");
                AccountionRecord accountionRecord = new AccountionRecord();
                accountionRecord.setTypeName(split[1]);
                try {
                    accountionRecord.setTime(sdf.parse(importDataVo.getDate()));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                accountionRecord.setPrice(importDataVo.getPrice());
                accountionRecord.setRemark(importDataVo.getRemark());
                accountionRecord.setUserId(userService.getLoginUser().getId());
                accountionRecordList.add(accountionRecord);
            }
            rabbitMqSender.send("testExchange","importData", JSON.toJSONString(accountionRecordList));
            return R.ok("文件上传并处理成功");
        } catch (IOException e) {
            e.printStackTrace();
            return R.error("文件处理失败: " + e.getMessage());
        }


    }


}
