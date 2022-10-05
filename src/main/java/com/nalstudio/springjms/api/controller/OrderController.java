package com.nalstudio.springjms.api.controller;

import com.nalstudio.springjms.data.OrderRepository;
import com.nalstudio.springjms.domain.Order;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/orders", produces = "application/json")
@CrossOrigin(origins = "*")//하용
public class OrderController {

    private OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping(produces = "application/json")
    public Iterable<Order> allOrders() {
        return orderRepository.findAll();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Order postOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }

    @PutMapping(path = "/{orderId}", consumes = "application/json")
    public Order putOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }

    //클라이언트가 기존 리소스를 완전히 교체해야 하는 경우 PUT을 사용할 수 있습니다. 부분 업데이트를 수행할 때 HTTP PATCH를 사용할 수 있습니다.
    @PatchMapping(path = "/{orderId}", consumes = "application/json")
    public Order patchOrder(@PathVariable("orderId") Long orderId, @RequestBody Order patch) {
        Order order = orderRepository.findById(orderId).get();

        if(patch.getDeliveryName() != null){ order.setDeliveryName(patch.getDeliveryName()); }
        if(patch.getDeliveryStreet() != null){ order.setDeliveryStreet(patch.getDeliveryStreet()); }
        if(patch.getDeliveryCity() != null){ order.setDeliveryCity(patch.getDeliveryCity()); }
        if(patch.getDeliveryState() != null){ order.setDeliveryState(patch.getDeliveryState()); }
        if(patch.getDeliveryZip() != null){ order.setDeliveryZip(patch.getDeliveryZip()); }
        if(patch.getCcNumber() != null){ order.setCcNumber(patch.getCcNumber()); }
        if(patch.getCcExpiration() != null){ order.setCcExpiration(patch.getCcExpiration()); }
        if(patch.getCcCVV() != null){ order.setCcCVV(patch.getCcCVV()); }

        return orderRepository.save(order);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") Long orderId) {
        try {
            orderRepository.deleteById(orderId);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
    }
}
