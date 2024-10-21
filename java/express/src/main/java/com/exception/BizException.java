package com.exception;

/**
 * 业务异常
 *
 * @author larry
 */
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String message;

    private Object data;

    public BizException(String message) {
        this.message = message;
    }
    public BizException(Integer code,String message) {
        this.message = message;
    }


    public BizException(String message, Object data) {
        this.data = data;
        this.message = message;
    }

    public BizException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public BizException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }
}
