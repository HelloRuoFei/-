package com.snow.sys.service;

import com.snow.sys.entity.User;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author snow
 * @since 2019-12-08
 */
public interface UserService extends IService<User> {

	void saveUserRole(Integer uid, Integer[] ids);


}
