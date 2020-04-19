package com.snow.sys.mapper;

import com.snow.sys.entity.Role;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author snow
 * @since 2019-12-24
 */
public interface RoleMapper extends BaseMapper<Role> {
	//根据角色id删除sys_role_permission中的数据
	void deleteRolePermissionByRid(@Param("id")Serializable id);
	
	//根据角色id删除sys_role_user中的数据
	void deleteRoleUserByRid(@Param("id")Serializable id);
	
	// 根据角色id查询角色拥有的所有的权限或菜单id
	List<Integer> queryRolePermissionIdsByRid(@Param("roleId")Integer roleId);
	
	//保存角色和菜单权限之间的关系
	void saveRolePermission(@Param("rid")Integer rid, @Param("pid")Integer pid);
	
	//在删除用户的同时把角色_用户表中的数据也删除，，这里是根据用户id来删除的
	void deleteRoleUserByUid(@Param("id")Serializable id);
	
	//根据用户id查询用户所拥有的角色 
	List<Integer> queryUserRoleIdsByUid(@Param("id")Integer id);
	
	////删除数据后重新把数据存储到权限角色表即sys_role_user
	void insertUserRole(@Param("uid")Integer uid, @Param("rid")Integer rid);

}
