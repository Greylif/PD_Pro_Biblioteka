package com.example.pd_pro_biblioteka_client.service;

import com.example.pd_pro_biblioteka_client.model.logAdmin;
import com.example.pd_pro_biblioteka_client.model.logUser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Base64;
import java.util.Map;

public class JWTdecoder {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static logUser decodeToLogUser(String jwt) throws Exception {
        String[] parts = jwt.split("\\.");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid JWT format.");
        }
        String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
        return objectMapper.readValue(payloadJson, logUser.class);
    }

    public static logAdmin decodeToLogAdm(String jwt) throws Exception {
        String[] parts = jwt.split("\\.");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid JWT format.");
        }
        String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
        return objectMapper.readValue(payloadJson, logAdmin.class);
    }

}
