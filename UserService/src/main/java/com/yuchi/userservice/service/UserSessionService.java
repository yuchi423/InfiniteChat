package com.yuchi.userservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuchi.userservice.model.entity.UserSession;

import java.util.List;


public interface UserSessionService extends IService<UserSession> {
    List<Long> getUserIdBySessionId(Long sessionId);
}
