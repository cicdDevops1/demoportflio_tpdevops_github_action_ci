package com.example.demoportflio_tpdevops_github_action_ci.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
     private  JwtFilter jwtFilter;
     @Autowired
    private UserDetailsService userDetailsService;

    //public SecurityConfig(UserDetailsService userDetailsService) {
      //  this.userDetailsService = userDetailsService;
   // }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      return   http
                                     .csrf(customizer->customizer.disable())
                                      .authorizeHttpRequests(requests-> requests
                                      .requestMatchers("/login/**", "/register", "/oauth2/**")
                                      .permitAll()
                                      .anyRequest()
                                      .authenticated()
                                      )
                                      .oauth2Login(oauth2 -> oauth2
                                              .successHandler((request, response, authentication) -> {
                                                  //response.sendRedirect("/user");
                                                  response.setStatus(HttpServletResponse.SC_OK);
                                              })
                                       )
                                    //.httpBasic(Customizer.withDefaults())
                                       .sessionManagement(sessionManagement ->sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                       .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                                          .build();
                                        // .authorizeHttpRequests(authorizeRequests ->authorizeRequests.anyRequest().authenticated())
                                        // .formLogin(Customizer.withDefaults())

    }

   /* @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User
                .withDefaultPasswordEncoder()
                .username("macka")
                .password("macka")
                .roles("USER")
                .build();

        UserDetails user1 = User
                .withDefaultPasswordEncoder()
                .username("macka123")
                .password("macka12")
                .roles("ADMIN")
                .build();


        return new InMemoryUserDetailsManager( user, user1 );
        }
*/
   @Bean
    AuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            authProvider.setPasswordEncoder(new BCryptPasswordEncoder(14));
            authProvider.setUserDetailsService(userDetailsService);
            return authProvider;
    }
   // https://api.test.bictorys.com/paymentlink-management/v1/pay/e86e0dea-e3ab-452e-b681-05481013e147?token=pNKCSm4t1D7cvVFwfIM


 @Bean
 public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
   return config.getAuthenticationManager();
 }
}
