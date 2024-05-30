package com.alioth4j.chatgpt3p5o.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserDTO {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    private String phone;

    private String icon;

}
