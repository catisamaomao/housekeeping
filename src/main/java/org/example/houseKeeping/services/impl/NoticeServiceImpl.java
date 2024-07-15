package org.example.houseKeeping.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.houseKeeping.mappers.NoticeMapper;
import org.example.houseKeeping.pojo.Notice;
import org.example.houseKeeping.services.NoticeService;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
}
