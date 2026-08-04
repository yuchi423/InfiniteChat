package com.yuchi.offlinedataservice.consumer;

import cn.hutool.json.JSONUtil;
import com.yuchi.common.model.dto.MessageRequest;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerOfflineService {

    @KafkaListener(topics = "store-topic", groupId = "Infinite-chat-store-group")
    public void consume(String message){
        System.out.println("Consumed message store: " + message);
        MessageRequest messageRequest = JSONUtil.toBean(message, MessageRequest.class);
        System.out.println(messageRequest);
    }
}
