package ista.M4A2.verificaciones;

import java.security.SecureRandom;

/**
 * Generador de contraseñas temporales seguras
 */
public class PasswordGenerator {

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%&*";
    private static final String ALL_CHARS = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARS;

    private static final SecureRandom random = new SecureRandom();
    private static final int DEFAULT_LENGTH = 12;

    /**
     * Genera una contraseña temporal segura con longitud por defecto (12
     * caracteres)
     * 
     * @return Contraseña temporal generada
     */
    public static String generateTemporaryPassword() {
        return generateTemporaryPassword(DEFAULT_LENGTH);
    }

    /**
     * Genera una contraseña temporal segura con longitud especificada
     * 
     * La contraseña generada cumple con los siguientes requisitos:
     * - Al menos una letra mayúscula
     * - Al menos una letra minúscula
     * - Al menos un dígito
     * - Al menos un carácter especial (!@#$%&*)
     * 
     * @param length Longitud de la contraseña (mínimo 8)
     * @return Contraseña temporal generada
     */
    public static String generateTemporaryPassword(int length) {
        if (length < 8) {
            throw new IllegalArgumentException("La longitud mínima de la contraseña es 8 caracteres");
        }

        StringBuilder password = new StringBuilder(length);

        // Asegurar que la contraseña tenga al menos un carácter de cada tipo
        password.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        password.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        password.append(SPECIAL_CHARS.charAt(random.nextInt(SPECIAL_CHARS.length())));

        // Rellenar el resto con caracteres aleatorios
        for (int i = 4; i < length; i++) {
            password.append(ALL_CHARS.charAt(random.nextInt(ALL_CHARS.length())));
        }

        // Mezclar los caracteres para que no sean predecibles
        return shuffleString(password.toString());
    }

    /**
     * Mezcla los caracteres de una cadena de forma aleatoria
     * 
     * @param input Cadena a mezclar
     * @return Cadena mezclada
     */
    private static String shuffleString(String input) {
        char[] characters = input.toCharArray();

        for (int i = characters.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = characters[i];
            characters[i] = characters[j];
            characters[j] = temp;
        }

        return new String(characters);
    }
}
