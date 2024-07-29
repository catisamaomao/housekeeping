package org.example.houseKeeping.controllers;
import org.example.houseKeeping.exception.BusException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.example.houseKeeping.pojo.Order;
import org.example.houseKeeping.pojo.Result;
import org.example.houseKeeping.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/worker")
public class OrderController {
    @Value("${image.path}")
    private String basePath;
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


    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
//获取文件后缀
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //判断文件基址,不存在就创建
       /* File baseFile = new File(basePath);
        if (!baseFile.exists()) {
            baseFile.mkdirs();
        }*/
        //组装
        String uuid = UUID.randomUUID().toString();
        String path = basePath + uuid + suffix;
        String name = uuid + suffix;
        try {
            file.transferTo(new File(path));
        } catch (IOException e) {
            throw new BusException("文件上传失败");
        }
        return Result.success(name);
    }

    @GetMapping("/download")
    public Result download(String name, HttpServletResponse response) {
        //读取文件
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
            response.setContentType("image/jpeg");
            int len = 0;
            byte[] bytes = new byte[1024];
            ServletOutputStream responseOutputStream = response.getOutputStream();
            while ((len = fileInputStream.read(bytes)) != -1) {
                responseOutputStream.write(bytes);
                responseOutputStream.flush();
            }
        } catch (IOException e) {
            throw new BusException("资源下载失败");
        }
        return Result.success("资源传输完成");
    }


    @PutMapping("/modifyState")
    @ResponseBody
    public Result modify(@RequestBody Map<String,String> payload) {
        Order order = orderService.getById(payload.get("orderId"));
        if (order == null) {
            throw new BusException("订单不存在");
        }

        order.setState(Integer.parseInt(payload.get("state")));
        orderService.updateById(order);
        return Result.success("状态修改成功");

    }
}
