package com.alioth4j.chatgpt3p5o.service;

import org.springframework.web.multipart.MultipartFile;

public interface MinIOService {
    String upload(MultipartFile file);
}
