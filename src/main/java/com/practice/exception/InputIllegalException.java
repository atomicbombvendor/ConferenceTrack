package com.practice.exception;

/**
 * 输入数据不合法
 */
public class InputIllegalException extends RuntimeException {

    private String msg;

    public InputIllegalException(String msg){
        super(msg);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
