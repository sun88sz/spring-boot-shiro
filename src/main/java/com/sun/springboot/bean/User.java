package com.sun.springboot.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Sun on 16/11/10.
 */
@Entity
@Table(name = "t_base_user_bak")
@ApiModel(value = "用户对象", description = "用户详细实体")
public class User extends BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;

	private Long deptId;

	@NotNull
	@ApiModelProperty(value = "用户名", required = true)
	private String username;

	private String password;

	private String sex;

	private String nameCn;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNameCn() {
		return nameCn;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}
}
