package ista.M4A2.dto;

import java.util.Map;

public class LoginResponse {
    
    private boolean success;
    private String message;
    private Map<String, Object> data;

    // Constructores
    public LoginResponse() {}

    public LoginResponse(boolean success, String message, Map<String, Object> data) {
        this.success = success;
        this.message = message;
        this.data = data;
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

    public Map<String, Object> getData() { 
        return data; 
    }
    
    public void setData(Map<String, Object> data) { 
        this.data = data; 
    }
}