package com.alioth4j.chatgpt3p5o.service;

import com.alioth4j.chatgpt3p5o.pojo.dto.UserDTO;
import com.alioth4j.chatgpt3p5o.pojo.dto.UserLoginDTO;
import com.alioth4j.chatgpt3p5o.pojo.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    User register(UserDTO userDTO);

    String login(UserLoginDTO userLoginDTO);

    User getUserByUsername(String username);

    UserDetails getUserDetailsByUsername(String username);
}
