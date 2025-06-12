package com.example.pd_pro_biblioteka_client.model;

public class logUser {
    private static Uzytkownik user;
    private static String userToken;

    public static void setUserToken(String token) {
        userToken = token;
    }

    public static void clearUserToken() {
        userToken = null;
    }

    public static String getUserToken() {
        return userToken;
    }

    public static void set(Uzytkownik u) {
        user = u;
    }

    public static Uzytkownik get() {
        return user;
    }

    public static void clear() {
        user = null;
    }
}
