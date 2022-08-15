package com.spring.boot.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.common.dto.ApiResult;
import com.spring.boot.common.config.ConnectionBasedVoter;
import com.spring.boot.common.util.RoleName;
import com.spring.boot.member.application.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig{
    private final FormAccessDeniedHandler formAccessDeniedHandler;
    private final FormAuthenticationEntryPoint formAuthenticationEntryPoint;

    public SecurityConfig(FormAccessDeniedHandler formAccessDeniedHandler, FormAuthenticationEntryPoint formAuthenticationEntryPoint) {
        this.formAccessDeniedHandler = formAccessDeniedHandler;
        this.formAuthenticationEntryPoint = formAuthenticationEntryPoint;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FormAuthenticationProvider formAuthenticationProvider(MemberService memberService){
        return new FormAuthenticationProvider(memberService);
    }

    @Bean
    public ConnectionBasedVoter connectionBasedVoter(){
        return new ConnectionBasedVoter();
    }
    @Bean
    public AccessDecisionManager accessDecisionManager(){
        List<AccessDecisionVoter<?>> decisionVoters = new ArrayList<>();
        decisionVoters.add(new WebExpressionVoter());
        decisionVoters.add(connectionBasedVoter());
        return new UnanimousBased(decisionVoters);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests(registry -> {
                    registry
                            .accessDecisionManager(accessDecisionManager())
                            .antMatchers("/api/admin/**").hasRole(RoleName.ADMIN.name())
                            .antMatchers("/api/post").hasAnyRole(RoleName.ADMIN.name(), RoleName.MEMBER.name())
                            .antMatchers("/api/connection/*").hasAnyRole(RoleName.MEMBER.name())
                            .anyRequest().permitAll();
                })
                .formLogin()
                .loginProcessingUrl("/login_process")
                .usernameParameter("email")
                .passwordParameter("password")
                .failureHandler((request, response, exception) -> {
                    ApiResult<?> LOGIN_FAIL_EXCEPTION = ApiResult.error("login failed", HttpStatus.NOT_FOUND);
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    writeResponse(response, LOGIN_FAIL_EXCEPTION);
                })
                .successHandler((request, response, exception) -> {
                    ApiResult<?> LOGIN_SUCCESS = ApiResult.success("login success");
                    response.setStatus(HttpServletResponse.SC_ACCEPTED);
                    writeResponse(response, LOGIN_SUCCESS);
                })

                .and()
                .exceptionHandling()
                .accessDeniedHandler(formAccessDeniedHandler) // 인가 예외
                .authenticationEntryPoint(formAuthenticationEntryPoint) // 인증 예외

                .and()
                .csrf()
                .disable();

        return httpSecurity.build();
    }

    private void writeResponse(HttpServletResponse response, ApiResult<?> result) throws IOException {
        ObjectMapper om = new ObjectMapper();
        response.setHeader("content-type", "application/json");
        response.getWriter().write(om.writeValueAsString(result));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
