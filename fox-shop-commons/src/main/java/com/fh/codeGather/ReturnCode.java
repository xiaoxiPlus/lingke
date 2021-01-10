package com.fh.codeGather;

public enum ReturnCode {
    SUCCESS(200,"操作成功"),
    ERROR(500,"操作失败"),
    USERNAME_PASSWORD_NULL(5001,"用户名或密码不能为空"),
    USER_ISNULL(5002,"用户不存在"),
    USER_PASSWORD_ERROR(5003,"密码有误"),
    SYSTEM_ERROR(5004,"代码执行异常，请联系管理员"),
    LEAVE_TIME_ERROR(5005,"请假时间选择有误"),
    LOGIN_DISABLED(5100,"登陆失效，请重新登陆"),
    NO_PERMISSION(403,"暂时没有该权限"),
    ;
    private Integer code;
    private String msg;

     ReturnCode(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
