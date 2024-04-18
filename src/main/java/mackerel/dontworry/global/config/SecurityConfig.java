package mackerel.dontworry.global.config;

import lombok.RequiredArgsConstructor;
import mackerel.dontworry.user.domain.UserRole;
import mackerel.dontworry.user.service.OAuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsFilter corsFilter;
    private final OAuthService oAuthService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //CORS 설정
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                //Token(OAuth2.0, JWT)을 사용하는 REST API의 경우 CSRF 보호가 필요하지 않음
                .csrf(AbstractHttpConfigurer::disable)
                //요청에 대한 인가 처리 설정
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/","/oauth2/**", "/css/**", "/images/**",
                                "/js/**").permitAll() // 로그인은 누구나 가능
                        .requestMatchers("/api/v1/**").hasRole(UserRole.USER.name()) // USER만
                        .anyRequest().authenticated()
                )

                //OAuth 2.0 기반 인증을 처리하기위해 Provider와의 연동을 지원
                //oAuthService - OAuth 2.0 인증이 처리되는데 사용될 사용자 서비스
                .oauth2Login(oauth2Login ->
                    oauth2Login.userInfoEndpoint(userInfoEndpointConfig ->
                            userInfoEndpointConfig.userService(oAuthService)))
        ;

        return http.build();
    }
}