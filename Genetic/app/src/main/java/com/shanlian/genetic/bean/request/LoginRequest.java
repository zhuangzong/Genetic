package com.shanlian.genetic.bean.request;

/**
 * Created by kang on 2018/3/30.
 */

public class LoginRequest {
    String phone;
    String pwd;

    public LoginRequest(String phone, String pwd) {
        this.phone = phone;
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "phone='" + phone + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
