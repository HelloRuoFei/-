package com.snow.sys.service;

import com.snow.sys.entity.Role;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author snow
 * @since 2019-12-24
 */
public interface RoleService extends IService<Role> {
	/*
	 * 根据角色id查询角色拥有的所有的权限或菜单id
	 * */
	List<Integer> queryRolePermissionIdsByRid(Integer roleId);
	
	/*
	 * 保存角色和菜单权限之间的关系 
	 * */
	
	void saveRolePermission(Integer rid, Integer[] ids);
	
	/*
	 * 根据用户id查询用户所拥有的角色 
	 * */
	List<Integer> queryUserRoleIdsByUid(Integer id);


}
