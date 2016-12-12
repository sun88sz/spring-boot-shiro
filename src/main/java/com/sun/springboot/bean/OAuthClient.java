package com.sun.springboot.bean;

import java.io.Serializable;

/**
 * Created by Sun on 16/12/9.
 */
public class OAuthClient implements Serializable{

	private Long id;
	private String clientName;
	private String clientId;
	private String clientKey;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientKey() {
		return clientKey;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}
}
