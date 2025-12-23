package ista.M4A2.config;
/**
 * Excepción personalizada para errores de autenticación
 * Permite enviar códigos de error específicos que la app móvil y web pueden interpretar
 * 
 * CÓDIGOS DE ERROR DEFINIDOS:
 * - USUARIO_NO_REGISTRADO: Usuario no existe en el sistema
 * - METODO_INCORRECTO: Intenta login tradicional pero su cuenta es de Google (o viceversa)
 * - PASSWORD_TEMPORAL: Empleado debe cambiar su contraseña temporal
 * - CREDENCIALES_INCORRECTAS: Contraseña incorrecta
 * - GOOGLE_TOKEN_INVALIDO: Token de Google no válido
 * - CEDULA_DUPLICADA: La cédula ya está registrada
 * - CORREO_DUPLICADO: El correo ya está registrado
 * - NO_AUTORIZADO: No tiene permisos para realizar la acción
 * - USUARIO_NO_ENCONTRADO: No se encontró el usuario
 * - GOOGLE_ERROR: Error general con autenticación de Google
 */
public class AuthException extends RuntimeException {
    
    private final String errorCode;
    private final String additionalInfo;
    
    /**
     * Constructor básico con código de error y mensaje
     * @param errorCode Código identificador del error (ej: "USUARIO_NO_REGISTRADO")
     * @param message Mensaje descriptivo para el usuario
     */
    public AuthException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.additionalInfo = null;
    }
    
    /**
     * Constructor con información adicional
     * @param errorCode Código identificador del error
     * @param message Mensaje descriptivo para el usuario
     * @param additionalInfo Información adicional (ej: correo del usuario)
     */
    public AuthException(String errorCode, String message, String additionalInfo) {
        super(message);
        this.errorCode = errorCode;
        this.additionalInfo = additionalInfo;
    }
    
    /**
     * Obtiene el código de error
     * @return Código del error (ej: "USUARIO_NO_REGISTRADO")
     */
    public String getErrorCode() {
        return errorCode;
    }
    
    /**
     * Obtiene información adicional del error
     * @return Información adicional o null si no existe
     */
    public String getAdditionalInfo() {
        return additionalInfo;
    }
}