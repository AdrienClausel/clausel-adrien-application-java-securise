package com.nnk.springboot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/**
 * Classe permettant de personnaliser la configuration par defaut de spring security
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    /**
     * Configuration des chaines de filtre de sécurité
     * @param httpSecurity Objet permettant de configurer la sécurité
     * @param customAuthenticationSuccessHandler Handler permettant de personnaliser la redirection après l'authentification réussie
     * @return la chaine de filtres de sécurité configurée
     * @throws Exception Exception générée si une erreur arrive pendant la configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) throws Exception {
        return httpSecurity.authorizeHttpRequests(auth -> auth
                .requestMatchers("/user/**").hasRole("ADMIN")
                .requestMatchers("/").hasRole("ADMIN")
                .requestMatchers("/app/login", "/css/**", "/app/error/**").permitAll()
                .anyRequest().authenticated()
        )
                .formLogin(form -> form
                        .loginPage("/app/login")
                        .loginProcessingUrl("/app/login")
                        .defaultSuccessUrl("/", true)
                        .successHandler(customAuthenticationSuccessHandler)
                        .failureUrl("/app/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/app-logout")
                )
                .build();
    }

    /**
     * Utilisé pour configurer le chiffrement des mots de passe
     * @return Objet permettant l'encoder les mots de passe
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
