package com.snow.bus.controller;


import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.snow.bus.entity.Goods;
import com.snow.bus.entity.Inport;
import com.snow.bus.entity.Provider;
import com.snow.bus.service.GoodsService;
import com.snow.bus.service.InportService;
import com.snow.bus.service.ProviderService;

import com.snow.bus.vo.InportVo;
import com.snow.sys.common.DataGridView;
import com.snow.sys.common.ResultObj;
import com.snow.sys.common.WebUtils;
import com.snow.sys.entity.User;

/**
 * <p>
 * 商品进货管理 前端控制器
 * </p>
 *
 * @author snow
 * @since 2019-12-30
 */
@RestController
@RequestMapping("/inport")
public class InportController {
	
	@Autowired
	private InportService inportService;
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private GoodsService goodsService;
	
	
	/*
	/*
	 * 查询所有客户,到页面进行展示
	 * */
	@RequestMapping("loadAllInport")
	public DataGridView loadAllInport(InportVo inportVo) {
		IPage<Inport> page = new Page<>(inportVo.getPage(),inportVo.getLimit());//分页设置
		QueryWrapper<Inport> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(inportVo.getProviderid()!=null&&inportVo.getProviderid()!=0, "providerid",inportVo.getProviderid());
		queryWrapper.eq(inportVo.getGoodsid()!=null&&inportVo.getGoodsid()!=0, "Goodsid",inportVo.getGoodsid());
		//拼接sql，模糊查询,当下面为null时是不生效的
		queryWrapper.like(StringUtils.isNotBlank(inportVo.getOperateperson()), "operateperson",inportVo.getOperateperson());
		queryWrapper.like(StringUtils.isNotBlank(inportVo.getRemark()), "remark",inportVo.getRemark());
		//区间查询
		queryWrapper.ge(inportVo.getStartTime()!=null, "logintime", inportVo.getStartTime());
		queryWrapper.le(inportVo.getEndTime()!=null, "logintime", inportVo.getEndTime());
		//排序
		queryWrapper.orderByAsc("inporttime");//按时间排序
		this.inportService.page(page, queryWrapper);//分页方法调用
		 List<Inport> records = page.getRecords();//扩展结果，加进providername
		for(Inport inport:records) {
			Provider byId = this.providerService.getById(inport.getProviderid());//根据id拿到供应商对象，缓存中有就从缓存拿，缓存没有就去数据库拿
			
			if(null!=byId) {
				inport.setProvidername(byId.getProvidername());//把供应商名字，赋值给扩展的属性
			}
			Goods byId2 = this.goodsService.getById(inport.getGoodsid());//根据id拿到商品对象，  缓存中有就从缓存拿，缓存没有就去数据库拿
			if(null!=byId2) {
				inport.setGoodsname(byId2.getGoodsname());//把商品名字，赋值给扩展的属性
				inport.setSize(byId2.getSize());
			}
		}
		return new DataGridView(page.getTotal(),records);
	}
	
	/*
	 *添加商品进货
	 *
	 *添加的同时需要修改库存数量
	 * */
	@RequestMapping("addInport")
	public ResultObj addInport(InportVo inportVo) {
		
		try {
			inportVo.setInporttime(new Date());//设置时间
			User user = (User) WebUtils.getSession().getAttribute("user");//拿到登录对象
			inportVo.setOperateperson(user.getName());//这里获取的是用户名
			this.inportService.save(inportVo);//进货记录
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}	
	}
	
	/**
	 * 修改
	 * 修改库存数量
	 */
	@RequestMapping("updateInport")
	public ResultObj updateInport(InportVo inportVo) {
		try {
			this.inportService.updateById(inportVo);
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.UPDATE_ERROR;
		}
	}
	
	/*
	 *订单删除
	 *
	 *修改库存数量
	 * */
	@RequestMapping("deleteInport")
	public ResultObj deleteInport(Integer id) {
		
		try {
			this.inportService.removeById(id);//删除订单信息
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
		
	}
	
	


}

