package com.sb.quartz.util;

/**
 * @version 1.0
 * @description：
 * @author: Yang.Chang
 * @project: cloud
 * @package: com.spring.cloud.common.vo、
 * @email: cy880708@163.com
 * @date: 2018/11/22 下午5:25
 * @mofified By:
 */
public enum ResultCode {

    // 成功
    SUCCESS("200"),
    // 失败
    FAIL("400"),
    // 页面走丢了
    INTERNAL_SERVER_ERROR("500"),
    // 无效果的Sgin
    INVALID_SIGN("403", "参数签名无效");

    private String code;
    private String message;

    ResultCode(String code) {
        this.code = code;
    }

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }
}
