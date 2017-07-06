package com.example.sulistyarif.serverpulsapkm;

/**
 * Created by momo on 16/04/17.
 */

public class User {
    public String name;
    public String email;
    public String paswot;
    public int saldo;
    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User(){

    }

    public User(String name, String email, String paswot, String id) {
        this.name = name;
        this.email = email;
        this.paswot = paswot;
        this.id = id;
    }

    public User(String name, String email, String paswot, int saldo, String id) {
        this.name = name;
        this.email = email;
        this.paswot = paswot;
        this.saldo = saldo;
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getPaswot(){
        return paswot;
    }

    public int getSaldo() {
        return saldo;
    }
}
