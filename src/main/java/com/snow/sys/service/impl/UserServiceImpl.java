package com.snow.sys.service.impl;

import com.snow.sys.entity.User;
import com.snow.sys.mapper.RoleMapper;
import com.snow.sys.mapper.UserMapper;
import com.snow.sys.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author snow
 * @since 2019-12-08
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
	@Autowired
	private RoleMapper roleMapper;
	
	
	@Override
	public User getById(Serializable id) {
		return super.getById(id);
	}
	
	@Override
	public boolean updateById(User entity) {
		return super.updateById(entity);
	}
	
	@Override
	public boolean removeById(Serializable id) {
		roleMapper.deleteRoleUserByUid(id);//在删除用户的同时把权限角色表中的数据也删除，，这里是根据用户id来删除的
		//删除用户头像，如果是默认头像则不删除，不是则删除
		return super.removeById(id);
	}
	
	@Override
	public boolean save(User entity) {
		// TODO Auto-generated method stub
		return super.save(entity);
	}

	@Override
	public void saveUserRole(Integer uid, Integer[] ids) {
		//在删除用户的同时把角色_用户表中的数据也删除，，这里是根据用户id来删除的
		roleMapper.deleteRoleUserByUid(uid);//先把之前用户所拥有的角色全部删除
		if(null!=ids&&ids.length>0) {//判断ids是否为空，不为空则代表选择了角色
			
			for(Integer rid: ids){ //遍历
				this.roleMapper.insertUserRole(uid,rid);//删除数据后重新把数据存储到权限角色表即sys_role_user
			}
		}
		
	}
}
