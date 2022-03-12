package cn.cdipcc.aerolite.server.config.security;

import cn.cdipcc.aerolite.server.exception.SecurityAccessDeniedHandler;
import cn.cdipcc.aerolite.server.exception.SecurityEntryPoint;
import cn.cdipcc.aerolite.server.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private AuthService authService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public SecurityEntryPoint securityEntryPoint() {
        return new SecurityEntryPoint();
    }

    @Bean
    public SecurityAccessDeniedHandler securityAccessDeniedHandler() {
        return new SecurityAccessDeniedHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(authService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security.cors()
                .and().csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/**").access("@authService.hasPermission(request, authentication)")
                .and().addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling()
                .authenticationEntryPoint(securityEntryPoint())
                .accessDeniedHandler(securityAccessDeniedHandler());
    }
}
