package org.example.lf52.hampo.datos;


import org.example.lf52.hampo.modelo.Lectura;

public interface LecturaAsinc {

    interface EscuchadorElemento{
        void onRespuesta(Lectura lectura);
    }
    void ultimaLectura(String id_jaula, LecturaAsinc.EscuchadorElemento escuchador);

}
