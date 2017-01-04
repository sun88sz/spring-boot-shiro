package com.sun.configuration.druid;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.sun.springboot.utils.security.AESUtil;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * druid 配置.
 *
 * 这样的方式不需要添加注解：@ServletComponentScan <br/>
 * 通过访问 http://ip:port/druid2/login.html
 * 
 * @author
 */
@Configuration
public class DruidConfiguration {
//
//	@Bean
//	@ConfigurationProperties(prefix = "spring.datasource")
//	public DruidDataSourceProperties getDatasourceProperties() {
//		return new DruidDataSourceProperties();
//	}
//
//	@Bean
//	public DruidDataSource getDatasource(DruidDataSourceProperties properties) {
//		DruidDataSource build = (DruidDataSource) properties.initializeDataSourceBuilder().type(DruidDataSource.class)
//				.build();
//		// 在这里使用无效, 密码未解密, 原因未知
//		// build.setPasswordCallback(new DBPasswordCallback());
//
//		try {
//			String pwd = AESUtil.decrypt(properties.getPassword(), DBPasswordCallback.key);
//			build.setPassword(pwd);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return build;
//	}

	/**
	 * 注册一个StatViewServlet
	 * 
	 * @return
	 */
	@Bean
	public ServletRegistrationBean DruidStatViewServle2() {
		// org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),
				"/druid2/*");
		// 添加初始化参数：initParams
		// 白名单：
		servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
		// IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not
		// permitted to view this page.
		// servletRegistrationBean.addInitParameter("deny", "192.168.1.73");
		// 登录查看信息的账号密码.
		servletRegistrationBean.addInitParameter("loginUsername", "admin");
		servletRegistrationBean.addInitParameter("loginPassword", "1");
		// 是否能够重置数据.
		servletRegistrationBean.addInitParameter("resetEnable", "false");
		return servletRegistrationBean;
	}

	/**
	 * 注册一个：filterRegistrationBean
	 * 
	 * @return
	 */
	@Bean
	public FilterRegistrationBean druidStatFilter2() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
		// 添加过滤规则.
		filterRegistrationBean.addUrlPatterns("/*");
		// 添加不需要忽略的格式信息.
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid2/*");
		return filterRegistrationBean;
	}

	// 请使用该方法加密后，把密文写入datasource.properties
	public static void main(String[] args) {
		try {
			String keyEn = AESUtil.encrypt("jike17712613261", DBPasswordCallback.key);
			System.out.println(keyEn);

			String decrypt = AESUtil.decrypt(keyEn, DBPasswordCallback.key);
			System.out.println(decrypt);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
