package com.example.userapp.objects;

public class CitaSolicitud {

    public String id, nombre, correo, telefono, tipo, descripcionPro, direccion, piso, descripUbi, fecha, hora, newid;

    public CitaSolicitud() {
    }

    public CitaSolicitud(String id, String newid, String nombre, String correo, String telefono, String tipo, String descripcionPro, String direccion, String piso, String descripUbi, String fecha, String hora) {
        this.id = id;
        this.newid = newid;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.tipo = tipo;
        this.descripcionPro = descripcionPro;
        this.direccion = direccion;
        this.piso = piso;
        this.descripUbi = descripUbi;
        this.fecha = fecha;
        this.hora = hora;
    }
}
