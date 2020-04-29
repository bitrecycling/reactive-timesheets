package de.bitrecycling.timeshizz.security.config;

import de.bitrecycling.timeshizz.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
class SpringSecurityConf extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/user").permitAll()
                .antMatchers("/**").hasRole("USER")
                .and()
                .formLogin();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider()
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
        
//        UserDetails user = User.withDefaultPasswordEncoder().username("timeshizz").password("timeshizz").roles("USER").build();
//        auth
//                .inMemoryAuthentication()
//                .withUser(user);
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }





//
//    @Autowired
//    UserService userService;
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }

//    @Override
//
//    protected void configure(AuthenticationManagerBuilder auth) {
//
//        auth.authenticationProvider(authenticationProvider());
//
//    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new UserService();
//    }

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("password")
//                        .roles("USER")
//                        .build();
//
//        return  UserService;
//    }
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        UserDetails user = User.withDefaultPasswordEncoder().username("timeshizz").password("timeshizz").roles("USER").build();
//        auth
//                .inMemoryAuthentication()
//                .withUser(user);
//    }
}