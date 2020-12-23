package org.example.lf52.hampo;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.example.lf52.hampo.datos.HamposAsinc;
import org.example.lf52.hampo.datos.HamposFirestore;


public class Aplicacion extends Application {
    public HamposAsinc hampos;
    public String id;


    @Override
    public void onCreate() {
        super.onCreate();
        id = FirebaseAuth.getInstance().getUid();
        hampos = new HamposFirestore();

    }


}