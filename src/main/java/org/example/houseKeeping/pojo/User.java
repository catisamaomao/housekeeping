package org.example.houseKeeping.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class User {
    @TableId(value = "user_id")
    private Integer userId;
    @TableField("account")
    private String account;
    @TableField("passward")
    private String passward;
    @TableField("user_name")
    private String userName;
    @TableField("role")
    private Integer role;
    @TableField("state")
    private Integer state;
    @TableField("phone")
    private String phone;
}
