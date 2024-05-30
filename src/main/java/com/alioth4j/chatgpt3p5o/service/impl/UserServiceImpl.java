package com.alioth4j.chatgpt3p5o.service.impl;

import com.alioth4j.chatgpt3p5o.mapper.UserMapper;
import com.alioth4j.chatgpt3p5o.pojo.domain.Resource;
import com.alioth4j.chatgpt3p5o.pojo.domain.UserUserDetails;
import com.alioth4j.chatgpt3p5o.pojo.dto.UserDTO;
import com.alioth4j.chatgpt3p5o.pojo.dto.UserLoginDTO;
import com.alioth4j.chatgpt3p5o.pojo.entity.User;
import com.alioth4j.chatgpt3p5o.service.UserService;
import com.alioth4j.chatgpt3p5o.util.JwtTokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public User register(UserDTO userDTO) {
        // 检验username是否已存在
        User existUsernameUser = userMapper.selectByUsername(userDTO.getUsername());
        if (existUsernameUser != null) {
            return null;
        }
        // 检验密码是否符合条件
        if (!isValid(userDTO.getPassword())) {
            return null;
        }
        // 设置User
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setCreateTime(new Date());
        user.setPoint(0);
        // 加密密码
        String encodePassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encodePassword);
        // 存入数据库
        userMapper.insert(user);
        return user;
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) {
        String token = null;
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();
        UserDetails userDetails = getUserDetailsByUsername(username);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new RuntimeException("账号或密码错误");
        }
        // 设置Security上下文
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成jwt token
        token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }

    @Override
    public UserDetails getUserDetailsByUsername(String username) {
        User user = getUserByUsername(username);
        if (user != null) {
            List<Resource> resourceList = getResourceList(username);
            return new UserUserDetails(user, resourceList);
        }
        throw new UsernameNotFoundException("未找到该用户");
    }

    // TODO 目前对所有的用户都是这样
    private List<Resource> getResourceList(String username) {
        List<Resource> resourceList = new ArrayList<>();
        resourceList.add(Resource.builder()
                .id(1L)
                .name("all")
                .url("/**")
                .build());
        return resourceList;
    }

    @Override
    public User getUserByUsername(String username) {
        // TODO 先从缓存中取，缓存中没有从数据库中查，并放回缓存

        // 缓存中没有从数据库中取
        User user = userMapper.selectByUsername(username);
        return user;
    }

    /**
     * 判断设置的密码是否符合条件
     * 6-16位，同时含有字母和数字
     * @param password
     * @return
     */
    private boolean isValid(String password) {
        int n = password.length();
        if (n < 6 || n > 16) {
            return false;
        }
        int numSize = 0, otherSize = 0;
        for (int i = 0; i < n; i++) {
            if (password.charAt(i) >= '0' && password.charAt(i) <= '9') {
                numSize++;
            } else {
                otherSize++;
            }
        }
        if (numSize == 0 || otherSize == 0) {
            return false;
        }
        return true;
    }
}
