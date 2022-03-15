package com.example.expertportal;

public class Expert {

    String name,password,email;

    Expert(String name,String email,String password){
        this.name=name;
        this.password=password;
        this.email=email;
    }

    Expert(){

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
