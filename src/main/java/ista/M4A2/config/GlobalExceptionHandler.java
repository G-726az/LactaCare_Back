package ista.M4A2.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja tu excepción personalizada AuthException.
     * Define el Código HTTP según el errorCode interno.
     */
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(AuthException ex) {

        HttpStatus status;

        switch (ex.getErrorCode()) {
            case "USUARIO_NO_REGISTRADO":
            case "USUARIO_NO_ENCONTRADO":
            case "ROL_NO_ENCONTRADO":
                status = HttpStatus.NOT_FOUND; // 404
                break;

            case "CREDENCIALES_INCORRECTAS":
            case "GOOGLE_TOKEN_INVALIDO":
            case "TEMPORARY_PASSWORD_INVALID":
                status = HttpStatus.UNAUTHORIZED; // 401
                break;

            case "PASSWORD_TEMPORAL":
            case "REQUIRE_PASSWORD_CHANGE":
                // 403: El frontend detecta esto y redirige a "Cambiar Contraseña"
                status = HttpStatus.FORBIDDEN;
                break;

            case "METODO_INCORRECTO":
            case "CEDULA_DUPLICADA":
            case "CORREO_DUPLICADO":
            case "GOOGLE_ID_CONFLICT":
                status = HttpStatus.CONFLICT; // 409 (Conflicto de estado)
                break;

            case "NO_AUTORIZADO":
            case "NO_AUTORIZADO_CONTACTAR_SOPORTE":
            case "ACCESO_DENEGADO":
                status = HttpStatus.FORBIDDEN; // 403
                break;

            case "CUENTA_SOLO_GOOGLE":
            case "NO_REQUIERE_CAMBIO":
                status = HttpStatus.BAD_REQUEST; // 400
                break;

            default:
                status = HttpStatus.BAD_REQUEST; // 400
        }

        ErrorResponse response = new ErrorResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(status.value());
        response.setError(ex.getErrorCode()); // Importante para el frontend
        response.setMessage(ex.getMessage());

        if (ex.getAdditionalInfo() != null) {
            Map<String, String> details = new HashMap<>();
            details.put("info", ex.getAdditionalInfo());
            response.setDetails(details);
        }

        return ResponseEntity.status(status).body(response);
    }

    /**
     * Maneja errores de validación (@Valid, @NotBlank, etc.)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse response = new ErrorResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setError("ERROR_VALIDACION");
        response.setMessage("Error en los datos enviados");
        response.setDetails(errors);

        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Maneja errores de Spring Security (cuando @PreAuthorize falla)
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setError("ACCESO_DENEGADO");
        response.setMessage("No tienes permisos para realizar esta acción.");

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    /**
     * Maneja credenciales inválidas generadas por Spring Security internamente
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setError("CREDENCIALES_INCORRECTAS");
        response.setMessage("Correo o contraseña incorrectos");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    /**
     * Maneja errores 404 - Endpoint no encontrado
     */
    @ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(org.springframework.web.servlet.NoHandlerFoundException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setError("ENDPOINT_NO_ENCONTRADO");
        response.setMessage("El endpoint solicitado no existe: " + ex.getRequestURL());

        Map<String, String> details = new HashMap<>();
        details.put("metodo", ex.getHttpMethod());
        details.put("url", ex.getRequestURL());
        response.setDetails(details);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Maneja errores 405 - Método HTTP no permitido
     */
    @ExceptionHandler(org.springframework.web.HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowed(
            org.springframework.web.HttpRequestMethodNotSupportedException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
        response.setError("METODO_NO_PERMITIDO");
        response.setMessage("El método " + ex.getMethod() + " no está permitido para este endpoint.");

        Map<String, String> details = new HashMap<>();
        details.put("metodoUsado", ex.getMethod());
        if (ex.getSupportedMethods() != null) {
            details.put("metodosPermitidos", String.join(", ", ex.getSupportedMethods()));
        }
        response.setDetails(details);

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    /**
     * Maneja errores de tipo de contenido no soportado
     */
    @ExceptionHandler(org.springframework.web.HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedMediaType(
            org.springframework.web.HttpMediaTypeNotSupportedException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
        response.setError("TIPO_CONTENIDO_NO_SOPORTADO");
        response.setMessage("El tipo de contenido no es soportado. Use 'application/json'.");

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(response);
    }

    /**
     * Maneja errores de parámetros faltantes
     */
    @ExceptionHandler(org.springframework.web.bind.MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParams(
            org.springframework.web.bind.MissingServletRequestParameterException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setError("PARAMETRO_FALTANTE");
        response.setMessage("Falta el parámetro requerido: " + ex.getParameterName());

        Map<String, String> details = new HashMap<>();
        details.put("parametro", ex.getParameterName());
        details.put("tipo", ex.getParameterType());
        response.setDetails(details);

        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Manejo general de cualquier otro error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ex.printStackTrace(); // Solo para logs en servidor

        ErrorResponse response = new ErrorResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setError("ERROR_INTERNO");
        response.setMessage("Ha ocurrido un error inesperado en el servidor.");

        // En desarrollo, puedes agregar más detalles
        Map<String, String> details = new HashMap<>();
        details.put("tipo", ex.getClass().getSimpleName());
        response.setDetails(details);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}