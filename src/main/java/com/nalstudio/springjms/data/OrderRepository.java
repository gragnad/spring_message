package com.nalstudio.springjms.data;

import com.nalstudio.springjms.domain.Order;
import com.nalstudio.springjms.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    //jpa repository custom
    List<Order> findByDeliveryZip(String deliveryZip);

    List<Order> readOrdersByDeliveryZipAndPlacedAtBetween(
            String deliveryZip, Date startDate, Date endDate
    );

    List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);

}
