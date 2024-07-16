package org.example.houseKeeping.controllers;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.example.houseKeeping.pojo.Order;
import org.example.houseKeeping.pojo.Result;
import org.example.houseKeeping.pojo.User;
import org.example.houseKeeping.services.CacheService;
import org.example.houseKeeping.services.OrderService;
import org.example.houseKeeping.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CacheService cacheService;


    @PostMapping("/login")
    @ResponseBody
    public Result login(@RequestBody User user) {
        return userService.login(user);
    }

    @PostMapping("/register")
    @ResponseBody
    public Result register(@RequestBody User user) {
        userService.save(user);
        cacheService.cacheData("allUsers", null);
        return Result.success();
    }

    @PostMapping("/publishOrder")
    @ResponseBody
    public Result publishOrder(@RequestBody Order order) {
        try {
            orderService.save(order);
            return Result.success();
        } catch (Exception e) {
            return Result.error("Failed to publish order , " + e.getMessage());
        }
    }

    @PutMapping("/closeOrder")
    @ResponseBody
    public Result closeOrder(@RequestBody Map<String, String> payload) {
        try {
            Order order = orderService.getOne(new LambdaQueryWrapper<Order>()
                    .eq(Order::getOrderId, payload.get("orderId")));

            if (order.getState() == 0) {
                order.setState(2);
                orderService.updateById(order);
                return Result.success();
            } else {
                return Result.error("请检查订单状态");
            }
        } catch (Exception e) {
            return Result.error("Failed to close order , " + e.getMessage());
        }

    }

    @GetMapping("/getAllOrderByUserId")
    @ResponseBody
    public Result getAllOrderByUserId(@RequestParam("userId") Long userId) {
        return Result.success(orderService.list(new LambdaQueryWrapper<Order>()
                .eq(Order::getUserId, userId)));
    }

    @GetMapping("/getDetail")
    @ResponseBody
    public Result getDetail(@RequestParam("userId") Long userId) {
        return Result.success(userService.getById(userId));
    }

    @PutMapping("/sendComment")
    @ResponseBody
    public Result sendComment(@RequestBody Map<String, String> payload) {
        try {
            Order order = orderService.getById(payload.get("orderId"));
            order.setComment(payload.get("comment"));
            orderService.updateById(order);
            return Result.success();
        } catch (Exception e) {
            return Result.error("Failed to send comment, " + e.getMessage());
        }
    }
}
