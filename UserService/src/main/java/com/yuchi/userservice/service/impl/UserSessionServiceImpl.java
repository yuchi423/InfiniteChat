package com.yuchi.userservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuchi.userservice.mapper.UserSessionMapper;
import com.yuchi.userservice.model.entity.UserSession;
import com.yuchi.userservice.service.UserService;
import com.yuchi.userservice.service.UserSessionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserSessionServiceImpl extends ServiceImpl<UserSessionMapper, UserSession>
        implements UserSessionService{

    @Override
    public List<Long> getUserIdBySessionId(Long sessionId){
        QueryWrapper<UserSession> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("session_id", sessionId);

        List<UserSession> userSessions = this.list(queryWrapper);

        return userSessions.stream().map(UserSession::getUserId).collect(Collectors.toList());

    }
}
