package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.service.SetmealService;
import com.atguigu.utils.QiniuUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    SetmealService setmealService;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("multipartFile") MultipartFile multipartFile){//使用springMVC上传文件，非File，参数名不为类名小写时需起别名
        try {
            String originalFilename = multipartFile.getOriginalFilename();//原始文件名称
            QiniuUtils.upload2Qiniu(multipartFile.getBytes(),originalFilename);//将上传的文件变为字节，上传的文件名
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,originalFilename);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

}
