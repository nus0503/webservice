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
                // antMatchers()메소드는 http의 모든 메소드에 대한 요청을 대상으로 한다. 만약 특정 메소드의 경로를 지정하고 싶으면 .antMatchers(HttpMethod.GET, "경로")
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/api/**", "/news/**", "/signup", "/loginForm", "/user").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    .anyRequest().authenticated() //위 antMatchers를 제외한 모든 url들은 로그인한 사용자들만 허용한다.
                .and()
                    .logout() // 로그아웃 경로 기본값은 /logout
                        .logoutSuccessUrl("/")
                .and()
                //일반 로그인
                    .formLogin()
                        .loginPage("/loginForm") //로그인 페이지 url
                        .usernameParameter("email") // 로그인 폼에서 전달하는 파라미터 이름이 시큐리티에서는 기본값으로 username과 password이기에 추가 설정
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
