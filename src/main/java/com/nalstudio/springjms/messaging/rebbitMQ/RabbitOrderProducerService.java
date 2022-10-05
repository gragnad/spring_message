package com.nalstudio.springjms.messaging.rebbitMQ;

import com.nalstudio.springjms.domain.Order;
import com.nalstudio.springjms.messaging.OrderMessageService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitOrderProducerService implements OrderMessageService {
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitOrderProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendOrder(Order order) {
        System.out.println("## Send Order");

        rabbitTemplate.convertAndSend("tacocloud.order.queue", order,
                new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        MessageProperties props = message.getMessageProperties();
                        props.setHeader("X_ORDER_SOURCE", "WEB");
                        return message;
                    }
                });
    }

//    @RabbitListener(queues = "tacocloud.order.queue")
//    public void receiveOrder(Order order) {
//        //Todo message listen execute your service
//    }
}
