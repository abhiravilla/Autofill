package com.ravilla.abhi.autofill;

public class fulllist {
    String site;
    String username;
    String password;
    public fulllist(String s, String us, String p){
        site = s;
        username = us;
        password = p;
    }
    public fulllist(){

    }
    public String getsite() {
        return site;
    }
    public String getpassword() {
        return password;
    }
    public String getuname() {
        return username;
    }
    public void setsite(String s) {
       site =s;
    }
    public void setUname(String uname) {
        username = uname;
    }
    public void setPassword(String pass) {
        password =pass;
    }
}
