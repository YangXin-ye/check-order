package com.iweb.controller;

import com.iweb.config.Result;
import com.iweb.utils.AliOSSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
public class UploadController {

    @PostMapping("/upload")
    public Result upload(@RequestBody MultipartFile file) throws IOException {
        String username = "";
        log.info("文件上传" + username +  file);
        AliOSSUtils aliOSSUtils = new AliOSSUtils();

        String url = aliOSSUtils.upload(file);
        log.info("文件上传成功" + file);
        return Result.success(url);
    }

}