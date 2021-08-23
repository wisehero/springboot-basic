package org.prms.service;

import org.prms.domain.Order;
import org.prms.domain.OrderItem;
import org.prms.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


//@Service
public class OrderService {

    private final VoucherService voucherService;
    private final OrderRepository orderRepository;


//    @Autowired
    public OrderService(VoucherService voucherService, OrderRepository orderRepository) {
        this.voucherService = voucherService;
        this.orderRepository = orderRepository;
    }

    // Voucher가 없는 경우
    public Order createOrder(UUID customerId, List<OrderItem> orderItems) {

        var order= new Order(UUID.randomUUID(),customerId,orderItems);
        // order 정보 저장

        return orderRepository.insert(order);
    }


    // Voucher가 잇는 경우
    public Order createOrder(UUID customerId, List<OrderItem> orderItems, UUID voucherId) {
        var voucher=voucherService.getVoucher(voucherId);
        var order= new Order(UUID.randomUUID(),customerId,orderItems,voucher);
        // order 정보 저장
        orderRepository.insert(order);

        // voucher를 재사용못하게 하는 useVoucher를 만듦
        voucherService.useVoucher(voucher);

        return order;
    }


}