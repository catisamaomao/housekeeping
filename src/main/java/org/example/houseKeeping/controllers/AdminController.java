package org.example.houseKeeping.controllers;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.example.houseKeeping.pojo.*;
import org.example.houseKeeping.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AcceptanceService acceptanceService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private CacheService cacheService;

    @GetMapping("/getAllUser")
    @ResponseBody
    public Result getAllUser() {
        String cacheKey = "allUsers";
        List<User> cachedData = cacheService.getData(cacheKey, new TypeReference<List<User>>() {
        });

        if (cachedData != null) {
            return Result.success(cachedData);
        }

        List<User> list = userService.list();
        cacheService.cacheData(cacheKey, list);
        return Result.success(list);
    }

    @GetMapping("/getUserByType")
    @ResponseBody
    public Result getUserByType(Integer type) {
        String cacheKey = "userType:" + type;
        List<User> cachedData = cacheService.getData(cacheKey, new TypeReference<List<User>>() {
        });

        if (cachedData != null) {
            return Result.success(cachedData);
        }

        List<User> list = userService.getUserByType(type);
        cacheService.cacheData(cacheKey, list);
        return Result.success(list);
    }

    @GetMapping("/getUserByUserId")
    @ResponseBody
    public Result getUserByUserId(@RequestParam("userId") Long userId) {
        return Result.success(userService.getById(userId));
    }

    @DeleteMapping("/deleteUser")
    @ResponseBody
    public Result deleteUser(@RequestParam Integer userId) {
        try {
            userService.removeById(userId);
            orderService.remove(new LambdaQueryWrapper<Order>().eq(Order::getUserId, userId));
            acceptanceService.remove(new LambdaQueryWrapper<Acceptance>().eq(Acceptance::getUserId, userId));
            cacheService.cacheData("allUsers", null);
            return Result.success();
        } catch (Exception e) {
            return Result.error("删除失败" + e.getMessage());
        }
    }

    @GetMapping("/searchUserByUsername")
    @ResponseBody
    public Result searchUserByUsername(@RequestParam String username) {
        List<User> list = userService.list(new LambdaQueryWrapper<User>()
                .like(User::getUserName, "%" + username + "%"));
        return Result.success(list);
    }

    @PostMapping("/addUser")
    @ResponseBody
    public Result addUser(@RequestBody User user) {
        try {
            userService.save(user);
            cacheService.cacheData("allUsers", null);
            return Result.success();
        } catch (Exception e) {
            return Result.error("添加失败" + e.getMessage());
        }
    }

    @PutMapping("/update")
    @ResponseBody
    public Result update(@RequestBody User user) {
        try {
            userService.updateById(user);
            cacheService.cacheData("allUsers", null);
            return Result.success();
        } catch (Exception e) {
            return Result.error("删除失败" + e.getMessage());
        }
    }

    @PutMapping("/updateState")
    @ResponseBody
    public Result updateState(@RequestBody Map<String, String> pload) {
        try {
            User user = userService.getById(pload.get("userId"));
            if (user == null) {
                return Result.error("用户不存在");
            }
            user.setState(Integer.parseInt(pload.get("state")));
            userService.updateById(user);
            cacheService.cacheData("allUsers", null);
            return Result.success();
        } catch (Exception e) {
            return Result.error("修改状态失败：" + e.getMessage());
        }
    }

    @GetMapping("/getAllNotice")
    @ResponseBody
    public Result getAllNotice() {
        List<Notice> list = noticeService.list(null);
        return Result.success(list);
    }

    @DeleteMapping("/deleteNoticeById")
    @ResponseBody
    public Result deleteNoticeById(@RequestParam Integer noticeId) {
        try {
            noticeService.removeById(noticeId);
            return Result.success();
        } catch (Exception e) {
            return Result.error("删除公告失败" + e.getMessage());
        }
    }

    @GetMapping("/searchNoticeByTitle")
    @ResponseBody
    public Result searchNoticeByTitle(@RequestParam String title) {
        List<Notice> list = noticeService.list(new LambdaQueryWrapper<Notice>()
                .like(Notice::getTitle, "%" + title + "%"));
        return Result.success(list);
    }

    @GetMapping("/getNoticeByType")
    @ResponseBody
    public Result getNoticeByType(@RequestParam Integer type) {
        List<Notice> list = noticeService.list(new LambdaQueryWrapper<Notice>()
                .eq(Notice::getType, type));
        return Result.success(list);
    }

    @PostMapping("/publishNotice")
    @ResponseBody
    public Result publishNotice(@RequestBody Notice notice) {
        try {
            noticeService.save(notice);
            return Result.success();
        } catch (Exception e) {
            return Result.error("发布公告失败" + e.getMessage());
        }
    }

    @PutMapping("/updateNoticeById")
    @ResponseBody
    public Result updateNoticeById(@RequestBody Notice notice) {
        try {
            noticeService.updateById(notice);
            return Result.success();
        } catch (Exception e) {
            return Result.error("修改公告失败" + e.getMessage());
        }
    }

    @GetMapping("/getAllAcceptance")
    @ResponseBody
    public Result getAllAcceptance() {
        List<Acceptance> list = acceptanceService.list(null);
        return Result.success(list);
    }

    @PutMapping("/dealAcceptance")
    @ResponseBody
    public Result dealAcceptance(@RequestBody Map<String, String> pload) {
        try {
            Acceptance acceptance = acceptanceService.getById(pload.get("id"));
            if (acceptance == null) {
                return Result.error("接单申请不存在");
            }
            acceptance.setAgree(Integer.parseInt(pload.get("agree")));
            acceptanceService.updateById(acceptance);
            return Result.success();
        } catch (Exception e) {
            return Result.error("处理接单申请失败：" + e.getMessage());
        }
    }

    @GetMapping("/getAllOrders")
    @ResponseBody
    public Result getAllOrders() {
        List<Order> list = orderService.list(null);
        return Result.success(list);
    }

    @DeleteMapping("/deleteOrder")
    @ResponseBody
    public Result deleteOrder(@RequestParam Integer id) {
        try {
            orderService.removeById(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error("删除订单失败" + e.getMessage());
        }
    }

    @PutMapping("/receiveOrder")
    @ResponseBody
    public Result receiveOrder(@RequestBody Map<String, String> pload) {
        try {
            Order order = orderService.getById(pload.get("orderId"));
            if (order == null) {
                return Result.error("订单不存在");
            }
            order.setReceiverId(Integer.parseInt(pload.get("receiverId")));
            order.setState(1); // 确认接单
            orderService.updateById(order);
            return Result.success();
        } catch (Exception e) {
            return Result.error("确认接单失败：" + e.getMessage());
        }
    }

    @PutMapping("/cancelReceive")
    @ResponseBody
    public Result cancelReceive(@RequestBody Map<String, String> payload) throws Exception {
        try {
            Order order = orderService.getById(payload.get("orderId"));
            if (order == null) {
                return Result.error("订单不存在");
            }
            order.setState(0); // 取消接单
            //order.setReceiverId(null);
            order.setReceiverId(0);
            orderService.updateById(order);
            return Result.success();
        } catch (Exception e) {
            return Result.error("取消接单失败：" + e.getMessage());
        }
    }

    @PutMapping("/finishOrder")
    @ResponseBody
    public Result finishOrder(@RequestBody Map<String, String> payload) throws Exception {
        try {
            Order order = orderService.getById(payload.get("orderId"));
            if (order == null) {
                return Result.error("订单不存在");
            }
            order.setState(3); // 完成接单
            order.setEndTime(new Date());
            orderService.updateById(order);
            return Result.success();
        } catch (Exception e) {
            return Result.error("完成接单失败：" + e.getMessage());
        }
    }

    @GetMapping("/getMyOrder")
    @ResponseBody
    public Result getMyOrder(@RequestParam Integer receiverId) {
        List<Order> list = orderService.list(new LambdaQueryWrapper<Order>()
                .eq(Order::getReceiverId, receiverId));
        return Result.success(list);
    }

    @GetMapping("/getMyTurnOver")
    @ResponseBody
    public Result getMyTurnOver(@RequestParam Integer receiverId) {
        List<Order> list = orderService.list(new LambdaQueryWrapper<Order>()
                .eq(Order::getReceiverId, receiverId)
                .eq(Order::getState, 3));

//        HashMap<Date, Double> map = new HashMap<>();
//        list.forEach((Order order) ->
//                map.put(order.getEndTime(), order.getPrice()));

        return Result.success(list);
    }

}
