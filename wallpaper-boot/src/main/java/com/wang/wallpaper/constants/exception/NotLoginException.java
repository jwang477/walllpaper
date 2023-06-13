package com.wang.wallpaper.constants.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jian.wang@zone-cloud.com
 * @date 2022-10-10 15:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotLoginException extends RuntimeException {

    private Integer code;

    private String message;

}
