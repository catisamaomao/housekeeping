package org.example.houseKeeping.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CacheService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void cacheData(String key, Object data) {
        try {
            String jsonData = objectMapper.writeValueAsString(data);
            redisTemplate.opsForValue().set(key, jsonData, 60, TimeUnit.MINUTES);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public String getData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public <T> T getData(String key, TypeReference<T> valueTypeRef) {
        String jsonData = redisTemplate.opsForValue().get(key);
        if (jsonData != null) {
            try {
                return objectMapper.readValue(jsonData, valueTypeRef);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
