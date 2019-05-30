package com.shibei.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ClassName ResultInfo
 * @Description
 * @Author zwj
 * @Date 2019-01-24 08:53
 * @Version 1.0
 */
@Getter
@Setter
@ToString
public class ResultInfo {
    private Integer code;
    private String msg;
    private Object data;

    public ResultInfo(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultInfo(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultInfo() {
    }

    public static  ResultInfo build() {
        return new ResultInfo();
    }

    public static ResultInfo build(int code, String msg) {
        return new ResultInfo(code, msg);
    }

    public static  ResultInfo build(int code, String msg, Object data) {
        return new ResultInfo(code, msg, data);
    }
}
