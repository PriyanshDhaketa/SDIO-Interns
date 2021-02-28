package com.example.sdiointerns;

public class user {

    private String Email;
    private String Password;

    public user(){

    }

    public user(String eml, String pass){
        if (eml.trim().equals("")) {
            eml = "No Name";
        }
        Email = eml;
        Password = pass;
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
}
