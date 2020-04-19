package com.snow.sys.cache;

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

import com.snow.sys.entity.Dept;
import com.snow.sys.entity.User;


@Aspect
@Component
@EnableAspectJAutoProxy
public class CacheAspect {
	
	
	/*
	 * 日志处理
	 * 
	 * */
	
	private Log log = LogFactory.getLog(CacheAspect.class);
	
	
	
	//声明一个缓存容器 这里所有的切面都用这个一个容器来做缓存所有是静态的
	private static Map<String,Object> CACHE_CONTAINER=new HashMap<>();
	public static Map<String, Object> getCACHE_CONTAINER() {
		return CACHE_CONTAINER;
	}
	
	//声明切面表达式
	
	private static final String POINTCUT_DEPT_GET="execution(* com.snow.sys.service.impl.DeptServiceImpl.getById(..))";
	private static final String POINTCUT_DEPT_UPDATE="execution(* com.snow.sys.service.impl.DeptServiceImpl.updateById(..))";
	private static final String POINTCUT_DEPT_DELETE="execution(* com.snow.sys.service.impl.DeptServiceImpl.removeById(..))";
	private static final String POINTCUT_DEPT_ADD="execution(* com.snow.sys.service.impl.DeptServiceImpl.save(..))";
	
	
	private static final String CACHE_DEPT_PROFIX="dept:";
	
	
	/**
	 * 查询切入
	 * @throws Throwable 
	 */
	@Around(value=POINTCUT_DEPT_GET)
	public Object cacheDeptGet(ProceedingJoinPoint joinPoint) throws Throwable {
		//取出第一个参数
		Integer object = (Integer) joinPoint.getArgs()[0];
		//从缓存里面取
		Object res1 = CACHE_CONTAINER.get(CACHE_DEPT_PROFIX+object);
		if(res1!=null) {
			log.info("从缓存中找到部门对象"+CACHE_DEPT_PROFIX+object);
			return res1;
		}else {
			log.info("未从缓存中找到部门对象----》去数据库进行查询并放入缓存");
			Dept res2 = (Dept) joinPoint.proceed();
			CACHE_CONTAINER.put(CACHE_DEPT_PROFIX+res2.getId(), res2);
			return res2;
		}
	}
	/**
	 * 更新切入
	 * @throws Throwable 
	 */
	@Around(value=POINTCUT_DEPT_UPDATE)
	public Object cacheDeptUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
		//取出第一个参数
		Dept deptVo = (Dept) joinPoint.getArgs()[0];
		Boolean  isSuccess =(Boolean) joinPoint.proceed();
		if(isSuccess) {
			 Dept dept = (Dept) CACHE_CONTAINER.get(CACHE_DEPT_PROFIX+deptVo.getId());
			 if(null==dept) {
				 dept=new Dept();
				 BeanUtils.copyProperties(deptVo, dept);
				 log.info("部门对象缓存已更新"+CACHE_DEPT_PROFIX+deptVo.getId());
				 CACHE_CONTAINER.put(CACHE_DEPT_PROFIX+dept.getId(), dept);
			 }
		}
		return isSuccess;
	}
	
	/**
	 * 删除切入
	 * @throws Throwable 
	 */
	@Around(value=POINTCUT_DEPT_DELETE)
	public Object cacheDeptDelete(ProceedingJoinPoint joinPoint) throws Throwable {
		//取出第一个参数
	    Integer id = (Integer) joinPoint.getArgs()[0];
		Boolean  isSuccess =(Boolean) joinPoint.proceed();
		if(isSuccess) {
			//删除缓存
			CACHE_CONTAINER.remove(CACHE_DEPT_PROFIX+id);
			 log.info("部门对象缓存已删除"+CACHE_DEPT_PROFIX+id);
		}
		return isSuccess;
	}
	
	/**
	 * 添加切入
	 * @throws Throwable 
	 */
	@Around(value=POINTCUT_DEPT_ADD)
	public Object cacheDeptAdd(ProceedingJoinPoint joinPoint) throws Throwable {
		//取出第一个参数
		Dept object = (Dept) joinPoint.getArgs()[0];//拿到第一个参数，部门对象
		Boolean res = (Boolean) joinPoint.proceed();//放行目标方法
		if(res) {
			CACHE_CONTAINER.put(CACHE_DEPT_PROFIX+object.getId(), object);//如果保存成功，在缓存中也放一份
		}
			
			return res;
	}
	
	//声明切面表达式
	private static final String POINTCUT_USER_UPDATE="execution(* com.snow.sys.service.impl.UserServiceImpl.updateById(..))";
	private static final String POINTCUT_USER_GET="execution(* com.snow.sys.service.impl.UserServiceImpl.getById(..))";
	private static final String POINTCUT_USER_DELETE="execution(* com.snow.sys.service.impl.UserServiceImpl.removeById(..))";
	private static final String POINTCUT_USER_ADD="execution(* com.snow.sys.service.impl.UserServiceImpl.save(..))";
	
	private static final String CACHE_USER_PROFIX="user:";
	/**
	 * 查询切入
	 * @throws Throwable 
	 */
	@Around(value=POINTCUT_USER_GET)
	public Object cacheUserGet(ProceedingJoinPoint joinPoint) throws Throwable {
		//取出第一个参数
		Integer object = (Integer) joinPoint.getArgs()[0];
		//从缓存里面取
		Object res1 = CACHE_CONTAINER.get(CACHE_USER_PROFIX+object);
		if(res1!=null) {
			log.info("从缓存中找到用户对象"+CACHE_USER_PROFIX+object);
			return res1;
		}else {
			
			User res2 = (User) joinPoint.proceed();
			CACHE_CONTAINER.put(CACHE_USER_PROFIX+res2.getId(), res2);//这里可能会出现空指针异常res2.getId()可能为空，不清楚原因待定
			log.info("未从缓存里面找到用户对象，去数据库查询并放到缓存"+CACHE_USER_PROFIX+res2.getId());
			return res2;
		}
	}
	/**
	 * 更新切入
	 * @throws Throwable 
	 */
	@Around(value=POINTCUT_USER_UPDATE)
	public Object cacheUserUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
		//取出第一个参数
		User UserVo = (User) joinPoint.getArgs()[0];
		Boolean  isSuccess =(Boolean) joinPoint.proceed();
		if(isSuccess) {
			 User user = (User) CACHE_CONTAINER.get(CACHE_USER_PROFIX+UserVo.getId());//查找对象在缓存中是否存在
			 if(null==user) {//不存在
				 user=new User();//新建一个
			 }
			 BeanUtils.copyProperties(UserVo, user);//进行对象之间属性的赋值，避免通过get、set方法一个一个属性的赋值
			 log.info("用户对象缓存已更新"+CACHE_USER_PROFIX+UserVo.getId());
			 CACHE_CONTAINER.put(CACHE_USER_PROFIX+user.getId(), user);//放入容器
		}
		return isSuccess;
	}
	
	/**
	 * 删除切入
	 * @throws Throwable 
	 */
	@Around(value=POINTCUT_USER_DELETE)
	public Object cacheUserDelete(ProceedingJoinPoint joinPoint) throws Throwable {
		//取出第一个参数
	    Integer id = (Integer) joinPoint.getArgs()[0];
		Boolean  isSuccess =(Boolean) joinPoint.proceed();
		if(isSuccess) {
			//删除缓存
			CACHE_CONTAINER.remove(CACHE_USER_PROFIX+id);
			 log.info("用户对象缓存已删除"+CACHE_USER_PROFIX+id);
		}
		return isSuccess;
	}
	
	/**
	 * 添加切入
	 * @throws Throwable 
	 */
	@Around(value=POINTCUT_USER_ADD)
	public Object cacheUserAdd(ProceedingJoinPoint joinPoint) throws Throwable {
		//取出第一个参数
		User object = (User) joinPoint.getArgs()[0];//拿到第一个参数，部门对象
		Boolean res = (Boolean) joinPoint.proceed();//放行目标方法
		if(res) {
			CACHE_CONTAINER.put(CACHE_USER_PROFIX+object.getId(), object);//如果保存成功，在缓存中也放一份
		}
			
			return res;
	}
}
