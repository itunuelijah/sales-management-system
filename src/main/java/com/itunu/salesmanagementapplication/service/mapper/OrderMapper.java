package com.itunu.salesmanagementapplication.service.mapper;

import com.itunu.salesmanagementapplication.data.dto.OrderDto;
import com.itunu.salesmanagementapplication.data.models.Order;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@Service
public class OrderMapper {

    public OrderDto fromOrder(Order order) {
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(order, orderDto);
        return orderDto;
    }

    public Order fromOrderDTO(OrderDto orderDto) {
        Order order = new Order();
        BeanUtils.copyProperties(orderDto, order);
        return order;
    }
}






