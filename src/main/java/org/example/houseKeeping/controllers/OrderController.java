package org.example.houseKeeping.controllers;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.example.houseKeeping.pojo.Order;
import org.example.houseKeeping.pojo.Result;
import org.example.houseKeeping.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/worker")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/getAllOrderByUnReceived")
    @ResponseBody
    public Result getAllOrderByUnReceived() {
        //获取所有未被接单的订单即 state == 0
        int state = 0;
        return Result.success(orderService.list(new LambdaQueryWrapper<Order>()
                .eq(Order::getState, state)));
    }

  /*  @GetMapping("/getAllOrderByUnReceived")
    @ResponseBody
    public Result getAllOrderByUnReceived() {
        //获取所有未被接单正在完成的订单即 state == 0 || state == 1
        int state1 = 0;
        List<Order> list1 = orderService.list(new LambdaQueryWrapper<Order>()
                .eq(Order::getState, state1));
        int state2 = 1;
        List<Order> list2 = orderService.list(new LambdaQueryWrapper<Order>()
                .eq(Order::getState, state2));

        return Result.success(list1.addAll(list2));
    }*/


}
