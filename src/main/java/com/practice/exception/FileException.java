package com.practice.exception;

/**
 * about file exception
 */
public class FileException extends RuntimeException {

    private String msg;

    public FileException(String msg){
        super(msg);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
