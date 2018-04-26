package com.shanlian.genetic.bean.request;

/**
 * Created by kang on 2018/3/30.
 */

public class RegisterRequest {
    int ID;
    String USERNAME;
    String PASSWORD;
    String COMPANY;
    String TELPHONE;
    int ISDELETE;
    String REGDATE;

    public RegisterRequest(int ID, String USERNAME, String PASSWORD, String COMPANY, String TELPHONE, int ISDELETE, String REGDATE) {
        this.ID = ID;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
        this.COMPANY = COMPANY;
        this.TELPHONE = TELPHONE;
        this.ISDELETE = ISDELETE;
        this.REGDATE = REGDATE;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "ID=" + ID +
                ", USERNAME='" + USERNAME + '\'' +
                ", PASSWORD='" + PASSWORD + '\'' +
                ", COMPANY='" + COMPANY + '\'' +
                ", TELPHONE='" + TELPHONE + '\'' +
                ", ISDELETE=" + ISDELETE +
                ", REGDATE='" + REGDATE + '\'' +
                '}';
    }
}
