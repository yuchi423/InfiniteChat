package com.yuchi.userservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuchi.userservice.model.entity.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper extends BaseMapper<User> {

}




