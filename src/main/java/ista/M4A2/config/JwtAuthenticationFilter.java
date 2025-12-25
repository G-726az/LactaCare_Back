package ista.M4A2.config;

<<<<<<< HEAD
<<<<<<< HEAD
=======
import ista.M4A2.config.authenticator.JwtTokenProvider;
>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======
import ista.M4A2.config.authenticator.JwtTokenProvider;
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
<<<<<<< HEAD
<<<<<<< HEAD
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import ista.M4A2.config.authenticator.JwtTokenProvider; 

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;  
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        
        try {
            String jwt = getJwtFromRequest(request);
            
            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
                Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
                String userType = jwtTokenProvider.getUserTypeFromToken(jwt);
                String rol = jwtTokenProvider.getRolFromToken(jwt);
                String correo = jwtTokenProvider.getCorreoFromToken(jwt);
                
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        correo, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + rol))
                );
                
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            logger.error("No se pudo establecer la autenticación del usuario en el contexto de seguridad", ex);
=======
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Filtro JWT que valida el token en cada petición
 * y establece el contexto de seguridad
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    /**
     * Lista de rutas públicas que no requieren autenticación
     */
    private static final String[] PUBLIC_PATHS = {
        "/api/auth/login",
        "/api/auth/google",
        "/api/auth/register/paciente",
        "/api/auth/complete-profile",
        "/api/auth/forgot-password",
        "/api/auth/reset-password",
        "/api/auth/empleado/forgot-password",
        "/api/auth/empleado/reset-password",
        "/api/validacion/**",
        "/api/admin/empleados/verificar-estado/**",
        "/api/admin/empleados/cambiar-password-inicial"
    };
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) 
            throws ServletException, IOException {
        
        String path = request.getRequestURI();
        
        // Permitir rutas públicas sin token
        if (isPublicPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }
        
        try {
            // 1. Extraer token del header Authorization
            String token = extractTokenFromRequest(request);
            
            if (token != null && jwtTokenProvider.validateToken(token)) {
                // 2. Extraer información del token
                Long userId = jwtTokenProvider.getUserIdFromToken(token);
                String userType = jwtTokenProvider.getUserTypeFromToken(token);
                String rol = jwtTokenProvider.getRolFromToken(token);
                String correo = jwtTokenProvider.getCorreoFromToken(token);
                
                // 3. Crear authorities (roles)
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_" + rol));
                
                // 4. Crear authentication object
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(
                        correo, // Principal (identificador del usuario)
                        null,   // Credentials (no necesarias después de autenticación)
                        authorities
                    );
                
                // 5. Agregar detalles adicionales
                authentication.setDetails(new WebAuthenticationDetailsSource()
                    .buildDetails(request));
                
                // 6. Establecer en SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            
        } catch (Exception e) {
            // Log del error (en producción usar un logger apropiado)
            System.err.println("Error al procesar token JWT: " + e.getMessage());
<<<<<<< HEAD
>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
        }
        
        filterChain.doFilter(request, response);
    }
    
<<<<<<< HEAD
<<<<<<< HEAD
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
=======
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
    /**
     * Extrae el token JWT del header Authorization
     */
    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        
        return null;
    }
    
    /**
     * Verifica si la ruta es pública (no requiere autenticación)
     */
    private boolean isPublicPath(String path) {
        for (String publicPath : PUBLIC_PATHS) {
            if (publicPath.endsWith("/**")) {
                String basePathPattern = publicPath.substring(0, publicPath.length() - 3);
                if (path.startsWith(basePathPattern)) {
                    return true;
                }
            } else if (path.equals(publicPath)) {
                return true;
            }
        }
        return false;
    }
<<<<<<< HEAD
}
>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======
}
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
