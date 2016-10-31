package me.faithfull;

import lombok.extern.slf4j.Slf4j;
import me.faithfull.dao.PrincipalRepository;
import me.faithfull.domain.Principal;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.*;

/**
 * @author Will Faithfull
 */
@Configuration
@Slf4j
public class SecurityConfiguration {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    UserDetailsService userDetailsService(final PrincipalRepository principalRepository) {
        return username -> {
            Principal principal = principalRepository.findOne(username);
            if(principal == null)
                throw new UsernameNotFoundException("No such username: " + username);

            return new User(principal.getUsername(),
                    principal.getPassword(),
                    principal.isAccountNonExpired(),
                    principal.isAccountNonLocked(),
                    principal.isCredentialsNonExpired(),
                    principal.isAccountNonLocked(),
                    toAuthorities(principal));
        };
    }

    static Collection<GrantedAuthority> toAuthorities(Principal principal) {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for(String authority : principal.getAuthorities()) {
            authorityList.add(new SimpleGrantedAuthority(authority));
        }
        return authorityList;
    }

    @Bean(name = "daoAuthenticationProvider")
    AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder encoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(encoder);
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    @Bean
    AuthenticationManager authManager(@Qualifier("daoAuthenticationProvider") AuthenticationProvider authenticationProvider) throws Exception {
        return new ProviderManager(Arrays.asList(authenticationProvider));
    }

    @Configuration
    @Profile("!development")
    static class ProductionSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.httpBasic().realmName("Warranty")
                    .and().authorizeRequests().anyRequest().authenticated()
                    .and().csrf().csrfTokenRepository(new CookieCsrfTokenRepository().withHttpOnlyFalse());
        }

    }

    @Configuration
    @Profile("development")
    static class DevelopmentSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.httpBasic().realmName("Warranty")
                    .and().authorizeRequests().anyRequest().authenticated()
                    .and().csrf().csrfTokenRepository(new CookieCsrfTokenRepository().withHttpOnlyFalse())
                    .and().cors().configurationSource(corsConfigurationSource());
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

            configuration.setAllowCredentials(true);
            configuration.addAllowedOrigin("*");
            configuration.addAllowedHeader("*");
            configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","PATCH","DELETE"));

            source.registerCorsConfiguration("/**", configuration);
            return source;
        }

    }

    @Value("${bootstrap.username}")
    String bootstrapUser;

    @Value("${bootstrap.password}")
    String bootstrapPass;

    @Value("${bootstrap.authorities}")
    List<String> bootstrapAuthorities;

    @Bean
    @Profile("bootstrap")
    CommandLineRunner bootstrap(PrincipalRepository principalRepository, PasswordEncoder encoder) {
        return args -> {
            log.warn("Bootstrapping user: {}", bootstrapUser);
              Principal principal = new Principal(
                      bootstrapUser,
                      encoder.encode(bootstrapPass),
                      true, true, true, true,
                      new HashSet<>(bootstrapAuthorities));

            principalRepository.save(principal);
        };
    }
}
