package com.controller;

import java.io.Serializable;

public class Result implements Serializable {

    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     *
     * @since Ver 1.1
     */

    private static final long serialVersionUID = 1L;
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
