package com.alioth4j.chatgpt3p5o.service.impl;

import com.alioth4j.chatgpt3p5o.mapper.HistoryMapper;
import com.alioth4j.chatgpt3p5o.pojo.entity.History;
import com.alioth4j.chatgpt3p5o.service.HistoryService;
import com.alioth4j.chatgpt3p5o.util.UserIdUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public void create(String question, String answer) {
        Long userId = UserIdUtil.getUserId();
        historyMapper.insert(userId, question, answer);
    }

    @Override
    public History list(Long id) {
        return historyMapper.list(id);
    }

    @Override
    public List<History> listAll(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Long userId = UserIdUtil.getUserId();
        return historyMapper.listAll(userId);
    }

    @Override
    public void delete(Long id) {
        historyMapper.delete(id);
    }

    @Override
    public void deleteAll() {
        Long userId = UserIdUtil.getUserId();
        historyMapper.deleteAll(userId);
    }

}
