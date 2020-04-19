package com.snow.bus.service.impl;

import com.snow.bus.entity.Goods;
import com.snow.bus.entity.Inport;
import com.snow.bus.entity.Outport;
import com.snow.bus.mapper.GoodsMapper;
import com.snow.bus.mapper.InportMapper;
import com.snow.bus.mapper.OutportMapper;
import com.snow.bus.service.OutportService;
import com.snow.sys.common.WebUtils;
import com.snow.sys.entity.User;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author snow
 * @since 2019-12-30
 */
@Service
public class OutportServiceImpl extends ServiceImpl<OutportMapper, Outport> implements OutportService {
	@Autowired
	private GoodsMapper goodsMapper;//在自身的实现类里面不能注册进来自己Service同时不能调用方法，只能使用自己Mapper层的方法可以注入一个Mapper
	
	@Autowired
	private InportMapper inportMapper;
	
	@Override
	public void addOutport(Integer id, Integer number, String remark) {
		//根据货单id查询进货单对象
		Inport inport = this.inportMapper.selectById(id);//拿到进货单对象
		Integer goodsid = inport.getGoodsid();//拿到商品id
		Goods goods = this.goodsMapper.selectById(goodsid);//拿到商品对象
		goods.setNumber(goods.getNumber()-number);//减商品库存
		this.goodsMapper.updateById(goods);//更新商品
		inport.setNumber(inport.getNumber()-number);//减订单中的商品数量
		this.inportMapper.updateById(inport);//更新订单信息
		//添加退货单信息
		Outport outport = new Outport();
		outport.setGoodsid(inport.getGoodsid());
		outport.setNumber(number);
		User user = (User) WebUtils.getSession().getAttribute("user");//拿到登录对象
		String name = user.getName();//用户名
		outport.setOperateperson(name);
		outport.setOutportprice(inport.getInportprice());
		outport.setOutputtime(new Date());
		outport.setPaytype(inport.getPaytype());
		outport.setProviderid(inport.getProviderid());
		outport.setRemark(inport.getRemark());
		this.getBaseMapper().insert(outport);//插入数据库
	}

}
