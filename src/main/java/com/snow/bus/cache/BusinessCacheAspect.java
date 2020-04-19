package com.snow.bus.cache;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import com.snow.bus.entity.Customer;
import com.snow.bus.entity.Goods;
import com.snow.bus.entity.Provider;
import com.snow.sys.cache.CacheAspect;


/**
 * 
 * 业务模块的切面类，用来做缓存
 * 
 * */
@Aspect
@Component
@EnableAspectJAutoProxy 
public class BusinessCacheAspect {
	
	
	/*
	 * 日志处理
	 * */
	private Log log = LogFactory.getLog(CacheAspect.class);
	
	//声明一个缓存容器 这里所有的切面都用这个一个容器来做缓存所有是静态的
	private static Map<String,Object> CACHE_CONTAINER=new HashMap<>();
	public static Map<String, Object> getCACHE_CONTAINER() {
		
		return CACHE_CONTAINER;
	}
	/*
	 * 客户切面操作
	 * 
	 * */
	
	//声明切面表达式
	
	private static final String POINTCUT_CUSTOMER_GET="execution(* com.snow.bus.service.impl.CustomerServiceImpl.getById(..))";
	private static final String POINTCUT_CUSTOMER_UPDATE="execution(* com.snow.bus.service.impl.CustomerServiceImpl.updateById(..))";
	private static final String POINTCUT_CUSTOMER_DELETE="execution(* com.snow.bus.service.impl.CustomerServiceImpl.removeById(..))";
	private static final String POINTCUT_CUSTOMER_ADD="execution(* com.snow.bus.service.impl.CustomerServiceImpl.save(..))";
	private static final String POINTCUT_CUSTOMER_BATCHDELETE = "execution(* com.snow.bus.service.impl.CustomerServiceImpl.removeByIds(..))";
	
	private static final String CACHE_CUSTOMER_PROFIX="customer:";
	
	
	/**
	 * 查询切入
	 * @throws Throwable 
	 */
	@Around(value=POINTCUT_CUSTOMER_GET)
	public Object cacheCustomerGet(ProceedingJoinPoint joinPoint) throws Throwable {
		//取出第一个参数
		Integer object = (Integer) joinPoint.getArgs()[0];
		//从缓存里面取
		Object res1 = CACHE_CONTAINER.get(CACHE_CUSTOMER_PROFIX+object);
		if(res1!=null) {
			log.info("从缓存中找到客户对象"+CACHE_CUSTOMER_PROFIX+object);
			return res1;
		}else {
			log.info("未从缓存中找到客户对象----》去数据库进行查询并放入缓存");
			Customer res2 = (Customer) joinPoint.proceed();
			CACHE_CONTAINER.put(CACHE_CUSTOMER_PROFIX+res2.getId(), res2);
			return res2;
		}
	}
	/**
	 * 更新切入
	 * @throws Throwable 
	 */
	@Around(value=POINTCUT_CUSTOMER_UPDATE)
	public Object cacheCustomerUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
		//取出第一个参数
		Customer CustomerVo = (Customer) joinPoint.getArgs()[0];
		Boolean  isSuccess =(Boolean) joinPoint.proceed();//执行目标方法
		if(isSuccess) {
			 Customer Customer = (Customer) CACHE_CONTAINER.get(CACHE_CUSTOMER_PROFIX+CustomerVo.getId());
			 if(null==Customer) {
				 Customer=new Customer();//存储的客户对象
				 BeanUtils.copyProperties(CustomerVo, Customer);
				 log.info("客户对象缓存已更新"+CACHE_CUSTOMER_PROFIX+CustomerVo.getId());//主键
				 CACHE_CONTAINER.put(CACHE_CUSTOMER_PROFIX+Customer.getId(), Customer);//存放进容器
			 }
		}
		return isSuccess;
	}
	
	/**
	 * 删除切入
	 * @throws Throwable 
	 */
	@Around(value=POINTCUT_CUSTOMER_DELETE)
	public Object cacheCustomerDelete(ProceedingJoinPoint joinPoint) throws Throwable {
		//取出第一个参数
	    Integer id = (Integer) joinPoint.getArgs()[0];
		Boolean  isSuccess =(Boolean) joinPoint.proceed();//放行目标方法
		if(isSuccess) {
			//删除缓存
			 CACHE_CONTAINER.remove(CACHE_CUSTOMER_PROFIX+id);
			 log.info("客户对象缓存已删除"+CACHE_CUSTOMER_PROFIX+id);
		}
		return isSuccess;
	}
	
	/**
	 * 添加切入
	 * @throws Throwable 
	 */
	@Around(value=POINTCUT_CUSTOMER_ADD)
	public Object cacheCustomerAdd(ProceedingJoinPoint joinPoint) throws Throwable {
		//取出第一个参数
		Customer object = (Customer) joinPoint.getArgs()[0];//拿到第一个参数，客户对象
		Boolean res = (Boolean) joinPoint.proceed();//放行目标方法
		if(res) {
			CACHE_CONTAINER.put(CACHE_CUSTOMER_PROFIX+object.getId(), object);//如果保存成功，在缓存中也放一份
		}
			
			return res;
	}
	/**
	 * 批量删除删除切入
	 * @throws Throwable 
	 */
	@Around(value=POINTCUT_CUSTOMER_BATCHDELETE)
	public Object cacheCustomerBatchDelete(ProceedingJoinPoint joinPoint) throws Throwable {
		// 取出第一个参数
		@SuppressWarnings("unchecked")//忽略警告 如使用List，ArrayList等未进行参数化产生的警告信息
		Collection<Serializable> idList = (Collection<Serializable>) joinPoint.getArgs()[0];
		Boolean isSuccess = (Boolean) joinPoint.proceed();//放行目标方法
		if (isSuccess) {
			for (Serializable id : idList) {
				// 删除缓存
				CACHE_CONTAINER.remove(CACHE_CUSTOMER_PROFIX + id);
				log.info("客户对象缓存已删除" + CACHE_CUSTOMER_PROFIX + id);
			}
		}
		return isSuccess;

	}
	
	/*
	 * 供应商切面操作
	 * */
	
	//声明切面表达式
	
	private static final String POINTCUT_PROVIDER_GET="execution(* com.snow.bus.service.impl.ProviderServiceImpl.getById(..))";
	private static final String POINTCUT_PROVIDER_UPDATE="execution(* com.snow.bus.service.impl.ProviderServiceImpl.updateById(..))";
	private static final String POINTCUT_PROVIDER_DELETE="execution(* com.snow.bus.service.impl.ProviderServiceImpl.removeById(..))";
	private static final String POINTCUT_PROVIDER_ADD="execution(* com.snow.bus.service.impl.ProviderServiceImpl.save(..))";
	private static final String POINTCUT_PROVIDER_BATCHDELETE = "execution(* com.snow.bus.service.impl.ProviderServiceImpl.removeByIds(..))";
	
	private static final String CACHE_PROVIDER_PROFIX="provider:";
	
	
	/**
	 * 查询切入
	 * @throws Throwable 
	 */
	@Around(value=POINTCUT_PROVIDER_GET)
	public Object cacheProviderGet(ProceedingJoinPoint joinPoint) throws Throwable {
		//取出第一个参数
		Integer object = (Integer) joinPoint.getArgs()[0];
		//从缓存里面取
		Object res1 = CACHE_CONTAINER.get(CACHE_PROVIDER_PROFIX+object);
		if(res1!=null) {
			log.info("从缓存中找到供应商对象"+CACHE_PROVIDER_PROFIX+object);
			return res1;
		}else {
			log.info("未从缓存中找到供应商对象----》去数据库进行查询并放入缓存");
			Provider res2 = (Provider) joinPoint.proceed();
			CACHE_CONTAINER.put(CACHE_PROVIDER_PROFIX+res2.getId(), res2);
			return res2;
		}
	}
	/**
	 * 更新切入
	 * @throws Throwable 
	 */
	@Around(value=POINTCUT_PROVIDER_UPDATE)
	public Object cacheProviderUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
		//取出第一个参数
		Provider providerVo = (Provider) joinPoint.getArgs()[0];
		Boolean  isSuccess =(Boolean) joinPoint.proceed();//执行目标方法
		if(isSuccess) {
			Provider provider = (Provider) CACHE_CONTAINER.get(CACHE_PROVIDER_PROFIX+providerVo.getId());
			 if(null==provider) {
				 provider=new Provider();//存储的供应商对象
				 BeanUtils.copyProperties(providerVo, provider);
				 log.info("供应商对象缓存已更新"+CACHE_PROVIDER_PROFIX+providerVo.getId());//主键
				 CACHE_CONTAINER.put(CACHE_PROVIDER_PROFIX+provider.getId(), provider);//存放进容器
			 }
		}
		return isSuccess;
	}
	
	/**
	 * 删除切入
	 * @throws Throwable 
	 */
	@Around(value=POINTCUT_PROVIDER_DELETE)
	public Object cacheProviderDelete(ProceedingJoinPoint joinPoint) throws Throwable {
		//取出第一个参数
	    Integer id = (Integer) joinPoint.getArgs()[0];
		Boolean  isSuccess =(Boolean) joinPoint.proceed();//放行目标方法
		if(isSuccess) {
			//删除缓存
			 CACHE_CONTAINER.remove(CACHE_PROVIDER_PROFIX+id);
			 log.info("供应商对象缓存已删除"+CACHE_PROVIDER_PROFIX+id);
		}
		return isSuccess;
	}
	
	/**
	 * 添加切入
	 * @throws Throwable 
	 */
	@Around(value=POINTCUT_PROVIDER_ADD)
	public Object cacheProviderAdd(ProceedingJoinPoint joinPoint) throws Throwable {
		//取出第一个参数
		Provider object = (Provider) joinPoint.getArgs()[0];//拿到第一个参数，客户对象
		Boolean res = (Boolean) joinPoint.proceed();//放行目标方法
		if(res) {
			CACHE_CONTAINER.put(CACHE_PROVIDER_PROFIX+object.getId(), object);//如果保存成功，在缓存中也放一份
		}
			
			return res;
	}
	/**
	 * 批量删除删除切入
	 * @throws Throwable 
	 */
	@Around(value=POINTCUT_PROVIDER_BATCHDELETE)
	public Object cacheProviderBatchDelete(ProceedingJoinPoint joinPoint) throws Throwable {
		// 取出第一个参数
		@SuppressWarnings("unchecked")//忽略警告 如使用List，ArrayList等未进行参数化产生的警告信息
		Collection<Serializable> idList = (Collection<Serializable>) joinPoint.getArgs()[0];
		Boolean isSuccess = (Boolean) joinPoint.proceed();//放行目标方法
		if (isSuccess) {
			for (Serializable id : idList) {
				// 删除缓存
				CACHE_CONTAINER.remove(CACHE_PROVIDER_PROFIX + id);
				log.info("供应商对象缓存已删除" + CACHE_PROVIDER_PROFIX + id);
			}
		}
		return isSuccess;

	}
	/*
	 * 商品切面操作
	 * */
	
	//声明切面表达式
	
	private static final String POINTCUT_GOODS_GET="execution(* com.snow.bus.service.impl.GoodsServiceImpl.getById(..))";
	private static final String POINTCUT_GOODS_UPDATE="execution(* com.snow.bus.service.impl.GoodsServiceImpl.updateById(..))";
	private static final String POINTCUT_GOODS_DELETE="execution(* com.snow.bus.service.impl.GoodsServiceImpl.removeById(..))";
	private static final String POINTCUT_GOODS_ADD="execution(* com.snow.bus.service.impl.GoodsServiceImpl.save(..))";
	
	private static final String CACHE_GOODS_PROFIX="goods:";
	
	
	/**
	 * 查询切入
	 * @throws Throwable 
	 */
	@Around(value=POINTCUT_GOODS_GET)
	public Object cacheGoodsGet(ProceedingJoinPoint joinPoint) throws Throwable {
		//取出第一个参数
		Integer object = (Integer) joinPoint.getArgs()[0];
		//从缓存里面取
		Object res1 = CACHE_CONTAINER.get(CACHE_GOODS_PROFIX+object);
		if(res1!=null) {
			log.info("从缓存中找到商品对象"+CACHE_GOODS_PROFIX+object);
			return res1;
		}else {
			log.info("未从缓存中找到商品对象----》去数据库进行查询并放入缓存");
			Goods res2 = (Goods) joinPoint.proceed();
			CACHE_CONTAINER.put(CACHE_GOODS_PROFIX+res2.getId(), res2);
			return res2;
		}
	}
	/**
	 * 更新切入
	 * @throws Throwable 
	 */
	@Around(value=POINTCUT_GOODS_UPDATE)
	public Object cacheGoodsUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
		//取出第一个参数
		Goods goodsVo = (Goods) joinPoint.getArgs()[0];
		Boolean  isSuccess =(Boolean) joinPoint.proceed();//执行目标方法
		if(isSuccess) {
			Goods goods = (Goods) CACHE_CONTAINER.get(CACHE_GOODS_PROFIX+goodsVo.getId());
			 if(null==goods) {
				 goods=new Goods();//存储的商品对象
				 BeanUtils.copyProperties(goodsVo, goods);
				 log.info("商品对象缓存已更新"+CACHE_GOODS_PROFIX+goodsVo.getId());//主键
				 CACHE_CONTAINER.put(CACHE_GOODS_PROFIX+goods.getId(), goods);//存放进容器
			 }
		}
		return isSuccess;
	}
	
	/**
	 * 删除切入
	 * @throws Throwable 
	 */
	@Around(value=POINTCUT_GOODS_DELETE)
	public Object cacheGoodsDelete(ProceedingJoinPoint joinPoint) throws Throwable {
		//取出第一个参数
	    Integer id = (Integer) joinPoint.getArgs()[0];
		Boolean  isSuccess =(Boolean) joinPoint.proceed();//放行目标方法
		if(isSuccess) {
			//删除缓存
			 CACHE_CONTAINER.remove(CACHE_GOODS_PROFIX+id);
			 log.info("商品对象缓存已删除"+CACHE_GOODS_PROFIX+id);
		}
		return isSuccess;
	}
	
	/**
	 * 添加切入
	 * @throws Throwable 
	 */
	@Around(value=POINTCUT_GOODS_ADD)
	public Object cacheGoodsAdd(ProceedingJoinPoint joinPoint) throws Throwable {
		//取出第一个参数
		Goods object = (Goods) joinPoint.getArgs()[0];//拿到第一个参数，客户对象
		Boolean res = (Boolean) joinPoint.proceed();//放行目标方法
		if(res) {
			CACHE_CONTAINER.put(CACHE_GOODS_PROFIX+object.getId(), object);//如果保存成功，在缓存中也放一份
		}
			return res;
	}
	
}
