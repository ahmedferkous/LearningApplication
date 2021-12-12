package com.example.testapplication.Items;

public class LoginResponse {
    public static final int SUCCESS = 200;
    public static final int FAILED_PASSWORD = 100;
    public static final int FAILED_USER_ID = 50;
    public static final int FORCED_SIGN_OUT = -1;
    public static final int UNKNOWN_ERROR = -2;

    //public static final String TYPE_USER = "user";
    //public static final String TYPE_ADMIN = "administrator";

    private int response;
    private String type;
    private String md5_password;

    public LoginResponse() {
    }

    public String getMd5_password() {
        return md5_password;
    }

    public void setMd5_password(String md5_password) {
        this.md5_password = md5_password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }
}
