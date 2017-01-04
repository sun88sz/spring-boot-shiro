package com.sun.springboot.service;

import com.sun.springboot.bean.BaseUser;
import com.sun.springboot.dao.BaseUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Sun on 16/12/27.
 */
@Service
@Transactional
public class BaseUserService {

	@Autowired
	private BaseUserMapper baseUserMapper;

	public void selectById() {
		List<BaseUser> baseUsers = baseUserMapper.selectAll();

		System.out.println(baseUsers);
	}

}
