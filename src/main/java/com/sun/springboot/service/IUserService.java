package com.sun.springboot.service;

import com.sun.springboot.bean.User;

/**
 * Created by Sun on 16/12/15.
 */
public interface IUserService {
	User selectByIdApproveLogin(Long id);
}
