package com.snow.bus.vo;




import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.snow.bus.entity.Inport;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 
 * 主要负责接收客户页面传过来的数据*/
@Data
@EqualsAndHashCode(callSuper=false)
public class InportVo  extends  Inport{
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
 }
