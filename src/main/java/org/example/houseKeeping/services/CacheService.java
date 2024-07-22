package org.example.houseKeeping.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Service
public class CacheService {

    private static final Logger logger = Logger.getLogger(CacheService.class.getName());

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void cacheData(String key, Object data) {
        try {
            String jsonData = objectMapper.writeValueAsString(data);
            redisTemplate.opsForValue().set(key, jsonData, 60, TimeUnit.MINUTES);
            logger.info("Data cached for key: " + key);
        } catch (JsonProcessingException e) {
            logger.severe("Error caching data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getData(String key) {
        String jsonData = null;
        try {
            jsonData = redisTemplate.opsForValue().get(key);
            logger.info("Data retrieved for key: " + key + ", data: " + jsonData);
        } catch (Exception e) {
            logger.severe("Error retrieving data from Redis for key: " + key + ". Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return jsonData;
    }

    public <T> T getData(String key, TypeReference<T> valueTypeRef) {
        String jsonData = null;
        try {
            jsonData = redisTemplate.opsForValue().get(key);
            logger.info("Data retrieved for key: " + key + ", data: " + jsonData);
        } catch (Exception e) {
            logger.severe("Error retrieving data from Redis for key: " + key + ". Exception: " + e.getMessage());
            e.printStackTrace();
        }

        if (jsonData != null) {
            try {
                return objectMapper.readValue(jsonData, valueTypeRef);
            } catch (JsonProcessingException e) {
                logger.severe("Error parsing JSON data for key: " + key + ". Exception: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }
}
