package com.wang.wallpaper.web.controller;

import com.wang.wallpaper.constants.exception.NotLoginException;
import com.wang.wallpaper.constants.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author jian.wang@zone-cloud.com
 * @date 2022-09-28 14:06
 */
@Slf4j
public class BaseController {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R ExceptionHandler(Exception ex) {

        log.error("ExceptionHandler", ex);
        return R.error(ex.getMessage());
    }

    @ExceptionHandler(NotLoginException.class)
    @ResponseBody
    public R ExceptionHandler(NotLoginException ex) {

        return R.error(ex.getCode(), ex.getMessage());
    }
}
