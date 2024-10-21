package com.iweb.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iweb.config.Result;
import com.iweb.entity.AccountionRecord;
import com.iweb.entity.ListUserPageReq;
import com.iweb.service.AccountionRecordService;
import com.iweb.vo.AccountionRecordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @date 2024/10/18 12:45
 */
@RestController
@RequestMapping("accountion/record")
public class AccountionRecordController {

    @Autowired
    private AccountionRecordService accountionRecordService;


    /**
     * 数据列表
     * @param listUserPageReq
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    public Result accountionRecordList(ListUserPageReq listUserPageReq,
                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        return Result.success(accountionRecordService.accountionRecordList(pageNum,pageSize,listUserPageReq));
    }


    /**
     * 数据导出
     */
    @GetMapping("export")
    public void export( @RequestParam(required = false) Integer type,
                        @RequestParam(required = false) String remark,
                        HttpServletResponse response) throws Exception{
        String fileName = URLEncoder.encode("记账数据导出", String.valueOf(StandardCharsets.UTF_8)).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        ListUserPageReq listUserPageReq = new ListUserPageReq();
        listUserPageReq.setType(type);
        listUserPageReq.setRemark(remark);
        Page<AccountionRecord> accountionRecordPage = accountionRecordService.accountionRecordList(1, 999999, listUserPageReq);
        List<AccountionRecordVo> accountionRecordVoList = accountionRecordPage.getRecords().stream()
                .map(item -> {
                    AccountionRecordVo accountionRecordVo = new AccountionRecordVo();
                    accountionRecordVo.setType(item.getType()==1?"支出":"收入");
                    accountionRecordVo.setRemark(item.getRemark());
                    accountionRecordVo.setPrice(item.getPrice());
                    accountionRecordVo.setUserName(item.getUserName());
                    accountionRecordVo.setClassifyName(item.getClassifyName());
                    accountionRecordVo.setCreateTime(item.getCreateTime());
                    return accountionRecordVo;
                }).collect(Collectors.toList());
        List<AccountionRecord> records = accountionRecordPage.getRecords();
        EasyExcel.write(response.getOutputStream(), AccountionRecordVo.class)
                .sheet("sheet")
                .doWrite(accountionRecordVoList);
    }

    /**
     * 数据恢复列表
     * @param listUserPageReq
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/del/list")
    public Result delList(ListUserPageReq listUserPageReq,
                                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                       @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        return Result.success(accountionRecordService.delList(pageNum,pageSize,listUserPageReq));
    }

    /**
     * 数据恢复
     * @param id
     * @return
     */
    @GetMapping("/restore")
    public Result restore(@RequestParam Integer id){
        return Result.success(accountionRecordService.restore(id));
    }


}
