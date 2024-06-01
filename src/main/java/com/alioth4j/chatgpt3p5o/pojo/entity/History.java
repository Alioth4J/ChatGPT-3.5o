package com.alioth4j.chatgpt3p5o.pojo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class History {

    private Long id;

    private Long userId;

    private String question;

    private String answer;

    private Date createDate;

}
