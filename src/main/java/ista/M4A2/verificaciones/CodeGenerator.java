package ista.M4A2.verificaciones;

import java.security.SecureRandom;

/**
 * Generador de códigos de verificación de 6 dígitos
 * para recuperación de contraseña.
 * 
 * Utiliza SecureRandom para generar códigos aleatorios seguros.
 */
public class CodeGenerator {

    private static final SecureRandom random = new SecureRandom();
    private static final int CODE_LENGTH = 6;
    private static final int MIN_CODE = 100000; // 6 dígitos mínimo
    private static final int MAX_CODE = 999999; // 6 dígitos máximo

    /**
     * Genera un código de verificación de 6 dígitos.
     * 
     * @return String con código de 6 dígitos (ej: "123456")
     */
    public static String generateResetCode() {
        // Genera número aleatorio entre 100000 y 999999
        int code = MIN_CODE + random.nextInt(MAX_CODE - MIN_CODE + 1);
        return String.valueOf(code);
    }

    /**
     * Valida que un código tenga el formato correcto.
     * 
     * @param code Código a validar
     * @return true si el código es válido, false en caso contrario
     */
    public static boolean isValidCodeFormat(String code) {
        if (code == null || code.length() != CODE_LENGTH) {
            return false;
        }

        // Verificar que solo contenga dígitos
        return code.matches("^\\d{6}$");
    }

    /**
     * Genera múltiples códigos únicos (útil para testing).
     * 
     * @param count Cantidad de códigos a generar
     * @return Array de códigos únicos
     */
    public static String[] generateMultipleCodes(int count) {
        String[] codes = new String[count];
        for (int i = 0; i < count; i++) {
            codes[i] = generateResetCode();
        }
        return codes;
    }
}
