package org.example.houseKeeping.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.houseKeeping.mappers.OrderMapper;
import org.example.houseKeeping.pojo.Order;
import org.example.houseKeeping.services.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
}
