package com.yuchi.userservice.model.entity;

import lombok.Data;

@Data
public class UpdateAvatarRequest {

    private String uri;

    private Long userId;
}
