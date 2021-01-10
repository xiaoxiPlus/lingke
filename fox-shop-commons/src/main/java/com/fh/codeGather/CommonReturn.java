package com.fh.codeGather;

import io.swagger.annotations.ApiModelProperty;

public class CommonReturn {
    @ApiModelProperty("状态码")
    private Integer code;
    @ApiModelProperty("状态信息")
    private String msg;
    @ApiModelProperty("返回数据")
    private Object data;

    /*成功状态*/
    public static CommonReturn success() {
        return new CommonReturn(ReturnCode.SUCCESS.getCode(), ReturnCode.SUCCESS.getMsg());
    }
    public static CommonReturn success(Object data) {
        return new CommonReturn(ReturnCode.SUCCESS.getCode(), ReturnCode.SUCCESS.getMsg(),data);
    }
    public static CommonReturn success(ReturnCode returnCode) {
        return new CommonReturn(returnCode.getCode(), returnCode.getMsg());
    }
    public static CommonReturn success(ReturnCode returnCode,Object data) {
        return new CommonReturn(returnCode.getCode(),returnCode.getMsg(),data);
    }


    /*失败状态*/
    public static CommonReturn error() {
        return new CommonReturn(ReturnCode.ERROR.getCode(), ReturnCode.ERROR.getMsg());
    }
    public static CommonReturn error(Object data) {
        return new CommonReturn(ReturnCode.ERROR.getCode(), ReturnCode.ERROR.getMsg(),data);
    }
    public static CommonReturn error(ReturnCode returnCode) {
        return new CommonReturn(returnCode.getCode(), returnCode.getMsg());
    }
    public static CommonReturn error(ReturnCode returnCode,Object data) {
        return new CommonReturn(returnCode.getCode(), returnCode.getMsg(),data);
    }



    private CommonReturn(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /*两个有参构造，两种赋值形式*/
    private CommonReturn(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private CommonReturn() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
