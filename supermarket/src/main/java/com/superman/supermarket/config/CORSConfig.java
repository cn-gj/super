package com.superman.supermarket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * web配置
 * @author ASUS
 *
 */
@Configuration
public class CORSConfig extends WebMvcConfigurationSupport{
	/**
     * 跨域请求支持
     */
	@Override
	protected void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		        .allowedOrigins("*")
		        .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
		        .maxAge(3600)
		        .allowCredentials(true);
	}

	/**
	 * 设置跨域请求响应编码
	 * @param converters
	 */
	@Override
	protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
	}
}
