package com.sun.springboot.service;

import com.sun.springboot.bean.OAuthClient;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Sun on 16/12/9.
 */
@Service
public class ClientServiceImpl implements ClientService {
	@Override
	public OAuthClient createClient(OAuthClient client) {
		return null;
	}

	@Override
	public OAuthClient updateClient(OAuthClient client) {
		return null;
	}

	@Override
	public void deleteClient(Long clientId) {

	}

	@Override
	public OAuthClient findOne(Long clientId) {
		return null;
	}

	@Override
	public List<OAuthClient> findAll() {
		return null;
	}

	@Override
	public OAuthClient findByClientId(String clientId) {
		OAuthClient client = new OAuthClient();
		client.setClientId("qaz");
		client.setClientKey("wsxedcrfv");
		return client;
	}

	@Override
	public OAuthClient findByClientSecret(String clientSecret) {
		return null;
	}
}
