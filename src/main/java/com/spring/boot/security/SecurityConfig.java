package com.spring.boot.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.common.response.ApiResult;
import com.spring.boot.role.domain.RoleName;
import com.spring.boot.user.application.UserService;
import com.spring.boot.security.exception.FormAccessDeniedHandler;
import com.spring.boot.security.exception.FormAuthenticationEntryPoint;
import com.spring.boot.security.voter.ConnectionBasedVoter;
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
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.math.NumberUtils.toLong;


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
    public FormAuthenticationProvider formAuthenticationProvider(UserService userService){
        return new FormAuthenticationProvider(userService);
    }

    @Bean
    public ConnectionBasedVoter connectionBasedVoter(){
        final String regex = "^/api/post/member/(\\d+).*$";
        final Pattern pattern = Pattern.compile(regex);
        RequestMatcher requestMatcher = new RegexRequestMatcher(pattern.pattern(), null);
        return new ConnectionBasedVoter(requestMatcher, (String url)->{
            Matcher matcher = pattern.matcher(url);
            return matcher.matches() ? toLong(matcher.group(1), -1) : -1;
        });
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
                            .antMatchers("/api/post/**").hasAnyRole(RoleName.ADMIN.name(), RoleName.MEMBER.name())
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
