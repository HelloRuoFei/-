package com.snow.sys.controller;


import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.snow.sys.common.ActiverUser;
import com.snow.sys.common.ResultObj;
import com.snow.sys.common.WebUtils;
import com.snow.sys.entity.Loginfo;
import com.snow.sys.service.LoginfoService;

/**
 * <p>
 *登录  前端控制器
 * </p>
 *
 * @author snow
 * @since 2019-12-08
 */
@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private LoginfoService loginfoService;
	
	@RequestMapping("login")
	public ResultObj login(String loginname,String pwd) {
		//获取subject
		Subject subject = SecurityUtils.getSubject();
		//通过传进来的用户名和密码获取token
		AuthenticationToken token = new UsernamePasswordToken(loginname, pwd);
		try {
			//拿着这个token去判断
			subject.login(token);
			ActiverUser activeUser = (ActiverUser) subject.getPrincipal();//拿到activerUser对象
			WebUtils.getSession().setAttribute("user", activeUser.getUser());//从activerUser对象拿到user对象并存储起来
			
			//登录成功记录日志
			Loginfo entity = new Loginfo();//实例化日志
			entity.setLoginname(activeUser.getUser().getName()+"-"+activeUser.getUser().getLoginname());//获取名字
			entity.setLoginip(WebUtils.getRequest().getRemoteAddr());//通过Request获取ip
			entity.setLogintime(new Date());//获取当前时间啊
			loginfoService.save(entity);//保存这个记录
			
			return ResultObj.LOGIN_SUCCESS;//成功就返回这个信息
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return ResultObj.LOGIN_ERROR_PASS;//失败就返回这个信息
		}
	}
}

