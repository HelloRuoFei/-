package com.snow.bus.service.impl;

import com.snow.bus.entity.Provider;
import com.snow.bus.mapper.ProviderMapper;
import com.snow.bus.service.ProviderService;
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
 * @since 2019-12-29
 */
@Service
public class ProviderServiceImpl extends ServiceImpl<ProviderMapper, Provider> implements ProviderService {
	@Override
	public boolean save(Provider entity) {
		return super.save(entity);
	}
	
	@Override
	public boolean updateById(Provider entity) {
		return super.updateById(entity);
	}
	
	@Override
	public Provider getById(Serializable id) {
		return super.getById(id);
	}
	
	@Override
	public boolean removeById(Serializable id) {
		return super.removeById(id);
	}
	
	@Override
	public boolean removeByIds(Collection<? extends Serializable> idList) {
		return super.removeByIds(idList);
	}
	
}
