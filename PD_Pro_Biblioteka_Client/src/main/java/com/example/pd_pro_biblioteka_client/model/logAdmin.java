package com.example.pd_pro_biblioteka_client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class logAdmin {
    private static AdminModel admin;
    private static String admToken;

    @Getter
    public static String admIdStr;
    @Getter @Setter
    private String sub;
    @Getter @Setter
    private String role;
    @Getter @Setter
    private String admId;
    @Getter @Setter
    private String isAdmin;
    @Getter @Setter
    private long iat;
    @Getter @Setter
    private long exp;

    @JsonCreator
    public logAdmin(
            @JsonProperty("sub") String sub,
            @JsonProperty("role") String role,
            @JsonProperty("userId") String userId,
            @JsonProperty("isAdmin") String isAdmin,
            @JsonProperty("iat") long iat,
            @JsonProperty("exp") long exp
    ) {
        this.sub = sub;
        this.role = role;
        this.admId = userId;
        this.isAdmin = isAdmin;
        this.iat = iat;
        this.exp = exp;
        this.admIdStr = userId;
    }

    public static void setAdminToken(String token) {
        admToken = token;
    }


    public static void clearAdmin() {
        admToken = null;
        admIdStr = null;
    }

    public static String getAdminToken() {
        return admToken;
    }

    public static void set(AdminModel a) {
        admin = a;
    }

    public static AdminModel get() {
        return admin;
    }
}
