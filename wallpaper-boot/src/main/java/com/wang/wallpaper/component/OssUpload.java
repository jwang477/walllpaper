package com.wang.wallpaper.component;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * @author jian.wang@zone-cloud.com
 * @date 2022-10-10 9:48
 */
@Component
@Slf4j
public class OssUpload {

    @Value("${com.wang.bucketName}")
    private String BUCKET_NAME;

    @Autowired
    private OSS ossClient;

    public void upload(InputStream ins, String fileName) {
        //设置objectMetadata  ContentType
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(getContentType(fileName.substring(fileName.lastIndexOf("."))));
        try {
            // 创建PutObject请求。
            ossClient.putObject(BUCKET_NAME, fileName, ins, objectMetadata);
        } catch (OSSException oe) {
            log.error("Error Message:{}\n,Error Code:{}\n,Request ID:{}\n,Host ID:{}", oe.getErrorMessage(), oe.getErrorCode(), oe.getRequestId(), oe.getHostId());
        } catch (ClientException ce) {
            log.error("Error Message:" + ce.getMessage());
        }
    }


    private static String getContentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        if (FilenameExtension.equalsIgnoreCase(".pdf")) {
            return "application/pdf";
        }

        return "image/jpeg";
    }


}
