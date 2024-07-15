package org.example.houseKeeping.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("`order`")
public class Order {
    @TableId("`order_id`")
    private Integer orderId;
    @TableField("user_id")
    private Integer userId;
    @TableField("content")
    private String content;
    @TableField("price")
    private Double price;
    @TableField("state")
    private Integer state;
    @TableField("comment")
    private String comment;
    @TableField("receiver_id")
    private Integer receiverId;
    @TableField("start_time")
    private Date startTime;
    @TableField("end_time")
    private Date endTime;
}