package com.controller;

import com.entity.CommonFile;
import com.exception.BizException;
import com.service.CommonFileService;
import com.utils.R;
import com.utils.SnowflakeUtil;
import com.vo.FileUploadVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * @Author：
 * @Package：com.controller
 * @Project：transport_system
 * @name：FileController
 * @Date：2024/9/29 17:02
 * @Description:
 */
@RequestMapping("/file")
@Controller
public class FileController {


    public static final String FILE_PATH = "";

    @Autowired
    private CommonFileService commonFileService;

    @PostMapping("/upload")
    @ResponseBody
    public R uploadFile(@RequestParam("file") MultipartFile file) {
        if(file == null){
            throw new BizException("上传文件不能为空");
        }
        try {
            // 获取原始文件名
            String originalFilename = file.getOriginalFilename();
            // 文件保存路径
            String filePath = FILE_PATH + UUID.randomUUID().toString().replace("-","") + "-" + originalFilename;
            // 保存文件到服务器
            file.transferTo(new File(filePath));
            //存储文件数据
            CommonFile commonFile = new CommonFile();
            commonFile.setFileName(originalFilename);
            commonFile.setFileUrl(filePath);
            long uniqueId = SnowflakeUtil.getUniqueId();
            commonFile.setId(uniqueId);
            commonFileService.save(commonFile);
            //创建返回对象
            FileUploadVo fileUploadVo = new FileUploadVo();
            fileUploadVo.setFileId(uniqueId);
            return R.ok().put("data",fileUploadVo);
        } catch (Exception e) {
           e.printStackTrace();
           throw new BizException("上传文件异常");
        }
    }

    @GetMapping("/download")
    public void downloadFile(@NotEmpty(message = "文件Id不能为空") @RequestParam String fileId, HttpServletResponse response) {
        //根据文件Id查询文件
        CommonFile commonFile = commonFileService.getById(fileId);
        if(commonFile == null){
            throw new BizException("文件Id不存在");
        }
        File file = new File(commonFile.getFileUrl());

        // 检查文件是否存在
        if (!file.exists()) {
            throw new BizException("文件不存在");
        }
        String encodedFileName = commonFile.getFileName();
        try {
            encodedFileName = URLEncoder.encode(commonFile.getFileName(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 设置响应头，指定文件名为attachment下载
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFileName);

        // 写入文件流到响应
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytesRead);
            }
            response.flushBuffer();
        } catch (Exception e){
            e.printStackTrace();
            throw new BizException("获取文件异常");
        }
    }
}
