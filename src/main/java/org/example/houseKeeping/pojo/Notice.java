package org.example.houseKeeping.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("notice")
public class Notice {
    @TableId("notice_id")
    private Integer noticeId;
    @TableField("content")
    private String content;
    @TableField("publish_time")
    private Date publishTime;
    @TableField("author")
    private String author;
    @TableField("title")
    private String title;
    @TableField("type")
    private Integer type;
}
