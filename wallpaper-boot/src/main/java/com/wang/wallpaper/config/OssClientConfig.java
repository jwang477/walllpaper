package com.wang.wallpaper.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author jian.wang@zone-cloud.com
 * @date 2022-10-10 9:41
 */
@Configuration
public class OssClientConfig {


    @Value("${com.wang.endpoint}")
    private String ENDPOINT;

    @Value("${com.wang.accessKeyId}")
    private String ACCESS_KEY_ID;

    @Value("${com.wang.accessKeySecret}")
    private String ACCESS_KEY_SECRET;


    @Bean
    public OSS oss() {

        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        return ossClient;
    }
}
