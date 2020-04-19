package com.snow.bus.vo;




import com.snow.bus.entity.Customer;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 
 * 主要负责接收客户页面传过来的数据*/
@Data
@EqualsAndHashCode(callSuper=false)
public class CustomerVo  extends Customer{
	private static final long serialVersionUID = 1L;
	//分页的初始值
	private Integer page = 1;
	private Integer limit = 10;
	//接收多个id用于批量、删除
	private Integer[] ids;
 }
