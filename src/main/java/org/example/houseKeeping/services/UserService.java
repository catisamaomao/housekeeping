package org.example.houseKeeping.services;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.houseKeeping.pojo.Result;
import org.example.houseKeeping.pojo.User;

import java.util.List;

public interface UserService extends IService<User> {
    Result login(User user);

    List<User> getUserByType(Integer type);
}
