package com.snow.sys.common;

import java.util.List;

import com.snow.sys.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 用户和角色和权限的抽象实体类，用于认证和授权
 * 
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiverUser {
	
	private User user;//用户
	private List<String> roles;//角色
	private List<String> permissions;//权限（菜单）
}
