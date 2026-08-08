package com.yuchi.offlinedataservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuchi.common.model.dto.MessageRequest;
import com.yuchi.offlinedataservice.model.entity.Message;

public interface MessageService extends IService<Message> {
    void saveMessageToMySQL (MessageRequest messageRequest);
}
