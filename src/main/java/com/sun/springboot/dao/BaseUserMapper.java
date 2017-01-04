package com.sun.springboot.dao;

import com.sun.springboot.bean.BaseUser;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface BaseUserMapper extends Mapper<BaseUser> {
}