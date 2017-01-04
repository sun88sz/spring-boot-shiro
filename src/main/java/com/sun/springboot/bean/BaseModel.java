package com.sun.springboot.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by Sun on 16/12/15.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseModel implements Serializable {

	private Integer limit;
	private Integer no;
	private String keywords;
	private String orderByClause;
	// 自定义排序
	private String orderByCustom;

	private Long id;
	private String token;

	private String accessWay;

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	public String getOrderByCustom() {
		return orderByCustom;
	}

	public void setOrderByCustom(String orderByCustom) {
		this.orderByCustom = orderByCustom;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAccessWay() {
		return accessWay;
	}

	public void setAccessWay(String accessWay) {
		this.accessWay = accessWay;
	}


	public static void main(String[] args) {

	}
}
