package com.yuchi.userservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuchi.userservice.model.dto.UserLoginCodeRequest;
import com.yuchi.userservice.model.dto.UserLoginPasswordRequest;
import com.yuchi.userservice.model.dto.UserRegisterRequest;
import com.yuchi.userservice.model.entity.User;
import com.yuchi.userservice.model.vo.LoginAndRegisterResponse;
import com.yuchi.userservice.model.vo.TokenResponse;


public interface UserService extends IService<User> {

    void sendCaptcha(String targetEmail);


    LoginAndRegisterResponse register(UserRegisterRequest userRegisterRequest);


    LoginAndRegisterResponse loginPassword(UserLoginPasswordRequest userLoginPasswordRequest);

    LoginAndRegisterResponse loginCode(UserLoginCodeRequest userLoginCodeRequest);

    boolean logout(String userId);

    TokenResponse refreshToken(String refreshToken);

    String refreshUri(Long userId);
}
