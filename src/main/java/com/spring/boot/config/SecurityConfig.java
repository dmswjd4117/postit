package com.spring.boot.config;


import com.spring.boot.domain.RoleName;
import com.spring.boot.security.FormAccessDeniedHandler;
import com.spring.boot.security.FormAuthenticationEntryPoint;
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

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

//    @Bean
//    public AccessDecisionManager accessDecisionManager(){
//        List<AccessDecisionVoter<?>> voters = new ArrayList<>();
//        voters.add(new WebExpressionVoter());
//        return new UnanimousBased(voters);
//    }
//
//    private AuthenticationProvider formAuthenticationProvider(){
//        return new FormAuthenticationProvider();
//    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests(registry -> {
                    registry
                            .antMatchers("/api/admin/**").hasRole(RoleName.ADMIN.name())
                            .anyRequest().permitAll();
                })

                .formLogin()
                .loginProcessingUrl("/login_process")
                .usernameParameter("email")
                .passwordParameter("password")

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
