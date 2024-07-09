package kr.co.app.config;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

//
//
//@Configuration
//@EnableWebSecurity
public class SecurityConfig {

//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .userDetailsService(userDetailsService)
//            .passwordEncoder(passwordEncoder());
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Configuration
//    public static class WebSecurityConfig {
//
//        @Autowired
//        private UserDetailsService userDetailsService;
//
//        @Bean
//        public PasswordEncoder passwordEncoder() {
//            return new BCryptPasswordEncoder();
//        }
//
//        protected void configure(HttpSecurity http) throws Exception {
//            http
//                .authorizeRequests()
//                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                    .requestMatchers("/admin/**").hasAuthority("ADMIN")
//                    .anyRequest().authenticated()
//                    .and()
//                .formLogin()
//                    .loginPage("/login")
//                    .permitAll()
//                    .and()
//                .logout()
//                    .permitAll();
//        }
//    }
}