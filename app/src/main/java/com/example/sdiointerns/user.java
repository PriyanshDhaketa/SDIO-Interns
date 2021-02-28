package com.example.sdiointerns;

public class user {

    private String Email;
    private String Password;
    private String name;
    private String city;
    private String mobile;

    public user(){

    }

    public user(String eml, String pass, String uname, String mcity, String mob){
        if (eml.trim().equals("")) {
            eml = "No Name";
        }
        Email = eml;
        Password = pass;
        name = uname;
        city = mcity;
        mobile = mob;


    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
