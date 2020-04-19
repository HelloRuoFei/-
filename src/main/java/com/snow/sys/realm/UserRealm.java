package com.snow.sys.realm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snow.sys.common.ActiverUser;
import com.snow.sys.common.Constast;
import com.snow.sys.entity.Permission;
import com.snow.sys.entity.User;
import com.snow.sys.service.PermissionService;
import com.snow.sys.service.RoleService;
import com.snow.sys.service.UserService;

public class UserRealm extends AuthorizingRealm {
	
	@Autowired
	@Lazy   
	//因为类加载顺序的问题，这个类是先加载进容器的同时因为使用的@Autowired也把UserService加进了容器  在做切面的时候发生了问题，拿不到 UserService的代理对象，只能拿到UserService，所以这里使用懒加载的方式
	private UserService userService;
	
	//下面两个也加上@Lazy，以后要是需要既可以使用
	@Autowired
	@Lazy
	//因为类加载顺序的问题，这个类是先加载进容器的同时因为使用的@Autowired也把UserService加进了容器  在做切面的时候发生了问题，拿不到 UserService的代理对象，只能拿到UserService，所以这里使用懒加载的方式
	private PermissionService permissionService;
	
	@Autowired
	@Lazy
	//因为类加载顺序的问题，这个类是先加载进容器的同时因为使用的@Autowired也把UserService加进了容器  在做切面的时候发生了问题，拿不到 UserService的代理对象，只能拿到UserService，所以这里使用懒加载的方式
	private RoleService roleService;

	@Override
	public String getName() {
		return this.getClass().getSimpleName();//获取当前类的对象
	}
	/*
	 * 认证
	 * */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("loginname",token.getPrincipal().toString());
		User user = userService.getOne(queryWrapper);//拿到用户名去数据库进行查找，返回该用户对象包含密码，用于下面进行对比
		if (null != user) {
			ActiverUser activerUser = new ActiverUser();//声明用户和角色和权限的抽象实体类
			activerUser.setUser(user);//放入用户
			
			//根据用户ID查询percode
			//查询所有菜单
			QueryWrapper<Permission> qw=new QueryWrapper<>();
			//设置只能查询菜单
			qw.eq("type",Constast.TYPE_PERMISSION);
			qw.eq("available", Constast.AVAILABLE_TRUE);
			
			//根据用户ID+角色+权限去查询
			
			Integer userId=user.getId();//拿到用户id
			//根据用户ID查询角色
			List<Integer> currentUserRoleIds = roleService.queryUserRoleIdsByUid(userId);//拿到角色id
			//根据角色ID取到权限和菜单ID
			Set<Integer> pids=new HashSet<>();//去重使用
			for (Integer rid : currentUserRoleIds) {//遍历角色id
				List<Integer> permissionIds = roleService.queryRolePermissionIdsByRid(rid);//拿到
				pids.addAll(permissionIds);
			}
			List<Permission> list=new ArrayList<>();
			//根据角色ID查询权限
			if(pids.size()>0) {
				qw.in("id", pids);
				list=permissionService.list(qw);//拿到Permission对象
			}
			List<String> percodes=new ArrayList<>();
			for (Permission permission : list) {
				percodes.add(permission.getPercode());//取出权限字段类似于dept:create
			}
			//放到实体类中
			activerUser.setPermissions(percodes);
			ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());//使用存储的salt当作盐
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activerUser, user.getPwd(), credentialsSalt,this.getName());
			return info;
		}
		return null;

	}
	
	
	
	
	/* 
	 * 
	 * 
	 * 授权*/
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
		ActiverUser activerUser=(ActiverUser) principals.getPrimaryPrincipal();
		User user=activerUser.getUser();
		List<String> permissions = activerUser.getPermissions();
		if(user.getType()==Constast.USER_TYPE_SUPER) {
			authorizationInfo.addStringPermission("*:*");
		}else {
			if(null!=permissions&&permissions.size()>0) {
				authorizationInfo.addStringPermissions(permissions);
			}
		}
		return authorizationInfo;

	}
	

}
