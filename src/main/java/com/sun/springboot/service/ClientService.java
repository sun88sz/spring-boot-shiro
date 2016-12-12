package com.sun.springboot.service;

import com.sun.springboot.bean.OAuthClient;

import java.util.List;

public interface ClientService {
	// 创建客户端
	OAuthClient createClient(OAuthClient client);

	// 更新客户端
	OAuthClient updateClient(OAuthClient client);

	// 删除客户端
	void deleteClient(Long clientId);

	// 根据 id 查找客户端
	OAuthClient findOne(Long clientId);

	// 查找所有
	List<OAuthClient> findAll();

	// 根据客户端 id 查找客户端
	OAuthClient findByClientId(String clientId);

	// 根据客户端安全 KEY 查找客户端
	OAuthClient findByClientSecret(String clientSecret);

}