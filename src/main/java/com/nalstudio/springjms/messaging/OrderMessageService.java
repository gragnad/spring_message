package com.nalstudio.springjms.messaging;

import com.nalstudio.springjms.domain.Order;

public interface OrderMessageService {
    void sendOrder(Order order);
}
