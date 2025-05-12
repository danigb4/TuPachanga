package org.tupachanga.tupachanga.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests(authz -> authz
            .requestMatchers(
                "/",                           // Página principal (index)
                "/login",                      // Página de login personalizada
                "/register",                   // Registro
                "/css/**",                     // Recursos estaticos
                "/uploads/**",                 // Archivos subidos
                "/municipalities/by-province"  // Endpoint de municipios
            ).permitAll()
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .usernameParameter("username")
            .passwordParameter("password")
            .defaultSuccessUrl("/personalized-index", true)
            .failureUrl("/login?error=true")
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/logout")            // URL de logout
            .logoutSuccessUrl("/")           // Redirigir a la página principal después del logout
            .invalidateHttpSession(true)     // Invalida la sesión
            .deleteCookies("JSESSIONID")  //Elimina la cookie de sesión
        )
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // Sesión basada en cookies
            .invalidSessionUrl("/login")
        )
        .build();
  }
}

