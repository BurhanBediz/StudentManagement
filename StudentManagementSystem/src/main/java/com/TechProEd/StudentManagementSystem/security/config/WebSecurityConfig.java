package com.TechProEd.StudentManagementSystem.security.config;

import com.TechProEd.StudentManagementSystem.security.jwt.AuthEntryPointJwt;
import com.TechProEd.StudentManagementSystem.security.jwt.AuthTokenFilter;
import com.TechProEd.StudentManagementSystem.security.service.StudentDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final StudentDetailsServiceImpl studentDetailsService;
    private final AuthEntryPointJwt authEntryPointJwt;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter(){

        return new AuthTokenFilter();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(studentDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().and().cors().disable().exceptionHandling().authenticationEntryPoint(authEntryPointJwt).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
                .antMatchers("/sms/api/student/**").permitAll().anyRequest().authenticated();
        http.csrf().and().cors().disable().exceptionHandling().authenticationEntryPoint(authEntryPointJwt)
                .and().sessionManagement().
                sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().antMatcher("/sms/api/register")
                .antMatcher("/sms/api/login");
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers();
    }
}
