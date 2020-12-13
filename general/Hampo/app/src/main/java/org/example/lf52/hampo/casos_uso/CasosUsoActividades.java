package org.example.lf52.hampo.casos_uso;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import org.example.lf52.hampo.presentacion.MainActivity;

public class CasosUsoActividades {

    private Activity actividad;

    public CasosUsoActividades(Activity actividad){
        this.actividad = actividad;
    }

    public void lanzarActividad(View v, Class c){
        Intent i = new Intent(this.actividad.getBaseContext(), c);
        actividad.startActivity(i);
    }
}
