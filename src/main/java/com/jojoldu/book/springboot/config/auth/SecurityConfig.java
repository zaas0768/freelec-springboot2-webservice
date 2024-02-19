package com.jojoldu.book.springboot.config.auth;

import com.jojoldu.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity      // 1
public class SecurityConfig {
// public class SecurityConfig extends WebSecurityConfigurerAdapter -> public class SecurityConfig 로 클래스 변경 extends 사용하지 않음
// WebSecurityConfigurerAdapter는 deprecated 되어, 상속을 받지 않고 모두 Bean으로 등록하여 사용하는 방식으로 변경됨 더이상 권장하지 않음

    private final CustomOAuth2UserService customOAuth2UserService;

//    기존방식
//    WebSecurityConfigurerAdapter를 상속받아 설덩을 오버라이딩 하여 사용했다.
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .headers().frameOptions().disable()
//                .and()
//                    .authorizeRequests()
//                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
//                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
//                    .anyRequest().authenticated()
//                .and()
//                    .logout()
//                        .logoutSuccessUrl("/")
//                .and()
//                    .oauth2Login()
//                        .userInfoEndpoint()
//                            .userService(customOAuth2UserService);
//    }
    
    // 개선된 방식
    @Bean
    public SecurityFilterChain newFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeHttpRequests(authorize -> authorize.antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                        .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                        .anyRequest().authenticated())
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .oauth2Login(oauth2Login -> oauth2Login.userInfoEndpoint()
                        .userService(customOAuth2UserService));

        return http.build();
        // 작성 방식이 람다 형식으로 변경되었다. 이방식을 권장한다.
        // 람다 형식으로 변경되면서 and()로 각 처리 계층에 대한 구분을 해주지 않아도 된다.
        // 처리 계증에 대한 구성 로직을 한눈에 보기 더욱 쉬워졌다.
    }

}

// @EnableWebSecurity -> Spring Security 설정들을 활성화 시켜 준다.

// csrf().disable().headers().frameOptions().disable() -> h2-console 화면을 사용하기 위해 해당 옵션들을 disable 한다.

// authorizeRequests -> URL별 권한 관리를 설정하는 옵션의 시작점, authorizeRequests가 선언되어야함 antMatchers 옵션을 사용할 수 있다.

// antMatchers -> 권한 관리 대상을 지정하는 옵션, URL, HTTP 메소드별로 관리 가능, "/"등 지정된 URL들은 permitAll() 옵션을 통해 전체 열람 권한을 주었다.

// "/api/v1/**" 주소를 가진 API는 USER 권한을 가진 사람만 가능하도록 했다.

// anyRequest -> 설정된 값들 이외 나머지 URL들을 나타낸다.

// logout().logoutSuccessUrl("/") -> 로그아웃 기능에 대한 여러 설정의 진입점이다, 로그아웃 성공지 /주소로 이동한다.

// oauth2Login -> OAuth2 로그인 기능에 대한 여러 설정의 진입점이다.

// userInfoEndpoint -> OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당합니다.

// userService -> 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록한다,
// 리소스 서버(즉, 소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시할 수 있다.
