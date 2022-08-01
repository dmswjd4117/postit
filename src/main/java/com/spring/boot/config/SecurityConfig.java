package com.spring.boot.config;


import com.spring.boot.domain.RoleName;
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
                .csrf()
                .disable();

        return httpSecurity.build();
    }
}
