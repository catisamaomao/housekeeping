package org.example.houseKeeping.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.houseKeeping.mappers.AcceptanceMapper;
import org.example.houseKeeping.pojo.Acceptance;
import org.example.houseKeeping.services.AcceptanceService;
import org.springframework.stereotype.Service;

@Service
public class AcceptanceServiceImpl extends ServiceImpl<AcceptanceMapper, Acceptance> implements AcceptanceService {
}
