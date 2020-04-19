package com.snow.sys.service.impl;

import com.snow.sys.entity.Dept;
import com.snow.sys.mapper.DeptMapper;
import com.snow.sys.service.DeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author snow
 * @since 2019-12-16
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {
	
	
	/*缓存的时候使用，，三个切面对应是三个方法 */
	@Override
	public Dept getById(Serializable id) {
		return super.getById(id);
	}
	
	@Override
	public boolean updateById(Dept entity) {
		// TODO Auto-generated method stub
		return super.updateById(entity);
	}
	
	@Override
	public boolean removeById(Serializable id) {
		return super.removeById(id);
	}
	
	@Override
	public boolean save(Dept entity) {
		// TODO Auto-generated method stub
		return super.save(entity);
	}
}
