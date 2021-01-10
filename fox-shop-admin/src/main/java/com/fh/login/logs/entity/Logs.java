package com.fh.login.logs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Document("logs")
@AllArgsConstructor
@NoArgsConstructor
public class Logs {
    @Id
    private String id;
    private String realName;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date CreateTime;
    private Integer type;
    private String className;
    private String methodName;
    private String args;
    private Integer runTime;
    private String describeInfo;
    private String errorInfo;
    private String operator;
}
