package com.aerb.budget.dto;

public class LoginResponse {
    private boolean success;
    private String message;
    private boolean isAdmin;
    private boolean isHead;

    // Getters and Setters
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

    public boolean isAdmin() {
        return isAdmin;
    }
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isHead() {
        return isHead;
    }
    public void setIsHead(boolean isHead) {
        this.isHead = isHead;
    }
}
