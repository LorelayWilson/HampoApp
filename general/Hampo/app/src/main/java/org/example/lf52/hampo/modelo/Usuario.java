package org.example.lf52.hampo.modelo;

public class Usuario {
    private String nombre;
    private String correo;
    private String foto;
    private String telefono;
    private long inicioSesion;

    public Usuario(){

    }

    public Usuario(String nombre, String correo, String foto, String telefono, long inicioSesion) {
        this.nombre = nombre;
        this.correo = correo;
        this.foto = foto;
        this.telefono = telefono;
        this.inicioSesion = inicioSesion;
    }

    public Usuario (String nombre, String correo, String foto, String telefono) {
        this(nombre, correo, foto, telefono, System.currentTimeMillis());
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public long getInicioSesion() {
        return inicioSesion;
    }

    public void setInicioSesion(long inicioSesion) {
        this.inicioSesion = inicioSesion;
    }
}