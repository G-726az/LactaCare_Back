package ista.M4A2.config;

import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

@Component
public class EcuadorianValidator {

    /**
     * Valida una cédula ecuatoriana de 10 dígitos
     * Algoritmo de validación según estándares ecuatorianos
     */
    public boolean validarCedula(String cedula) {
        if (cedula == null || !cedula.matches("\\d{10}")) {
            return false;
        }

        // Los dos primeros dígitos deben corresponder a una provincia válida (01-24)
        int provincia = Integer.parseInt(cedula.substring(0, 2));
        if (provincia < 1 || provincia > 24) {
            return false;
        }

        // El tercer dígito debe ser menor a 6 (para personas naturales)
        int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
        if (tercerDigito > 5) {
            return false;
        }

        // Validación del dígito verificador usando el algoritmo módulo 10
        int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
        int suma = 0;

        for (int i = 0; i < 9; i++) {
            int digito = Integer.parseInt(cedula.substring(i, i + 1));
            int producto = digito * coeficientes[i];
            
            // Si el producto es mayor a 9, se resta 9
            if (producto >= 10) {
                producto -= 9;
            }
            suma += producto;
        }

        // Calcular el dígito verificador
        int residuo = suma % 10;
        int digitoVerificador = residuo == 0 ? 0 : 10 - residuo;

        // Comparar con el último dígito de la cédula
        int ultimoDigito = Integer.parseInt(cedula.substring(9, 10));

        return digitoVerificador == ultimoDigito;
    }

    /**
     * Valida un RUC ecuatoriano (cédula + 001)
     */
    public boolean validarRUC(String ruc) {
        if (ruc == null || !ruc.matches("\\d{13}")) {
            return false;
        }

        // Extraer la cédula (primeros 10 dígitos)
        String cedula = ruc.substring(0, 10);
        
        // Validar que la cédula sea correcta
        if (!validarCedula(cedula)) {
            return false;
        }

        // Verificar que termine en 001
        String sufijo = ruc.substring(10, 13);
        return sufijo.equals("001");
    }

    /**
     * Valida correo electrónico con dominios específicos permitidos
     * Dominios permitidos: gmail.com, hotmail.com, tecazuay.edu.ec
     */
    public boolean validarCorreoElectronico(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        // Patrón básico de email
        String emailPattern = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!Pattern.matches(emailPattern, email)) {
            return false;
        }

        // Verificar dominios permitidos
        String emailLower = email.toLowerCase();
        return emailLower.endsWith("@gmail.com") || 
               emailLower.endsWith("@hotmail.com") || 
               emailLower.endsWith("@tecazuay.edu.ec");
    }

    /**
     * Genera un RUC a partir de una cédula válida
     */
    public String generarRUC(String cedula) {
        if (validarCedula(cedula)) {
            return cedula + "001";
        }
        return null;
    }
}