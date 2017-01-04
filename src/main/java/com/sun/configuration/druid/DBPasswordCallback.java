package com.sun.configuration.druid;

import com.alibaba.druid.util.DruidPasswordCallback;
import com.sun.springboot.utils.StringUtils;
import com.sun.springboot.utils.security.AESUtil;

import java.util.Properties;

/**
 * 数据库密码回调解密
 */
public class DBPasswordCallback extends DruidPasswordCallback {

	// 必须16位
	public static String key = "vcxz1234qwer0987";

	public void setProperties(Properties properties) {
		super.setProperties(properties);
		String pwd = properties.getProperty("password");
		if (StringUtils.isNotBlank(pwd)) {
			try {
				// 这里的password是将jdbc.properties配置得到的密码进行解密之后的值
				// 所以这里的代码是将密码进行解密
				// TODO 将pwd进行解密;
				String password = AESUtil.decrypt(pwd, key);
				setPassword(password.toCharArray());
			} catch (Exception e) {
				setPassword(pwd.toCharArray());
			}
		}
	}
}