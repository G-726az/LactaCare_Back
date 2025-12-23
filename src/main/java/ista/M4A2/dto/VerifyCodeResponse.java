package ista.M4A2.dto;

/**
 * DTO para respuesta de verificación de código de recuperación.
 */
public class VerifyCodeResponse {

    private boolean success;
    private String message;
    private String resetToken; // Token temporal para el siguiente paso

    // Constructores
    public VerifyCodeResponse() {
    }

    public VerifyCodeResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public VerifyCodeResponse(boolean success, String message, String resetToken) {
        this.success = success;
        this.message = message;
        this.resetToken = resetToken;
    }

    // Getters y Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    @Override
    public String toString() {
        return "VerifyCodeResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", resetToken='" + (resetToken != null ? "***" : "null") + '\'' +
                '}';
    }
}
