package com.alioth4j.chatgpt3p5o.controller;

import com.alioth4j.chatgpt3p5o.common.CommonResult;
import com.alioth4j.chatgpt3p5o.service.AiService;
import com.alioth4j.chatgpt3p5o.service.MinIOService;
import com.alioth4j.chatgpt3p5o.service.OCRService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 主控制器
 * @author Alioth4J
 */
@RestController
public class MainController {

    @Autowired
    private MinIOService minIOService;

    @Autowired
    private OCRService ocrService;

    @Autowired
    private AiService aiService;

    @PostMapping("/uploadAndProcess")
    public CommonResult<String> uploadAndProcess(@NotNull @RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return CommonResult.failed("请上传文件");
        }
        // 存储文件到MinIO
        String url = minIOService.upload(file);
        // 通过MinIO提供的url获取文件
        if ("".equals(url)) {
            return CommonResult.failed();
        }
        InputStream is = new URL(url).openStream();
        BufferedImage image = ImageIO.read(is);
        // OCR
        String imgText = ocrService.doOCR(image);
        // AI
        String answer = aiService.getAnswer(imgText);
        // 返回结果
        return CommonResult.success(answer);
    }

    @PostMapping("/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("成功", HttpStatus.OK);
    }
}
