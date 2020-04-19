package com.snow.sys.vo;

import java.util.Date;


import org.springframework.format.annotation.DateTimeFormat;

import com.snow.sys.entity.Notice;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 
 * 主要负责接收日志页面传过来的数据*/
@Data
@EqualsAndHashCode(callSuper=false)
public class NoticeVo  extends Notice{
	private static final long serialVersionUID = 1L;
	//分页的初始值
	private Integer page = 1;
	private Integer limit = 10;
	//开始时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	//结束时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	//接收多个id用于批量、删除
	private Integer[] ids;
 }
