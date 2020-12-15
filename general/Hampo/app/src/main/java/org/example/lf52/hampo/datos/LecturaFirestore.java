package org.example.lf52.hampo.datos;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.example.lf52.hampo.Aplicacion;
import org.example.lf52.hampo.modelo.Lectura;

public class LecturaFirestore implements LecturaAsinc {


    @Override
    public void ultimaLectura(String id_jaula, final EscuchadorElemento escuchador) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String id = Aplicacion.getId();
        Query lecturas = db.collection("Hampos").document(id_jaula).collection("Lecturas").orderBy("Fecha").limit(1);

        lecturas.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if(task.getResult().size()==1){

                        Lectura lectura = task.getResult().getDocuments().get(0).toObject(Lectura.class);
                        escuchador.onRespuesta(lectura);
                    }
                } else {
                    Log.e("Firebase", "Error al leer", task.getException());
                    escuchador.onRespuesta(null);
                }
            }
        });
    }

}