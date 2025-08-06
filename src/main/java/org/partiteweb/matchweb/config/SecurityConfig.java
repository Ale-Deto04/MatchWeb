package org.partiteweb.matchweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@ComponentScan("org.partiteweb.matchweb")
public class SecurityConfig {

    public static final String TEAM_ID = "03";
    public static final String USER = "USER#" + TEAM_ID;
    public static final String MODERATOR = "MODERATOR#" + TEAM_ID;
    public static final String ADMIN = "ADMIN#" + TEAM_ID;

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.formLogin(config -> config.loginPage("/login").defaultSuccessUrl("/dashboard").failureForwardUrl("/login_error"));

        http.authorizeHttpRequests(auth -> auth.requestMatchers("/dashboard").authenticated()
                                         .requestMatchers("/userDashboard").hasAnyRole(MODERATOR, USER)
                                         .requestMatchers("/perform_add").hasAnyRole(MODERATOR, USER)
                                         .requestMatchers("/change_password").authenticated()
                                         .requestMatchers("/confirm_logout").authenticated()
                                         .requestMatchers("/account").authenticated()
                                         .requestMatchers("/calendar").hasAnyRole(MODERATOR, USER)
                                         .requestMatchers("/addRating").hasAnyRole(MODERATOR, USER)
                                         .requestMatchers("/play").hasAnyRole(MODERATOR, USER)
                                         .requestMatchers("/results").hasAnyRole(MODERATOR, USER)
                                         .requestMatchers("/adminDashboard").hasRole(ADMIN)
                                         .requestMatchers("/users").hasRole(ADMIN)
                                         .requestMatchers("/scoreboard").hasRole(ADMIN)
                                         .requestMatchers("/promote").hasRole(ADMIN)
                                         .requestMatchers("/podium").hasRole(ADMIN)
                                         .anyRequest().permitAll());

        http.logout(config -> config.logoutUrl("/perform_logout").logoutSuccessUrl("/logout"));

        return http.build();
    }
}
