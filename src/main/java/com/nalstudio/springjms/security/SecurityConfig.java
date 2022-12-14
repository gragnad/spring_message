package com.nalstudio.springjms.security;


import io.netty.handler.codec.http.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Resource(name = "userDetailsService")
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST.toString(), "/api/ingredients").permitAll()
                .antMatchers("design", "/orders/**", "/produce/**").permitAll()
                        .antMatchers("/ingredients/**").permitAll()
                        .antMatchers("ingredients").permitAll()
                        .antMatchers("/**").access("permitAll")
                        .antMatchers("/swagger-ui.html").access("permitAll")
                        .antMatchers("/**").access("permitAll")
                        .and()
                                .formLogin()
                                        .loginPage("/login")
                                            //.defaultSuccessUrl("????????? ?????????")
                                            //.failureUrl("????????? ?????????")
                        .and().httpBasic().realmName("Taco cloud")
                        .and().logout().logoutSuccessUrl("/")
                        .and().csrf()
                            .ignoringAntMatchers("/h2-console/**",
                                    "/ingredients",
                                    "/ingredients/**",
                                    "/design",
                                    "/orders/**",
                                    "/api/**",
                                    "/produce/**")
                        .and().headers().frameOptions().sameOrigin();
        //super.configure(http);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //????????? ?????? ????????? ?????????
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

        /* LDAP ?????? ????????? ?????????
        auth.ldapAuthentication()
                .userSearchBase("ou=people")
                .userSearchFilter("(uid={0})")
                .groupSearchBase("ou=groups")
                .groupSearchFilter("(member={0})")
                .contextSource()
                .root("dc=tacocloud,dc=com")
                .ldif("classpath:users.ldif")
                .and()
                .passwordCompare()
                .passwordEncoder(new BCryptPasswordEncoder())
                .passwordAttribute("userPasscode");
        */

        /* JDBC ????????? ????????? ?????????
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username, password, enabled from users " +
                                "where username=?")
                .authoritiesByUsernameQuery(
                        "select username, authority from authorities " +
                                "where username=?")
                .passwordEncoder(new NoEncodingPasswordEncoder());
                // .passwordEncoder(new BCryptPasswordEncoder());
        */

        /* ???????????? ?????? ????????? ????????? ??????
        auth.inMemoryAuthentication()
                .withUser("user1") // ????????? ?????? ??????
                .password("{noop}password1") // ???????????? ??????
                .authorities("ROLE_USER") // ?????? ??????
                .and()
                .withUser("user2")
                .password("{noop}password2")
                .authorities("ROLE_USER");
         */
        //super.configure(auth);
    }
}
