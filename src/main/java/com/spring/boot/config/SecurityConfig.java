package com.spring.boot.config;


import com.spring.boot.domain.RoleName;
import com.spring.boot.security.FormAccessDeniedHandler;
import com.spring.boot.security.FormAuthenticationEntryPoint;
import com.spring.boot.security.FormAuthenticationProvider;
import com.spring.boot.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


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
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests(registry -> {
                    registry
                            .antMatchers("/api/admin/**").hasRole(RoleName.ADMIN.name())
                            .antMatchers("/api/post").hasAnyRole(RoleName.ADMIN.name(), RoleName.MEMBER.name())
                            .anyRequest().permitAll();
                })

                .formLogin()
                .loginProcessingUrl("/login_process")
                .usernameParameter("email")
                .passwordParameter("password")
//                .failureHandler((request, response, exception) -> {
//                    ApiResult<?> LOGIN_FAIL_EXCEPTION = ApiResult.error("login failed", HttpStatus.NOT_FOUND);
//                    ObjectMapper om = new ObjectMapper();
//                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//                    response.setHeader("content-type", "application/json");
//                    response.getWriter().write(om.writeValueAsString(LOGIN_FAIL_EXCEPTION));
//                    response.getWriter().flush();
//                    response.getWriter().close();
//                })

                .and()
                .exceptionHandling()
                .accessDeniedHandler(formAccessDeniedHandler) // 인가 예외
                .authenticationEntryPoint(formAuthenticationEntryPoint) // 인증 예외

                .and()
                .csrf()
                .disable();

        return httpSecurity.build();
    }
}
