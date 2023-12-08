package com.jojoldu.book.webservice.config.auth;

import com.jojoldu.book.webservice.domain.oAuthUser.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Spring Security 설정 활성화
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfiguration {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final UserDetailsService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/**", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/api/**", "/news/**").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    .anyRequest().authenticated() //위 antMatchers를 제외한 모든 url들은 로그인한 사용자들만 허용한다.
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                //일반 로그인
                    .formLogin()
                        .loginPage("/loginForm") //로그인 페이지 url
                        .loginProcessingUrl("/login") // 이 url을 로그인 기능을 담당
                        .defaultSuccessUrl("/") // 로그인 성공시 url
                .and()
                //Oauth 로그인
                    .oauth2Login()
                        .loginPage("/login") //로그인 페이지 url
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);
        return http.build();
    }

    // 인증 관리자 관련 설정
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }

    // 패스워드 인코더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
