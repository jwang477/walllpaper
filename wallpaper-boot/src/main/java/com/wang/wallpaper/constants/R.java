package com.wang.wallpaper.constants;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.apache.http.HttpStatus;

import java.util.List;

@Data
public class R<T> {

    private Integer code;

    private T data;

    private String msg;

    private PageInfo pageInfo;

    public R() {
        this.code = 200;
        this.msg = "success";
    }

    public static R error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.setMsg(msg);
        return r;
    }

    public static <T>R success(T data) {
        R<T> r = new R();
        r.setData(data);
        return r;
    }

    public static R ok() {
        return new R();
    }

    public static R Page(Page page) {
        R r = new R<>();
        PageInfo pageInfo = new PageInfo();
        pageInfo.setData(page.getRecords());
        pageInfo.setPageNum(page.getCurrent());
        pageInfo.setPageSize(page.getSize());
        pageInfo.setTotalPage(page.getPages());
        pageInfo.setTotalCount(page.getTotal());
        r.setPageInfo(pageInfo);
        return r;
    }

    @Data
    public static class PageInfo{

        private List data;

        private long pageSize;

        private long pageNum;

        private long totalCount;

        private long totalPage;
    }

}
