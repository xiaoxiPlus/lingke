package com.fh.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author liqiang
 * @since 2020-12-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TSysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    @TableField("userName")
    private String username;

    /**
     * 姓名
     */
    @TableField("realName")
    private String realname;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 身份证号
     */
    @TableField("idCard")
    private String idcard;

    /**
     * 性别1男，2女
     */
    private Integer sex;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 个人头像
     */
    @TableField("imgUrl")
    private String imgurl;

    /**
     * 学历Id
     */
    @TableField("eduId")
    private Integer eduid;

    /**
     * 省编码
     */
    @TableField("provinceCode")
    private String provincecode;

    /**
     * 市编码
     */
    @TableField("cityCode")
    private String citycode;

    /**
     * 县编码
     */
    @TableField("areaCode")
    private String areacode;

    /**
     * 部门Id
     */
    @TableField("deptId")
    private Integer deptid;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private LocalDateTime createtime;

    /**
     * 修改时间
     */
    @TableField("updateTime")
    private LocalDateTime updatetime;

    @TableField("saltCode")
    private String saltcode;


}
