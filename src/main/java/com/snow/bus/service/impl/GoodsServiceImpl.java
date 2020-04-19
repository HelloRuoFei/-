package com.snow.bus.service.impl;

import com.snow.bus.entity.Goods;
import com.snow.bus.mapper.GoodsMapper;
import com.snow.bus.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;

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
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
	@Override
	public Goods getById(Serializable id) {
		return super.getById(id);
	}
	
	@Override
	public boolean save(Goods entity) {
		return super.save(entity);
	}
	
	@Override
	public boolean updateById(Goods entity) {
		return super.updateById(entity);
	}
	
	@Override
	public boolean removeById(Serializable id) {
		return super.removeById(id);
	}
}
