package com.sun.springboot.service;

public interface OAuthService {
	// 添加 auth code
	void addAuthCode(String authCode, String username);

	// 添加 access token boolean checkAuthCode(String authCode);
	// 验证 auth code 是否有效
	void addAccessToken(String accessToken, String username);

	// 验证 access token 是否有效
	boolean checkAccessToken(String accessToken);

	// 根据 auth code 获取用户名
	String getUsernameByAuthCode(String authCode);

	// 根据 access token 获取用户名 long getExpireIn();
	// auth code / access token 过期时间
	String getUsernameByAccessToken(String accessToken);

	// 检查客户端 id 是否存在
	boolean checkClientId(String clientId);

	// 坚持客户端安全 KEY 是否存在
	boolean checkClientSecret(String clientId, String clientSecret);

	// 检测AuthCode
	boolean checkAuthCode(String authCode);

	String getExpireIn();
}