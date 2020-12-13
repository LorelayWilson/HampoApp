package org.example.lf52.hampo;

import android.app.Application;

import org.example.lf52.hampo.datos.HamposAsinc;
import org.example.lf52.hampo.datos.HamposFirestore;


public class Aplicacion extends Application {
    public HamposAsinc hampos;
    private static String id;


    @Override
    public void onCreate() {
        super.onCreate();

        hampos = new HamposFirestore();

    }

    public static String getId() {
        return id;
    }



}