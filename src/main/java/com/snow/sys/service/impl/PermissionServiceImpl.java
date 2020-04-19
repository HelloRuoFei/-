package com.snow.sys.service.impl;

import com.snow.sys.entity.Permission;
import com.snow.sys.mapper.PermissionMapper;
import com.snow.sys.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author snow
 * @since 2019-12-12
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

	@Override
	public boolean removeById(Serializable id) {//重写删除的方法。在该方法执行前 根据权限或者菜单id删除权限表和角色的关系表里面的数据
		PermissionMapper permissionMapper = this.getBaseMapper();
		permissionMapper.deleteRolePermissionByid(id);
		return super.removeById(id); //删除权限表的数据
	}
}
