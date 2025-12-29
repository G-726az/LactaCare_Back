package ista.M4A2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Configuración de seguridad de Spring Security
 * Define qué rutas son públicas y cuáles requieren autenticación
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
<<<<<<< HEAD
=======
    
    /**
     * NUEVO: Ignora completamente Spring Security para estas rutas
     * Esto evita problemas con LazyInitializationException
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
            .requestMatchers("/api/lactarios/**")
            .requestMatchers("/api/instituciones/**")
            .requestMatchers("/api/cubiculos/**");
    }
>>>>>>> fd5e5d2 (Cambios en SecurityConfig, ubicacion de dto's, y ajustes en entity y controllers para manejo de sala_lactancia e intituciones)
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Deshabilitar CSRF (no necesario para API REST con JWT)
            .csrf(csrf -> csrf.disable())
            
            // Configurar CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // Política de sesión: sin estado (stateless)
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // Configurar autorización de endpoints
            .authorizeHttpRequests(auth -> auth
                // ===== RUTAS PÚBLICAS =====
                // Autenticación y registro
                .requestMatchers("/api/auth/login").permitAll()
                .requestMatchers("/api/auth/google").permitAll()
                .requestMatchers("/api/auth/register/paciente").permitAll()
                .requestMatchers("/api/auth/complete-profile").permitAll()
                
                // Recuperación de contraseña
                .requestMatchers("/api/auth/forgot-password").permitAll()
                .requestMatchers("/api/auth/reset-password").permitAll()
                .requestMatchers("/api/auth/empleado/forgot-password").permitAll()
                .requestMatchers("/api/auth/empleado/reset-password").permitAll()
                
                // Validaciones ecuatorianas
                .requestMatchers("/api/validacion/**").permitAll()
                
                // Cambio de contraseña inicial para empleados
                .requestMatchers("/api/admin/empleados/verificar-estado/**").permitAll()
                .requestMatchers("/api/admin/empleados/cambiar-password-inicial").permitAll()
                
                // Health check
                .requestMatchers("/api/auth/health").permitAll()
                
                // ===== DESARROLLO: DESCOMENTAR PARA PERMITIR ACCESO SIN AUTH =====
                // ⚠️ SOLO PARA DESARROLLO LOCAL - COMENTAR EN PRODUCCIÓN ⚠️
                .requestMatchers("/api/empleados/**").permitAll()
                // =================================================================
                
                // ===== RUTAS PROTEGIDAS POR ROL =====
                // Solo ADMINISTRADORES pueden crear empleados
                .requestMatchers("/api/admin/**").hasRole("ADMINISTRADOR")
                
                // Doctores y Admins pueden gestionar empleados
                // ⚠️ COMENTAR ESTAS LÍNEAS SI DESCOMENTASTE LA LÍNEA DE ARRIBA
                // .requestMatchers(HttpMethod.GET, "/api/empleados/**")
                //     .hasAnyRole("DOCTOR", "ADMINISTRADOR")
                // .requestMatchers(HttpMethod.PUT, "/api/empleados/**")
                //     .hasAnyRole("DOCTOR", "ADMINISTRADOR")
                
                // Solo Admins pueden eliminar empleados
                // .requestMatchers(HttpMethod.DELETE, "/api/empleados/**")
                //     .hasRole("ADMINISTRADOR")
                
                // Gestión de roles solo para Admins
                .requestMatchers("/api/roles/**").hasRole("ADMINISTRADOR")
                
                // Pacientes pueden ver y actualizar su propio perfil
                .requestMatchers("/api/pacientes/**").hasAnyRole("PACIENTE", "DOCTOR", "ADMINISTRADOR")
                
                // Usuario autenticado puede ver su propia información
                .requestMatchers("/api/user/me").authenticated()
                .requestMatchers("/api/user/profile").authenticated()
                
                // Cualquier otra petición requiere autenticación
                .anyRequest().authenticated()
            )
            
            // Agregar filtro JWT antes del filtro de autenticación estándar
            .addFilterBefore(jwtAuthenticationFilter, 
                UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    /**
     * Configuración de CORS para permitir peticiones desde móvil y web
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Permitir orígenes específicos (en producción, especificar dominios exactos)
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        
        // Métodos HTTP permitidos
        configuration.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"
        ));
        
        // Headers permitidos
        configuration.setAllowedHeaders(Arrays.asList(
            "Authorization", 
            "Content-Type", 
            "X-Requested-With",
            "Accept",
            "Origin",
            "Access-Control-Request-Method",
            "Access-Control-Request-Headers"
        ));
        
        // Headers expuestos
        configuration.setExposedHeaders(Arrays.asList(
            "Authorization",
            "Content-Type"
        ));
        
        // Permitir credenciales
        configuration.setAllowCredentials(true);
        
        // Tiempo de cache de configuración CORS (en segundos)
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
    
    /**
     * Bean de PasswordEncoder usando BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}