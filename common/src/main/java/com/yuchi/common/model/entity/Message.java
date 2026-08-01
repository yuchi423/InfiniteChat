package com.yuchi.common.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@TableName(value = "'message'")
public class Message implements Serializable {

    @TableId
    private Long messageId;

    private Long senderId;

    private Long sessionId;

    private Integer type;

    private String content;

    private Long replyId;

    private Integer sessionType;

    private Date createdTime;

    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 1L;
}
