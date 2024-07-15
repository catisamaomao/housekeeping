package org.example.houseKeeping.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("acceptance")
public class Acceptance {
    @TableId("acceptance_id")
    private Integer acceptanceId;
    @TableField("user_id")
    private Integer userId;
    @TableField("content")
    private String content;
    @TableField("agree")
    private Integer agree;
}