package com.snow.sys.service.impl;

import com.snow.sys.entity.Role;
import com.snow.sys.mapper.RoleMapper;
import com.snow.sys.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 *
 * @author snow
 * @since 2019-12-24
 */
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
	
	/*
	 * 删除角色的同时应该同时删除角色和权限表中的数据以及角色和用户表之间的数据
	 * */
	@Override
	public boolean removeById(Serializable id) {//这是api自带的方法，不需要在service声明
		
		RoleMapper roleMapper = this.getBaseMapper();
		//根据角色id删除sys_role_permission中的数据
		roleMapper.deleteRolePermissionByRid(id);
		//根据角色id删除sys_role_user中的数据
		roleMapper.deleteRoleUserByRid(id);
		
		return super.removeById(id);
	}
	
	/*
	 * 根据角色id查询角色拥有的所有的权限或菜单id
	 * */
	@Override
	public List<Integer> queryRolePermissionIdsByRid(Integer roleId) {//下面两个是自定义的方法需要在service声明
		RoleMapper roleMapper = this.getBaseMapper();
		return roleMapper.queryRolePermissionIdsByRid(roleId);
	}
	
	/*
	 *保存角色和菜单权限之间的关系
	 * */
	@Override
	public void saveRolePermission(Integer rid, Integer[] ids) {
		RoleMapper roleMapper = this.getBaseMapper();
		roleMapper.deleteRolePermissionByRid(rid);//先把原来的删除，根据rid，在往里面存储新选择的，双主键不能重复
		if(ids!=null&&ids.length>0) {//一条一条的插入数据
			for (Integer pid: ids) {
				roleMapper.saveRolePermission(rid,pid);
			}
		}
	}
	
	/*
	 * 根据用户id查询用户所拥有的角色 
	 * */
	@Override
	public List<Integer> queryUserRoleIdsByUid(Integer id) {
		RoleMapper roleMapper = this.getBaseMapper();
		return roleMapper.queryUserRoleIdsByUid(id);
	}
}
