package home.javacodes.securingwebapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                                        .antMatchers("/", "home", "logout").permitAll()
                                        .anyRequest().authenticated()
                                        .and()
                    .formLogin().loginPage("/login").permitAll()
                                                                .and()
                    .logout().permitAll();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        /*First, Using PasswordEncoder instantiate passwordEncoder to encrypt the password "user@dmin" to a password {bcrypt} string
        *then paste it into .password("{bcrypt}) when this code is placed in production
            PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            String pass = passwordEncoder.encode("user@dmin");
            System.out.println(pass);
         */
        UserDetails user = User.withUsername("admin").password("{bcrypt}$2a$10$Kvi78XnkyvivGhEsdgnSNezaIPrhSVmjpayJCpQ6glcFV699hzZ3i").roles("USER").build();

        return new InMemoryUserDetailsManager(user);

    }
}

/* Notes:
The WebSecurityConfig class is annotated with @EnableWebSecurity to enable Spring Security’s web security support and provide the Spring MVC integration.
It also extends WebSecurityConfigurerAdapter and overrides a couple of its methods to set some specifics of the web security configuration.

The configure(HttpSecurity) method defines which URL paths should be secured and which should not. Specifically, the / and /home paths are configured to
not require any authentication. All other paths must be authenticated.

When a user successfully logs in, they are redirected to the previously requested page that required authentication. There is a custom /login page
(which is specified by loginPage()), and everyone is allowed to view it.

The userDetailsService() method sets up an in-memory user store with a single user. That user is given a user name of user,
a password of password, and a role of USER.
 */