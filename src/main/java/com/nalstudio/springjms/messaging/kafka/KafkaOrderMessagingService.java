package com.nalstudio.springjms.messaging.kafka;

import com.nalstudio.springjms.domain.Order;
import com.nalstudio.springjms.messaging.OrderMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaOrderMessagingService implements OrderMessageService {

    private KafkaTemplate<String, Order> kafkaTemplate;

    @Autowired
    public KafkaOrderMessagingService(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendOrder(Order order) {
//        kafkaTemplate.send("tacocloud.topic", order);

        kafkaTemplate.sendDefault(order);

    }
}
