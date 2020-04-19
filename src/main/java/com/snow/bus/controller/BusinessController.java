package com.snow.bus.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 *  业务前端控制器  主要负责业务模块的路由，跳转页面
 * </p>
 *
 * @author snow
 * @since 2019-12-28
 */
@Controller
@RequestMapping("/bus")
public class BusinessController {

	/*
	 * 跳转到客户管理页面
	 * */
	@RequestMapping("toCustomerManager")
	public String toCustomerManager() {
		
		return "business/customer/customerManager";
	}
	
	/*
	 * 跳转到供应商管理页面
	 * */
	@RequestMapping("toProviderManager")
	public String toProviderManager() {
		
		return "business/provider/providerManager";
	}
	
	/*
	 * 跳转到供应商管理页面
	 * */
	@RequestMapping("toGoodsManager")
	public String toGoodsManager() {
		
		return "business/goods/goodsManager";
	}
	
	/*
	 * 跳转到商品进货管理页面
	 * */
	@RequestMapping("toInportManager")
	public String toInportManager() {
		
		return "business/inport/inportManager";
	}
	
	/*
	 * 跳转到商品退货查询管理页面
	 * */
	@RequestMapping("toOutportManager")
	public String toOutportManager() {
		
		return "business/outport/outportManager";
	}
	/*
	 * 跳转到商品销售管理页面
	 * */
	@RequestMapping("toSalesManager")
	public String toSalesManager() {
		
		return "business/sales/salesManager";
	}
	
	/*
	 * 跳转到商品销售退货查询管理页面
	 * */
	@RequestMapping("toSalesBackManager")
	public String toSalesBackManager() {
		
		return "business/salesback/salesbackManager";
	}
}


