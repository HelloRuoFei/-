package com.snow.sys.vo;


import com.snow.sys.entity.Role;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 
 * 主要负责接收日志页面传过来的数据*/
@Data
@EqualsAndHashCode(callSuper=false)
public class RoleVo  extends Role{
	private static final long serialVersionUID = 1L;
	//分页的初始值
	private Integer page = 1;
	private Integer limit = 10;
 }
