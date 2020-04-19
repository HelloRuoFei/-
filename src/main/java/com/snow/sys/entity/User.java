package com.snow.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author snow
 * @since 2019-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String loginname;

    private String address;

    private Integer sex;

    private String remark;

    private String pwd;

    private Integer deptid;

    private Date hiredate;

    private Integer mgr;//直接领导id

    private Integer available;

    private Integer ordernum;

    /**
     * 用户类型[0超级管理员1，管理员，2普通用户]
     */
    private Integer type;

    /**
     * 头像地址
     */
    private String imgpath;

    private String salt;
    
    @TableField(exist=false)
    private String leadername;//领导名称
    
    @TableField(exist=false)
    private String deptname;//部门名称
}
