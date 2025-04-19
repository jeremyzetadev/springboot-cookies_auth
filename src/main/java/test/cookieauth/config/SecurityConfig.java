package test.cookieauth.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User
                .withUsername("user")
                .password("user")
                .roles("USER")
                .build();
        UserDetails admin = User
                .withUsername("admin")
                .password("admin")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception{
//        return http
//                .csrf(csrf->csrf.disable())
//                .authorizeHttpRequests(auth->{
//                        auth.requestMatchers("/").permitAll();
//                        auth.requestMatchers("/user").hasRole("USER");
//                        auth.requestMatchers("admin").hasRole("ADMIN");
//                })
//                .httpBasic(Customizer.withDefaults())
//                .build();
        return http
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/anonymous*")
                        .anonymous()
                        .requestMatchers("/login*").permitAll()
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin.loginPage("/login.html")
                        .loginProcessingUrl("/login")
                        .failureUrl("/login.html?error=true"))
                .rememberMe(rememberMe -> rememberMe.key("uniqueAndSecret").tokenValiditySeconds(600))
                .logout(logout -> logout.deleteCookies("JSESSIONID"))
                .build();
    }
}



//DEPRECATED WebSecurityConfigurerAdapter
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
//
//    public SecurityConfig(UserAuthenticationEntryPoint userAuthenticationEntryPoint,
//                          UserAuthenticationProvider userAuthenticationProvider) {
//        this.userAuthenticationEntryPoint = userAuthenticationEntryPoint;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .exceptionHandling().authenticationEntryPoint(userAuthenticationEntryPoint)
//                .and()
//                .addFilterBefore(new UsernamePasswordAuthFilter(), BasicAuthenticationFilter.class)
//                .addFilterBefore(new CookieAuthenticationFilter(), UsernamePasswordAuthFilter.class)
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().logout().deleteCookies(CookieAuthenticationFilter.COOKIE_NAME)
//                .and()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/v1/signIn", "/v1/signUp").permitAll()
//                .anyRequest().authenticated();
//    }
//
//}