package com.sun.springboot.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.PasswordMatcher;

/**
 * Created by Sun on 16/12/6.
 */
public class BCryptCredentialsMathcher extends PasswordMatcher {

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		// 数据库里的密码
		SimpleAuthenticationInfo sai = (SimpleAuthenticationInfo) info;
		String pwd = (String) sai.getCredentials();

		// 用户输入的密码
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		BCryptPasswordService passwordService = (BCryptPasswordService) getPasswordService();
		String pwdInput = passwordService.getUnencryptPassword(upToken);

		return getPasswordService().passwordsMatch(pwdInput, pwd);
	}
}
