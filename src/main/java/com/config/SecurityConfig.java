package com.config;

import com.security.MyBasicAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.constant.ValidationConstants.ADMIN;
import static com.constant.ValidationConstants.MODERATOR;
import static com.constant.ValidationConstants.USER;

/**
 * This class {@link SecurityConfig}
 * which provides a security configuration.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true)
@ComponentScan(basePackages = "com")
@Import(value = EncoderConfig.class)
public class SecurityConfig
        extends WebSecurityConfigurerAdapter {


    /**
     * This is field {@link BCryptPasswordEncoder} it helps
     * to encryption data.
     */
    private final BCryptPasswordEncoder encoder;


    /**
     * This is field {@link org.springframework
     * .security.core.userdetails.UserDetailsService} for take
     * details about such user.
     */
    private final UserDetailsService userDetailsService;


//    /**
//     * This is field {@link MyBasicAuthenticationEntryPoint} for
//     * create basic authentication entry points.
//     */
//    private MyBasicAuthenticationEntryPoint authenticationEntryPoint;


    /**
     * This is constructor {@link SecurityConfig}.
     * It injects two classes such as:
     * {@link BCryptPasswordEncoder}
     * {@link UserDetailsService}.
     * {@link MyBasicAuthenticationEntryPoint}.
     *
     * @param encoderPassword {@link BCryptPasswordEncoder}.
     * @param userDetails     {@link UserDetailsService}.
     *                        ?@param authenticationEntryPoints {@link MyBasicAuthenticationEntryPoint}.
     */
    @Autowired
    public SecurityConfig(final BCryptPasswordEncoder encoderPassword,
                          @Qualifier("myUserDetailsService") final
                          UserDetailsService userDetails /* ,
                         final MyBasicAuthenticationEntryPoint
                                  authenticationEntryPoints*/) {
        this.encoder = encoderPassword;
        this.userDetailsService = userDetails;
//        this.authenticationEntryPoint = authenticationEntryPoints;
    }


    /**
     * This is configure(AuthenticationManagerBuilder auth) method.
     * It is provide authentication and registration for
     * securing application.
     *
     * @param auth AuthenticationManagerBuilder auth.
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider());
//        auth.userDetailsService(userDetailsService)
//                .passwordEncoder(encoder);

    }


    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider =
                new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder);
        return authProvider;

    }


    /**
     * This is configure(HttpSecurity http) method.
     * It allows configuring web based security
     * for specific http requests.
     *
     * @param http {@link HttpSecurity}.
     * @throws Exception can throw exception.
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic()
//                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/myapp/author/authors")
                .permitAll()
                .antMatchers("/myapp/login*")
                .permitAll()
                .antMatchers()
                .authenticated()
                .antMatchers(HttpMethod.GET,
                        "/myapp/announcement/announcements/{id}",
                        "/myapp/announcement/announcements/"
                                + "{id}/get-by-heading-id",
                        "/myapp/announcement/announcements/filter-by",
                        "/myapp/announcements/filter-by-revelation",
                        "/myapp/announcements/announcements/pagination",
                        "/myapp/author/authors/{id}",
                        "/myapp/heading/headings/{id}",
                        "/myapp/heading/headings"
                                + "/get-announce-from-some-headings",
                        "/myapp/suitableAd/suitable-ads/{id}"
                ).permitAll()
                .antMatchers(HttpMethod.POST,
                        "/myapp/announcement/announcements",
                        "/myapp/suitableAd/suitable-ads"
                ).hasAnyRole(USER, ADMIN, MODERATOR)
                .antMatchers(HttpMethod.PUT,
                        "/myapp/announcement/announcements",
                        "/myapp/author/authors",
                        "/myapp/suitableAd/suitable-ads"
                ).hasAnyRole(USER, ADMIN, MODERATOR)
                .antMatchers(HttpMethod.DELETE,
                        "/myapp/announcement/announcements/{id}",
                        "/myapp/announcement/announcements/{id}/delete-by-id",
                        "/myapp/author/authors/{id}",
                        "/myapp/author/authors/{id}/"
                                + "delete-announcements-by-author-id",
                        "/myapp/suitableAd/suitable-ads/{id}"
                ).hasAnyRole(USER, ADMIN, MODERATOR)
                .antMatchers(HttpMethod.POST, "/myapp/role/roles")
                .hasAnyRole(ADMIN)
                .and()
                .formLogin()
                .defaultSuccessUrl("/myapp/author/authors", true)
                .and()
                .logout()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
    }
}
