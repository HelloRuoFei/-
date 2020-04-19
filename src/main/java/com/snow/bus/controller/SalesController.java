package com.snow.bus.controller;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.snow.bus.entity.Goods;
import com.snow.bus.entity.Inport;
import com.snow.bus.entity.Provider;
import com.snow.bus.vo.InportVo;
import com.snow.sys.common.DataGridView;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author snow
 * @since 2020-03-20
 */
@RestController
@RequestMapping("/sales")
public class SalesController {
//	/*
//	/*
//	 * 查询所有客户,到页面进行展示
//	 * */
//	@RequestMapping("loadAllInport")
//	public DataGridView loadAllInport(InportVo inportVo) {
//		IPage<Inport> page = new Page<>(inportVo.getPage(),inportVo.getLimit());//分页设置
//		QueryWrapper<Inport> queryWrapper = new QueryWrapper<>();
//		queryWrapper.eq(inportVo.getProviderid()!=null&&inportVo.getProviderid()!=0, "providerid",inportVo.getProviderid());
//		queryWrapper.eq(inportVo.getGoodsid()!=null&&inportVo.getGoodsid()!=0, "Goodsid",inportVo.getGoodsid());
//		//拼接sql，模糊查询,当下面为null时是不生效的
//		queryWrapper.like(StringUtils.isNotBlank(inportVo.getOperateperson()), "operateperson",inportVo.getOperateperson());
//		queryWrapper.like(StringUtils.isNotBlank(inportVo.getRemark()), "remark",inportVo.getRemark());
//		//区间查询
//		queryWrapper.ge(inportVo.getStartTime()!=null, "logintime", inportVo.getStartTime());
//		queryWrapper.le(inportVo.getEndTime()!=null, "logintime", inportVo.getEndTime());
//		//排序
//		queryWrapper.orderByAsc("inporttime");//按时间排序
//		this.inportService.page(page, queryWrapper);//分页方法调用
//		 List<Inport> records = page.getRecords();//扩展结果，加进providername
//		for(Inport inport:records) {
//			Provider byId = this.providerService.getById(inport.getProviderid());//根据id拿到供应商对象，缓存中有就从缓存拿，缓存没有就去数据库拿
//			
//			if(null!=byId) {
//				inport.setProvidername(byId.getProvidername());//把供应商名字，赋值给扩展的属性
//			}
//			Goods byId2 = this.goodsService.getById(inport.getGoodsid());//根据id拿到商品对象，  缓存中有就从缓存拿，缓存没有就去数据库拿
//			if(null!=byId2) {
//				inport.setGoodsname(byId2.getGoodsname());//把商品名字，赋值给扩展的属性
//				inport.setSize(byId2.getSize());
//			}
//		}
//		return new DataGridView(page.getTotal(),records);
//	}
}

