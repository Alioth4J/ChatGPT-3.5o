package com.alioth4j.chatgpt3p5o.service;

import com.alioth4j.chatgpt3p5o.pojo.entity.History;

import java.util.List;

/**
 * 搜索历史记录Service
 * @author Alioth4J
 */
public interface HistoryService {

    void create(String question, String answer);

    History list(Long id);

    List<History> listAll(Integer pageNum, Integer pageSize);

    void delete(Long id);

    void deleteAll();

}
