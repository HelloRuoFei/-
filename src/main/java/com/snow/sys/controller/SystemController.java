package com.snow.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/*
 * 
 * 只负责处理请求进行页面跳转，具体的业务有各自的业务Controller来进行查找和处理数据进行发送
 * 
 * */
@Controller
@RequestMapping("/sys")
public class SystemController {

	/*
	 * 
	 * 跳转到登录页面
	 * */
	@RequestMapping("toLogin")
	public String toLogin() {
		
		return "system/index/login";
	}
	
	/*
	 * 
	 * 跳转到首页
	 * */
	@RequestMapping("index")
	public String index() {
		
		return "system/index/index";
	}
	
	/*
	 * 跳转到工作台
	 * 
	 * */
	@RequestMapping("toDeskManager")
	public String toDeskManager() {
		
		return "system/index/deskManager";
	}
	
	/*
	 * 跳转到日志管理
	 * 
	 * */
	@RequestMapping("toLoginfoManager")
	public String toLoginfoManager() {
		
		return "system/loginfo/loginfoManager";
	}
	
	/*
	 * 跳转到系统公告管理
	 * 
	 * */
	@RequestMapping("toNoticeManager")
	public String toNoticeManager() {
		
		return "system/notice/noticeManager";
	}
	
	/*
	 * 
	 * 跳转到部门管理页面
	 * 
	 * */
	@RequestMapping("toDeptManager")
	public String toDeptManager() {
		
		return "system/dept/deptManager";
	}

	/*
	 * 
	 * 跳转到部门管理页面---left
	 * 
	 * */
	@RequestMapping("toDeptLeft")
	public String toDeptLeft() {
		
		return "system/dept/deptLeft";
	}
	/*
	 * 
	 * 跳转到部门管理页面---Rigth
	 * 
	 * */
	@RequestMapping("toDeptRight")
	public String toDeptRigth() {
		
		return "system/dept/deptRight";
	}
	
	/*
	 * 
	 * 跳转到菜单管理页面
	 * 
	 * */
	@RequestMapping("toMenuManager")
	public String toMenuManager() {
		
		return "system/menu/menuManager";
	}

	/*
	 * 
	 * 跳转到菜单管理页面---left
	 * 
	 * */
	@RequestMapping("toMenuLeft")
	public String toMenuLeft() {
		
		return "system/menu/menuLeft";
	}
	/*
	 * 
	 * 跳转到菜单管理页面---Rigth
	 * 
	 * */
	@RequestMapping("toMenuRight")
	public String toMenuRigth() {
		
		return "system/menu/menuRight";
	}
	
	/*
	 * 
	 * 跳转到权限管理页面
	 * 
	 * */
	@RequestMapping("toPermissionManager")
	public String toPermissionManager() {
		
		return "system/permission/permissionManager";
	}

	/*
	 * 
	 * 跳转到权限管理页面---left
	 * 
	 * */
	@RequestMapping("toPermissionLeft")
	public String toPermissionLeft() {
		
		return "system/permission/permissionLeft";
	}
	/*
	 * 
	 * 跳转到权限管理页面---Rigth
	 * 
	 * */
	@RequestMapping("toPermissionRight")
	public String toPermissionRight() {
		
		return "system/permission/permissionRight";
	}
	
	/*
	 * 
	 * 跳转到角色管理页面
	 * 
	 * */
	@RequestMapping("toRoleManager")
	public String toRoleManager() {
		
		return "system/role/roleManager";
	}
	
	/*
	 * 
	 * 跳转到用户管理页面
	 * 
	 * */
	@RequestMapping("toUserManager")
	public String toUserManager() {
		
		return "system/user/userManager";
	}
}
