package com.practice.exception;

/**
 * illegal file content
 * @author haoyue
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
