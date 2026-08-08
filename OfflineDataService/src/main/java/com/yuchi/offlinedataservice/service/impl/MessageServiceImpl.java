package com.yuchi.offlinedataservice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuchi.common.common.ErrorCode;
import com.yuchi.common.exception.ThrowUtils;
import com.yuchi.common.model.dto.MessageRequest;
import com.yuchi.offlinedataservice.model.entity.Message;
import com.yuchi.offlinedataservice.mapper.MessageMapper;
import com.yuchi.offlinedataservice.service.MessageService;

public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Override
    public void saveMessageToMySQL(MessageRequest messageRequest){
        Message message = new Message();
        BeanUtil.copyProperties(messageRequest, message);

        message.setContent(messageRequest.getBody().getContent());
        message.setReplyId(messageRequest.getBody().getReplyId());

        ThrowUtils.throwIf(!this.save(message), ErrorCode.SYSTEM_ERROR);

    }
}
