package com.iweb.config;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author 
 * @Create 2024-06-07-14:10
 * @Message
 **/


/**
 * 返回结果
 **/
@Data
@NoArgsConstructor
public class Result implements Serializable {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 数据
     */
    private Object data;

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    /**
     * 带消息成功返回
     */
    public static Result successWithMsg(String msg) {
        return new Result(ResultCode.SUCCESS.getCode(), msg, null);
    }

    /**
     * 带数据成功返回
     */
    public static Result success(Object data) {
        return new Result(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 不带数据成功返回
     */
    public static Result success() {
        return new Result(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    /**
     * 错误返回，指定错误消息
     */
    public static Result failed(String msg) {
        return new Result(ResultCode.FAILED.getCode(), msg, null);
    }

    /**
     * 错误返回，指定错误消息
     */
    public static Result failed(Integer code, String msg) {
        return new Result(code, msg, null);
    }

    /**
     * 错误返回，默认错误消息
     */
    public static Result failed() {
        return failed(ResultCode.FAILED.getMessage());
    }


    public static Result failed(Integer code, String msg, Object data) {
        return new Result(code, msg, data);
    }

    /**
     * 没有权限
     */
    public static Result noAuth() {
        return new Result(ResultCode.NO_AUTH.getCode(), ResultCode.NO_AUTH.getMessage(), null);
    }

    /**
     * 没有权限
     */
    public static Result noAuth(String data) {
        return new Result(ResultCode.NO_AUTH.getCode(), ResultCode.NO_AUTH.getMessage(), data);
    }



    /**
     * token失效
     */
    public static Result tokenExpired() {
        return new Result(ResultCode.TOKEN_EXPIRE.getCode(), ResultCode.TOKEN_EXPIRE.getMessage(), null);
    }

    /**
     * token失效  自定义消息
     */
    public static Result tokenExpired(String msg) {
        return new Result(ResultCode.TOKEN_EXPIRE.getCode(), msg, null);
    }

}

