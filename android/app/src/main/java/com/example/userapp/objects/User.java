package com.example.userapp.objects;

public class User {
    public String id, nombre, numero, correo;

    public User() {
    }

    public User(String id, String nombre, String numero, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.numero = numero;
        this.correo = correo;
    }
}
