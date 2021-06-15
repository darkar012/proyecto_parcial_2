package com.example.userapp.objects;

public class Cita {
    private String correo, descripUbi, descripcionPro, direccion, fecha, hora, id, newid, nombre, piso, telefono, tipo, trabajador, trabajadorNombre;
    private Long trabajadorTel;

    public Cita() {
    }

    public Cita(String correo, String descripUbi, String descripcionPro, String direccion, String fecha, String hora, String id, String newid, String nombre, String piso, String telefono, String tipo, String trabajador, String trabajadorNombre, Long trabajadorTel) {
        this.correo = correo;
        this.descripUbi = descripUbi;
        this.descripcionPro = descripcionPro;
        this.direccion = direccion;
        this.fecha = fecha;
        this.hora = hora;
        this.id = id;
        this.newid = newid;
        this.nombre = nombre;
        this.piso = piso;
        this.telefono = telefono;
        this.tipo = tipo;
        this.trabajador = trabajador;
        this.trabajadorNombre = trabajadorNombre;
        this.trabajadorTel = trabajadorTel;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDescripUbi() {
        return descripUbi;
    }

    public void setDescripUbi(String descripUbi) {
        this.descripUbi = descripUbi;
    }

    public String getDescripcionPro() {
        return descripcionPro;
    }

    public void setDescripcionPro(String descripcionPro) {
        this.descripcionPro = descripcionPro;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewid() {
        return newid;
    }

    public void setNewid(String newid) {
        this.newid = newid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(String trabajador) {
        this.trabajador = trabajador;
    }

    public String getTrabajadorNombre() {
        return trabajadorNombre;
    }

    public void setTrabajadorNombre(String trabajadorNombre) {
        this.trabajadorNombre = trabajadorNombre;
    }

    public Long getTrabajadorTel() {
        return trabajadorTel;
    }

    public void setTrabajadorTel(Long trabajadorTel) {
        this.trabajadorTel = trabajadorTel;
    }
}
