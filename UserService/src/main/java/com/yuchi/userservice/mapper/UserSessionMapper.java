package com.yuchi.userservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuchi.userservice.model.entity.UserSession;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserSessionMapper extends BaseMapper<UserSession> {
}
