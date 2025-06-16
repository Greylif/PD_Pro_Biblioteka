package com.example.pd_pro_biblioteka_client.model;

public class TOTPSetupResponse {
    private String qrCodeUrl;
    private String secret;

    // Gettery
    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public String getSecret() {
        return secret;
    }
}
