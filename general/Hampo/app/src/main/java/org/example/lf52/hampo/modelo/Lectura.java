package org.example.lf52.hampo.modelo;

public class Lectura {
    private String temperatura;
    private String  humedad;
    private String  iluminacion;
    private String  bebedero;
    private String  comedero;
    private String  actividad;
    private long tiempo;

    public Lectura(String temperatura, String humedad, String iluminacion, String bebedero, String comedero, String actividad, long tiempo) {
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.iluminacion = iluminacion;
        this.bebedero = bebedero;
        this.comedero = comedero;
        this.actividad = actividad;
        this.tiempo = tiempo;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getHumedad() {
        return humedad;
    }

    public void setHumedad(String humedad) {
        this.humedad = humedad;
    }

    public String getIluminacion() {
        return iluminacion;
    }

    public void setIluminacion(String iluminacion) {
        this.iluminacion = iluminacion;
    }

    public String getBebedero() {
        return bebedero;
    }

    public void setBebedero(String bebedero) {
        this.bebedero = bebedero;
    }

    public String getComedero() {
        return comedero;
    }

    public void setComedero(String comedero) {
        this.comedero = comedero;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public long getTiempo() {
        return tiempo;
    }

    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }
}
