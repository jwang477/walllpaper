package com.wang.wallpaper.web.controller;

import com.wang.wallpaper.component.OssUpload;
import com.wang.wallpaper.constants.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author jian.wang@zone-cloud.com
 * @date 2022-10-10 10:02
 */
@Controller
@RequestMapping("file")
public class FileController extends BaseController{

    @Autowired
    private OssUpload cosUpload;

    @Value("${com.wang.baseUrl}")
    private String BASE_URL;

    @PostMapping("upload")
    @ResponseBody
    public R<String> upload(MultipartFile file) throws IOException {

        cosUpload.upload(file.getInputStream(),file.getOriginalFilename());

        return R.success(BASE_URL+file.getOriginalFilename());
    }
}
