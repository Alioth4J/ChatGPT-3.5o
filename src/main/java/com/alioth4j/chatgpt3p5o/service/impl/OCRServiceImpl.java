package com.alioth4j.chatgpt3p5o.service.impl;

import com.alioth4j.chatgpt3p5o.service.OCRService;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
public class OCRServiceImpl implements OCRService {
    @Override
    public String doOCR(BufferedImage image) {
        Tesseract tess = new Tesseract();
        tess.setDatapath("D:\\Tesseract-OCR\\tessdata");
        tess.setLanguage("chi_sim");
        String imgText = null;
        try {
            imgText = tess.doOCR(image);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return imgText;
    }
}
