package org.example.lf52.hampo.modelo;

public class Hampo {
    private String nombre;
    private String foto;
    private String idUser;
    private String raza;
    private String sexo;
    private String peso;

    public Hampo(){}

    public Hampo(String nombre, String foto, String idUser, String raza, String sexo, String peso) {
        this.nombre = nombre;
        this.foto = foto;
        this.idUser = idUser;
        this.raza = raza;
        this.sexo = sexo;
        this.peso = peso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }
}