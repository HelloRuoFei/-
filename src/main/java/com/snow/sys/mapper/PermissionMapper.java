package com.snow.sys.mapper;

import com.snow.sys.entity.Permission;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author snow
 * @since 2019-12-12
 */
public interface PermissionMapper extends BaseMapper<Permission> {
	/*
	 * 根据权限或者菜单id删除权限表和角色的关系表里面的数据
	 * 
	 * */
	void deleteRolePermissionByid(@Param("id")Serializable id);
}
