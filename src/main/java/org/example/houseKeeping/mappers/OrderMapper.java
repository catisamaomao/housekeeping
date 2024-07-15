package org.example.houseKeeping.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.houseKeeping.pojo.Order;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
