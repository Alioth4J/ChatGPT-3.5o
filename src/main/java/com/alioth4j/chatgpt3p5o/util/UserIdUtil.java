package com.alioth4j.chatgpt3p5o.util;

import com.alioth4j.chatgpt3p5o.pojo.domain.UserUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 通过Spring Security上下文获取userId
 * @author Alioth4J
 */
public class UserIdUtil {

    public static Long getUserId() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof UserUserDetails)) {
            throw new ClassCastException("principal不是UserUserDetails对象");
        }
        Long userId = ((UserUserDetails)principal).getUserId();
        return userId;
    }
}
