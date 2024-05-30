package com.alioth4j.chatgpt3p5o.config;

import com.alioth4j.chatgpt3p5o.component.IgnoreUrls;
import com.alioth4j.chatgpt3p5o.component.JwtAuthenticationTokenFilter;
import com.alioth4j.chatgpt3p5o.component.RestAuthenticationEntryPoint;
import com.alioth4j.chatgpt3p5o.component.RestfulAccessDeniedHandler;
import com.alioth4j.chatgpt3p5o.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 配置类
 * @author Alioth4J
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    private IgnoreUrls ignoreUrls;
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 白名单放行
                .authorizeHttpRequests(registry -> {
                    // 自定义放行路径
                    for (String url : ignoreUrls.getUrls()) {
                        registry.requestMatchers(url).permitAll();
                    }
                    // 允许跨域请求的OPTIONS请求
                    registry.requestMatchers(HttpMethod.OPTIONS).permitAll();
        })
                // 其余请求需要身份认证
                .authorizeHttpRequests(registry -> {
                    registry.anyRequest().access(AuthenticatedAuthorizationManager.authenticated());
                })
                // 关闭跨站请求防护
                .csrf(AbstractHttpConfigurer::disable)
                // session生成策略为无状态会话
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 异常处理
                .exceptionHandling(configurer -> configurer.accessDeniedHandler(restfulAccessDeniedHandler).authenticationEntryPoint(restAuthenticationEntryPoint))
                // 自定义Filter
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    // 为解决循环依赖问题，在自定义Filter类上不加@Component
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userService.getUserDetailsByUsername(username);
            }
        };
    }
}
