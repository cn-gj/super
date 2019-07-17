package com.superman.supermarket.config;

import com.superman.supermarket.realm.EmployeeRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro核心配置类
 */
@Configuration
public class ShiroConfig {
	
	/**
	 * 注入uesrRealm
	 * @return
	 */
	@Bean
	public EmployeeRealm getRealm() {
		return new EmployeeRealm();
	}
	
	/**
	 * 创建ShiroFilterFactoryBean 过滤器、设置安全管理器
	 * @param securityManager
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
		//创建ShiroFilterFactoryBean对象
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		//设置安全管理器
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		//添加shiro内置过滤器
		/*
		 *shiro内置过滤器,可以实现权限相关的拦截器
		 *	常用过滤器：
		 *		anon：无需认证(登录)即可访问
		 *		anthc：必须身份验证才可访问
		 *		user：如果使用rememberMe(记住我)的功能可以直接访问
		 *		perms：该资源必须得到资源权限才可访问
		 *		roles：该资源必须得到角色权限才可访问
		 * 
		 */
		//创建map集合、用户保存过滤器请求
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
		/**********************设置放行anon请求***************************/
		filterChainDefinitionMap.put("/employee/login", "anon");//登录操作

		//设置过滤器链
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		// 设置登录页面请求（不设置默认去到login.jsp页面）
		//shiroFilterFactoryBean.setLoginUrl("/login.html");
		// 设置权限验证失败请求页面
		//shiroFilterFactoryBean.setUnauthorizedUrl();
		return shiroFilterFactoryBean;
	}
	
	
	/**
	 * 创建DefaultWebSecurityManager、并关联自定义realm
	 * @param employeeRealm
	 * @return
	 */
	@Bean
	public DefaultWebSecurityManager getDefaultWebSecurityManager(EmployeeRealm employeeRealm) {
		//创建defaultWebSecurityManager对象
		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		//设置关联的realm
		defaultWebSecurityManager.setRealm(employeeRealm);
		//返回defaultWebSecurityManager对象
		return defaultWebSecurityManager;
	}
}
