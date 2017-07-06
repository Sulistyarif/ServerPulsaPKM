package com.example.sulistyarif.serverpulsapkm;

/**
 * Created by Gndx on 6/20/2017.
 */

public class Relation {
    public String id;
    public int saldo;

    public Relation(){

    }

    public Relation(String id, int saldo) {
        this.id = id;
        this.saldo = saldo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
}
