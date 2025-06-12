package com.example.pd_pro_biblioteka_client.model;

public class logAdmin {
    private static AdminModel admin;

    public static void set(AdminModel a) {
        admin = a;
    }

    public static AdminModel get() {
        return admin;
    }
}
