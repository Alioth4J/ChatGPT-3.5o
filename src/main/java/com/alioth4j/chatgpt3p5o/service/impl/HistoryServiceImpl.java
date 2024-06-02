package com.alioth4j.chatgpt3p5o.service.impl;

import com.alioth4j.chatgpt3p5o.config.RedisConfig;
import com.alioth4j.chatgpt3p5o.mapper.HistoryMapper;
import com.alioth4j.chatgpt3p5o.pojo.entity.History;
import com.alioth4j.chatgpt3p5o.service.HistoryService;
import com.alioth4j.chatgpt3p5o.util.UserIdUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 搜索历史记录Service实现类
 * @author Alioth4J
 */
@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryMapper historyMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    // 增 先不放到缓存中
    @Override
    public void create(String question, String answer) {
        Long userId = UserIdUtil.getUserId();
        historyMapper.insert(userId, question, answer);
    }

    @Override
    @CacheEvict(value = RedisConfig.VALUE, key = RedisConfig.KEY_HISTORY)
    public void delete(Long id) {
        historyMapper.delete(id);
    }

    @Override
    public void deleteAll() {
        Long userId = UserIdUtil.getUserId();
        // 为了删缓存，先查得所有
        List<History> historyList = historyMapper.listAll(userId);
        for (History history : historyList) {
            Long id = history.getId();
            redisTemplate.delete("history:" + userId + id);
        }
        // 删数据库
        historyMapper.deleteAll(userId);
    }

    @Override
    @Cacheable(value = RedisConfig.VALUE, key = RedisConfig.KEY_HISTORY)
    public History list(Long id) {
        return historyMapper.list(id);
    }

    @Override
    public List<History> listAll(Integer pageNum, Integer pageSize) {
        // 直接去数据库查，不查缓存（缓存可能不全）
        PageHelper.startPage(pageNum, pageSize);
        Long userId = UserIdUtil.getUserId();
        List<History> historyList = historyMapper.listAll(userId);
        // 放到缓存中
        for (History history : historyList) {
            Long id = history.getId();
            redisTemplate.opsForValue().setIfAbsent("history:" + userId + id, history);
        }
        return historyList;
    }

}
