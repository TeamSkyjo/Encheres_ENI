package fr.tp.eni.encheresskyjo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {
    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT pseudo, mot_de_passe, 1 FROM UTILISATEURS WHERE pseudo = ?;");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT pseudo, role FROM UTILISATEURS INNER JOIN ROLES ON administrateur = is_admin WHERE pseudo = ?;");

        return jdbcUserDetailsManager;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> {
//                    auth
//                            .requestMatchers(HttpMethod.GET, "/films").permitAll()
//                            .requestMatchers(HttpMethod.GET, "/inscription").permitAll()
//                            .requestMatchers(HttpMethod.POST, "/inscription").permitAll()
//                            .requestMatchers("/").permitAll()
//                            .requestMatchers("/css/*").permitAll()
//                            .requestMatchers("/images/*").permitAll()
//                            .anyRequest().authenticated();
                    auth.requestMatchers(HttpMethod.POST, "/inscription").permitAll();
                    auth.anyRequest().permitAll();
                }
        );

        // Use default Spring login form
//        http.formLogin(Customizer.withDefaults());

        // Use personalized login form
        http.formLogin(login -> {
            login.loginPage("/login");
            login.defaultSuccessUrl("/login_success");
            login.permitAll();
        });

        http.logout(logout -> {
            logout.invalidateHttpSession(true);
            logout.deleteCookies("JSESSIONID");
            logout.clearAuthentication(true);
            logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
            logout.logoutSuccessUrl("/");
        });

        return http.build();
    }
}
