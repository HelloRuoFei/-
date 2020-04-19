package com.snow.bus.service.impl;

import com.snow.bus.entity.Goods;
import com.snow.bus.entity.Inport;
import com.snow.bus.mapper.GoodsMapper;
import com.snow.bus.mapper.InportMapper;
import com.snow.bus.service.GoodsService;
import com.snow.bus.service.InportService;
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
 * @since 2019-12-30
 */
@Service
@Transactional
public class InportServiceImpl extends ServiceImpl<InportMapper, Inport> implements InportService {
	
	@Autowired
	private GoodsMapper goodsMapper;
	
	@Autowired
	private GoodsService goodsService;//在自身的实现类里面不能注册进来自己Service同时不能调用方法，只能使用自己Mapper层的方法可以注入一个Mapper
	
	@Override
	public boolean save(Inport entity) {
		//根据商品id查询商品，在进货的同时，增加商品的数量
		Goods goods = this.goodsService.getById(entity.getGoodsid());//根据id拿到商品对象
		//Goods goods1 = this.goodsMapper.selectById(entity.getGoodsid());//根据id拿到商品对象  这里可以使用goodsService层的方法，也可以使用goodsMapper层的方法
		goods.setNumber(goods.getNumber()+entity.getNumber());//库存加新进的商品数量
		this.goodsMapper.updateById(goods);//更新数据
		return super.save(entity);
	}
	
	
	@Override
	public boolean updateById(Inport entity) {
		//根据商品id查询商品，在进货的同时，增加商品的数量
		Goods goods = this.goodsService.getById(entity.getGoodsid());//根据id拿到商品对象
		//baseMapper是所有mapper的父类可以直接使用
		Inport inport = this.baseMapper.selectById(entity.getId());//拿到进货单对象
		goods.setNumber(goods.getNumber()-inport.getNumber()+entity.getNumber());//更新库存，   相当于，库存减去前一个订单数量，加上新订单数量
		this.goodsService.updateById(goods);//跟新数据
		return super.updateById(entity);
	}
	
	@Override
	public boolean removeById(Serializable id) {
		//baseMapper是所有mapper的父类可以直接使用
		Inport inport = this.baseMapper.selectById(id);//拿到进货单对象
		//根据商品id查询商品，在进货的同时，增加商品的数量
		Goods goods = this.goodsService.getById(inport.getGoodsid());//根据id拿到商品对象
		goods.setNumber(goods.getNumber()-inport.getNumber());//更新库存，   相当于，库存减去前一个订单数量
		this.goodsService.updateById(goods);//跟新数据
		return super.removeById(id);
	}
}
