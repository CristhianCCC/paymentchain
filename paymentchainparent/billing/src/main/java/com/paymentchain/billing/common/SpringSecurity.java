package com.paymentchain.billing.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.PipedOutputStream;
import java.time.Duration;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    /*variable la cual contendra las url que no requeriran autentificacion*/
    private static final String[] NO_AUTH_LIST = {
            "/v3/api-docs", //
            "/configuration/ui", //
            "/swagger-resources", //
            "/configuration/security", //
            "/webjars/**", //
            "/login", //
            "/h2-console/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /*primera manera, para agregar todo en un mismo login y que al momento de loguearse tenga acceso a todo el contenido de los endpoints */
        /*http
                .authorizeHttpRequests(auth ->
                        auth.anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());

        return http.build();*/

        /* segunda manera, para personalizar los endpoints que van a tener acceso dependiendo de si esta logueado o no */

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(NO_AUTH_LIST).permitAll() //no hay necesidad de autentificar
                        .requestMatchers(HttpMethod.POST, "/billing/**").authenticated() //todas que coincidan con metodos post dentro del requestmapping de billing estaran controladas por un metodo de autentificacion
                        .requestMatchers(HttpMethod.GET, "/billing/**").hasRole("ADMIN") //todos los que sean get y sean admin dentro de requestmapping de billing podran ejecutarse
                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());
        return http.build();

    }

    //configurando el cors
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cc = new CorsConfiguration();
        cc.setAllowedHeaders(Arrays.asList("origin,Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Authorization"));
        cc.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        cc.setAllowedOrigins(Arrays.asList("/*"));
        cc.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "PUT", "PATCH"));
        cc.addAllowedOriginPattern("*");
        cc.setMaxAge(Duration.ZERO);
        cc.setAllowCredentials(Boolean.TRUE);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cc);
        return source;

    }
}

