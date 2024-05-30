package com.alioth4j.chatgpt3p5o.pojo.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户类
 * @author Alioth4J
 */
@Data
public class User {

    private Long id;

    private String username;

    private String password;

    private String phone;

    private String icon;

    private Date createTime;

    // 点数
    private Integer point;

    private static final long serialVersionUID = 1L;
}
