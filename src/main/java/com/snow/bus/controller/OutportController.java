package com.snow.bus.controller;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.snow.bus.entity.Goods;
import com.snow.bus.entity.Outport;
import com.snow.bus.entity.Provider;
import com.snow.bus.service.GoodsService;
import com.snow.bus.service.OutportService;
import com.snow.bus.service.ProviderService;

import com.snow.bus.vo.OutportVo;
import com.snow.sys.common.DataGridView;
import com.snow.sys.common.ResultObj;

/**
 * <p>
 *  商品退货查询前端控制器
 * </p>
 *
 * @author snow
 * @since 2019-12-30
 */
@RestController
@RequestMapping("/outport")
public class OutportController {
	
	@Autowired
	private OutportService outportService;
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private GoodsService goodsService;
	/*
	 *订单退货
	 *
	 *修改库存数量
	 * */
	@RequestMapping("addOutport")
	public ResultObj addOutport(Integer id,Integer number,String remark) {
		
		try {
			this.outportService.addOutport(id,number,remark);//退货订单信息
			return ResultObj.OPERATE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.OPERATE_ERROR;
		}
		
	}
	
	/*
	/*
	 * 查询所有退货订单,到页面进行展示
	 * */
	@RequestMapping("loadAllOutport")
	public DataGridView loadAllOutport(OutportVo outportVo) {
		IPage<Outport> page = new Page<>(outportVo.getPage(),outportVo.getLimit());//分页设置
		QueryWrapper<Outport> queryWrapper = new QueryWrapper<>();
		//下拉查询
		queryWrapper.eq(outportVo.getProviderid()!=null&&outportVo.getProviderid()!=0,"providerid",outportVo.getProviderid());
		queryWrapper.eq(outportVo.getGoodsid()!=null&&outportVo.getGoodsid()!=0,"goodsid",outportVo.getGoodsid());
		//日期查询
		queryWrapper.ge(outportVo.getStartTime()!=null, "outputtime", outportVo.getStartTime());
		queryWrapper.le(outportVo.getEndTime()!=null, "outputtime", outportVo.getEndTime());
		//模糊查询
		queryWrapper.like(StringUtils.isNotBlank(outportVo.getOperateperson()), "operateperson", outportVo.getOperateperson());
		queryWrapper.like(StringUtils.isNotBlank(outportVo.getRemark()), "remark", outportVo.getRemark());
		//排序
		queryWrapper.orderByDesc("outputtime");
		this.outportService.page(page, queryWrapper);
		List<Outport> records = page.getRecords();
		for (Outport outport : records) {
			Provider provider = this.providerService.getById(outport.getProviderid());
			if(null!=provider) {
				outport.setProvidername(provider.getProvidername());
			}
			Goods goods = this.goodsService.getById(outport.getGoodsid());
			if(null!=goods) {
				outport.setGoodsname(goods.getGoodsname());
				outport.setSize(goods.getSize());
			}
		}
		return new DataGridView(page.getTotal(),records);
	}
}

