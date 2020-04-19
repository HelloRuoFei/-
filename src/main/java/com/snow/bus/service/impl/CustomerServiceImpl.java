package com.snow.bus.service.impl;

import com.snow.bus.entity.Customer;
import com.snow.bus.mapper.CustomerMapper;
import com.snow.bus.service.CustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author snow
 * @since 2019-12-28
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {
	
	@Override
	public boolean save(Customer entity) {
		return super.save(entity);
	}
	
	@Override
	public boolean updateById(Customer entity) {
		return super.updateById(entity);
	}
	
	@Override
	public boolean removeById(Serializable id) {
		return super.removeById(id);
	}
	
	@Override
	public boolean removeByIds(Collection<? extends Serializable> idList) {
		return super.removeByIds(idList);
	}
	
	@Override
	public Customer getById(Serializable id) {
		return super.getById(id);
	}
}
