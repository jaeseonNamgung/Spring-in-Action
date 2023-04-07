package sia.tacocloud.tacos.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/design", "/orders")
                .hasRole("USER")
                .requestMatchers("/", "/**")
                .permitAll()
                .and()
                // formLogin: Custom login
                .formLogin()
                // loginPage: Custom login url
                .loginPage("/login")
                // 사용자가 로그인 페이지를 누른 후 로그인을 성공했을 시 이동된다.
                // 사용자가 로그인 전에 어떤 페이지에 있었는 지와 무관하게 로그인 후 지정된
                // 페이지로 이동하고 싶다면 true을 주면 된다.
                .defaultSuccessUrl("/design", true)
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .csrf();

        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration ) throws Exception {
        /*
        * WebSecurityConfigurerAdapter 가 deprecated 되면서 기존에 사용했던 AuthenticationManagerBulider 에서
        * DetailsService 와 PasswordEncoder 를 이번 변경된 설정에서는 AuthenticationManager가 빈 주입시
        * 자동으로 DetailsService, PasswordEncoder를 설정해준다.
         * */
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
    }

}
