package com.yuchi.userservice.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User {
    @TableId
    private Long userId;

    private String phone;

    private String email;

    private String password;

    private String nickname;

    private String avatar;

    private Integer gender;

    private String description;

    private Integer state;

    private Integer role;

    private Date createdTime;

    private Date updatedTime;

    private Integer isDelete;
}
