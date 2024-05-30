package com.alioth4j.chatgpt3p5o.pojo.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode
@Builder
public class Resource {

    private Long id;

    private Date createTime;

    private String name;

    private String url;

    private String description;

    private Long categoryId;
}
