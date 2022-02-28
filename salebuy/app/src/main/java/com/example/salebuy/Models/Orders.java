package com.example.salebuy.Models;

public class Orders {
    private String email;
    private int position;

    public  Orders(){

    }
    public Orders(String email,int position) {
        this.email = email;
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
