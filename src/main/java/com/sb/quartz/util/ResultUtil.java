package com.sb.quartz.util;

import java.io.Serializable;

/**
 * @version 1.0
 * @description：
 * @author: Yang.Chang
 * @project: cloud
 * @package: com.spring.cloud.common.vo、
 * @email: cy880708@163.com
 * @date: 2018/11/22 下午5:22
 * @mofified By:
 */
public class ResultUtil<T> implements Serializable {

    private String code;
    private String message;
    private T data;

    public String getCode() {
        return code;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResultUtil(Builder<T> builder) {
        this.code = builder.code;
        this.message = builder.message;
        this.data = builder.data;
    }

    public static class Builder<T>{
        private String code;
        private String message;
        private T data;


        public Builder code(String code){
            this.code = code;
            return this;
        }

        public Builder success(){
            this.code = ResultCode.SUCCESS.getCode();
            this.message = "SUCCESS";
            return this;
        }
        public Builder success(T data){
            this.code = ResultCode.SUCCESS.getCode();
            this.message = "SUCCESS";
            this.data = data;
            return this;
        }
        public Builder failure(T data){
            this.code = ResultCode.FAIL.getCode();
            this.message = "正在加载数据！";
            this.data = data;
            return this;
        }
        public Builder success(String msg,T data){
            this.code = ResultCode.SUCCESS.getCode();
            this.message = msg;
            this.data = data;
            return this;
        }
        public Builder failure(){
            this.code = ResultCode.FAIL.getCode();
            this.message = "正在加载数据！";
            return this;
        }
        public Builder failure(String msg){
            this.code = ResultCode.FAIL.getCode();
            this.message = msg;
            return this;
        }
        public Builder failure(String msg, String code){
            this.code = code;
            this.message = msg;
            return this;
        }
        public Builder failure(String msg, String code,T data){
            this.code = code;
            this.message = msg;
            this.data = data;
            return this;
        }

        public ResultUtil build() {
            return new ResultUtil(this);
        }
    }

}
