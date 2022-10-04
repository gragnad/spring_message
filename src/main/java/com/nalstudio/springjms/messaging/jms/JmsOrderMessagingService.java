package com.nalstudio.springjms.messaging.jms;

import com.nalstudio.springjms.domain.Order;
import com.nalstudio.springjms.messaging.OrderMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;

@Service
public class JmsOrderMessagingService implements OrderMessageService {

    private JmsTemplate jmsTemplate;

    @Autowired
    public JmsOrderMessagingService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

     /* MessageCreator 사용할 때
    @Override
    public void sendOrder(Order order) {
        // Order로 부터 새로운 메시지 생성
        jmsTemplate.send(session -> session.createObjectMessage(order));
    }
    */

    /* 기본 도착지를 send 의 파라미터로 전달할 때
    public void sendOrder(Order order) {
        // Order로 부터 새로운 메시지 생성
        jmsTemplate.send(
                "tacocloud.order.queue",
                session -> session.createObjectMessage(order));
    }
    */

    // convertAndSend() 로 전성될 객체 인자로 직접 전달
    /*@Override
    public void sendOrder(Order order) {
        jmsTemplate.convertAndSend("tacocloud.order.queue", order);
    }*/


    @Override
    public void sendOrder(Order order) {
        /* jmsTemplate.convertAndSend("tacocloud.order.queue", order,
                this::addOrderSource);*/
        jmsTemplate.convertAndSend("tacocloud.queue", order);
    }

    private Message addOrderSource(Message message) throws JMSException {
        message.setStringProperty("X_ORDER_SOURCE", "WEB");
        return message;
    }
}
