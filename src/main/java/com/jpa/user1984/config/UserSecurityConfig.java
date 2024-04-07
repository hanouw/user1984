package com.jpa.user1984.config;

import com.jpa.user1984.security.CustomMemberSecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserSecurityConfig extends ConfigForExtend{

    private final CustomMemberSecurityService customMemberSecurityService;
    private final DataSource dataSource; // yml DB 접속 정보 설정

    @Bean
    public DaoAuthenticationProvider userAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customMemberSecurityService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // 시큐리티 통과 조건
    @Bean
    public SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception{

        http.authenticationProvider(userAuthenticationProvider());

        http.authorizeHttpRequests(request -> // anyRequest(): 어떤 요청이든 permitAll(): 다 허용
                                request.requestMatchers("/", "/signup", "/login", "/logout").permitAll()
                                .requestMatchers("/main/**").hasAnyAuthority("STATUS_USER") // authenticated는 사용자 정보를 기반으로 사용자가 인증되었는지 확인 //hasAnyRole을 할 경우에는 앞에 ROLE_~~ 로 시작해야함
                                .anyRequest().authenticated()
                );


        http.formLogin(login -> login.loginPage("/login") // 로그인 페이지는 이 페이지야~
                .loginProcessingUrl("/login")
                .usernameParameter("userID")
                .passwordParameter("userPassword")
                .defaultSuccessUrl("/")) // 로그인 처리 성공했으면
                .rememberMe(remember -> remember.userDetailsService(customMemberSecurityService)
                        .tokenRepository(memberTokenRepository())
                        .tokenValiditySeconds(3600))
                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 로그아웃 경로는 요청 경로로 지정해서(POST방식) 성공하면 / : 홈으로 돌아가라
                        .logoutSuccessUrl("/") // 홈 : /으로 돌아가라
                        .invalidateHttpSession(true)); // 세션도 지워라

        http.csrf(csrf -> csrf.disable()); // csrf 토큰을 달고 넘어가는데 disable해둠
        return http.build();
    }


    // remember-me 에서 필요 : 시큐리티 자동 로그인 토큰 관리를 위한 빈 등록
    @Bean
    public PersistentTokenRepository memberTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }


}
