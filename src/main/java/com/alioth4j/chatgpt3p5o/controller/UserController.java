package com.alioth4j.chatgpt3p5o.controller;

import com.alioth4j.chatgpt3p5o.common.CommonResult;
import com.alioth4j.chatgpt3p5o.pojo.dto.UserDTO;
import com.alioth4j.chatgpt3p5o.pojo.dto.UserLoginDTO;
import com.alioth4j.chatgpt3p5o.pojo.entity.User;
import com.alioth4j.chatgpt3p5o.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @param userDTO
     * @return
     */
    @PostMapping("/register")
    public CommonResult<User> register(@RequestBody @Validated UserDTO userDTO) {
        User user = userService.register(userDTO);
        if (user == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(user);
    }

    @PostMapping("/login")
    public CommonResult login(@RequestBody @Validated UserLoginDTO userLoginDTO) {
        String token = userService.login(userLoginDTO);
        if (token == null) {
            return CommonResult.failed("用户名或密码错误");
        }
        Map<String, String> tokenMap  = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @GetMapping("/info")
    public CommonResult getUserInfo(Principal principal) {
        if (principal == null) {
            return CommonResult.unauthorized();
        }
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        Map<String, Object> info = new HashMap<>();
        info.put("id", user.getId());
        info.put("username", user.getUsername());
        info.put("phone", user.getPhone());
        info.put("icon", user.getIcon());
        info.put("createTime", user.getCreateTime());
        info.put("point", user.getPoint());
        return CommonResult.success(info);
    }
}
