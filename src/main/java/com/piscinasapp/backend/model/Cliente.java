package com.piscinasapp.backend.model;

public class Cliente {
    private String name;
    private String apellido;
    private String email;
    private int mantenciones;
    private double total;
    private String descripcion; // Nuevo campo
    private String fotoMantencion; // Campo para la foto

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMantenciones() {
        return mantenciones;
    }

    public void setMantenciones(int mantenciones) {
        this.mantenciones = mantenciones;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFotoMantencion() {
        return fotoMantencion;
    }

    public void setFotoMantencion(String fotoMantencion) {
        this.fotoMantencion = fotoMantencion;
    }
}