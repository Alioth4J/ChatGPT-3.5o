package com.alioth4j.chatgpt3p5o.service.impl;

import com.alioth4j.chatgpt3p5o.service.AiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AiServiceImpl implements AiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AiServiceImpl.class);

    private final ChatClient chatClient;

    @Autowired
    public AiServiceImpl(ChatClient chatClient) {
        this.chatClient = chatClient;
    }
    @Override
    public String getAnswer(String imgText) {
        String answer = chatClient.call(imgText);
        LOGGER.info("调用AI获取答案成功");
        return answer;
    }
}
