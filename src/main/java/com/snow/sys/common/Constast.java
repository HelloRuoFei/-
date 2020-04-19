package com.snow.sys.common;



/**
 * 常量类
 * 
 * */
public interface Constast {

	
	/*
	 * 状态码
	 * 
	 * */
	public static final Integer OK = 200;
	public static final Integer ERROR = -1;
	
	/*
	 * 
	 * 菜单权限类型
	 * */
	public static final String TYPE_MNEU = "menu";
	public static final String TYPE_PERMISSION = "permission";
	
	/*
	 * 可用状态
	 * 
	 * */
	public static final Object AVAILABLE_TRUE = 1;
	public static final Object AVAILABLE_FALSE = 0;
	
	/*
	 * 用户类型
	 * 
	 * */
	public static final Integer USER_TYPE_SUPER = 0;
	public static final Integer USER_TYPE_NORMAL = 1;
	
	/**
	 * 
	 * 用户默认密码
	 * 
	 * */
	public static final String USER_DEFAULT_PWD = "123456";
	
	/*
	 * 
	 * 展开类型
	 * */
	public static final Integer OPEN_TRUE = 1;
	public static final Integer OPEN_FALSE = 1;
	
	/*
	 * 
	 * 默认商品图片路径
	 * */
	public static final String IMG_GOODS_PNG = "2019-12-29/A42773497F0D4DA596040B2975E4E020.gif";
}
