package com.bnta.codecompiler.models.users;

public class PasswordResetInput {
    private Long userId;
    private String secret;
    private String newPassword;

    public PasswordResetInput(Long userId, String secret, String newPassword) {
        this.userId = userId;
        this.secret = secret;
        this.newPassword = newPassword;
    }

    public PasswordResetInput() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }


}
