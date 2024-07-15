package org.example.houseKeeping.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.houseKeeping.mappers.UserMapper;
import org.example.houseKeeping.pojo.Result;
import org.example.houseKeeping.pojo.User;
import org.example.houseKeeping.services.UserService;
import org.example.houseKeeping.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result login(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getAccount, user.getAccount())
                .eq(User::getPassward, user.getPassward());
        User userResult = userMapper.selectOne(queryWrapper);

        if (userResult == null) {
            return Result.error("账号或密码错误");
        } else {
            if (userResult.getState() == 1) {
                return Result.error("账户被禁用");
            } else if (userResult.getState() == 0) {
                // 登录成功，生成JWT令牌
                String jwtToken = JwtUtils.generateJwt(userResult.getUserId().toString()); // 假设生成JWT的方法

                // 构建返回结果，包含JWT令牌和用户信息
                Map<String, Object> data = new HashMap<>();
                data.put("token", jwtToken);
                data.put("user", userResult);

                return Result.success(data);
            } else {
                return Result.error("检查用户状态，出现了其他状态 state = " + userResult.getState());
            }
        }
    }

    @Override
    public List<User> getUserByType(Integer type) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getRole, type);
        return userMapper.selectList(queryWrapper);
    }
}
