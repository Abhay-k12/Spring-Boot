package net.emgineeringdigest.journalApp.config;

import net.emgineeringdigest.journalApp.service.CustomUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private CustomUserServiceImpl userDetailService;

    /* Security filter chain : used instead of protected void configure(HttpSecurity http) throws Exception {}*/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/journal/**","/user/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults());

        http.csrf(AbstractHttpConfigurer::disable);   //For stateless application it is okay to have csrf disable.
        /*
            CSRF : cross site request forgery
            when csrf is enabled then the spring expect that with the request we'll send a token.
            If that token is not present then it'll respond with forbidden.

            Example: I've logged into my bank site as well as into malicious site, which means
            session is created and cookies have information, then if csrf is disabled then the malicious site
            can send transaction request to the logged bank site, and as you're logged in then it will think it's triggered
            by you and will result into money loss.
                So enabling csrf will include a token mechanism to avoid the cross site request forgery.

         */
        return http.build();
    }

    /* Authentication manager : used instead of protected void configure(AuthenticationManagerBuilder auth) throws Exception {} */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}



/*
     -------Deprecated Version used in old spring--------
    public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserServiceImpl userDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatcher("/hello").permitAll()
                    .anyRequest().authenticated()
                .and()
                .formLogin();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
 */

