package com.wang.wallpaper.web.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.TextUtils;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author: xiWen
 * @Date: 2021-11-11
 * @description:  日志
 */
@Component
@Slf4j
public class LogFilter extends OncePerRequestFilter {

    private final static String SESSION_TOKEN_KEY = "T";
    public static final String PARM_NAME_DATA = "data";
    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        MDC.put(SESSION_TOKEN_KEY, generateSimpleId());

        BodyReaderHttpServletRequestWrapper requestWrapper = new BodyReaderHttpServletRequestWrapper(servletRequest);

        String method = requestWrapper.getMethod();
        String requestURI = requestWrapper.getRequestURI();
        String dataParam = Optional.ofNullable(requestWrapper.getParameter(PARM_NAME_DATA)).orElse("-");
        String requestParam = readParam(requestWrapper);
        String requestBody = deleteSysChar(readBody(requestWrapper));

        log.info("********************************{}**********************************","请求进来啦");
        Enumeration<String> header =   requestWrapper.getHeaderNames();
        while (header.hasMoreElements()){
            String headername = header.nextElement();
            log.info("{}:{}",headername,requestWrapper.getHeader(headername));
        }
        log.info("method:{} url:{} dataParam:{} requestParam:{} body:{}", method, requestURI, dataParam, requestParam, requestBody);

        filterChain.doFilter(requestWrapper, servletResponse);

        long endTime = System.currentTimeMillis();
        long execteTime = endTime - startTime;


        log.info("{} {} {}ms ", method, requestURI, execteTime );
        log.info("********************************{}**********************************","请求结束啦");

        MDC.remove(SESSION_TOKEN_KEY);
    }

    @Override
    public void destroy() {

    }
    public static String deleteSysChar(String str){
        if(TextUtils.isBlank(str)){
            return "";
        }
        return str.replaceAll("\\s", "");
    }
    public static String generateSimpleId(){
        int hashCode = UUID.randomUUID().toString().hashCode();
        if(hashCode < 0){
            hashCode = -hashCode;
        }
        return String.format("%d", hashCode);
    }
    private String getCookieValue(HttpServletRequest request, String cookieName){
        Cookie[] cookies = request.getCookies();
        if(null == cookies){
            return null;
        }
        for(Cookie cookie: cookies){
            if(cookieName.equalsIgnoreCase(cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;
    }

    private String readParam(HttpServletRequest request) {
        Map<String,Object> paramMap = new HashMap<>();
        if(null !=  request.getParameterMap()) {
            for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
                if(!entry.getKey().equalsIgnoreCase(PARM_NAME_DATA) && null != entry.getValue()){
                    paramMap.put(entry.getKey(), entry.getValue().length == 1 ? entry.getValue()[0] : entry.getValue());
                }
            }
        }
        return paramMap.isEmpty() ? "-" :  JSON.toJSONString(paramMap);
    }

    private String readBody(HttpServletRequest request) throws IOException {
        if(request.getContentLength() > 0) {
            if (request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE) || request.getContentType().contains(MediaType.TEXT_XML_VALUE)) {
                return StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
            }
        }
        return "-";
    }

    public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {
        private final byte[] bodyCopier;

        public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
            super(request);
            request.getParameterMap();
            bodyCopier = StreamUtils.copyToByteArray(request.getInputStream());
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(this.getInputStream()));
        }

        @Override
        public ServletInputStream getInputStream() throws IOException{
            return new ServletInputStreamCopier(bodyCopier);
        }

        public byte[] getCopy() {
            return this.bodyCopier;
        }

        public String getBody() throws UnsupportedEncodingException {
            return new String(this.bodyCopier, StandardCharsets.UTF_8);
        }

        public  class ServletInputStreamCopier extends ServletInputStream {
            private ByteArrayInputStream bais;

            public ServletInputStreamCopier(byte[] in) {
                this.bais = new ByteArrayInputStream(in);
            }

            @Override
            public boolean isFinished() {
                return bais.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                throw new RuntimeException("Not implemented");
            }

            @Override
            public int read() throws IOException {
                return this.bais.read();
            }
        }
    }
}
