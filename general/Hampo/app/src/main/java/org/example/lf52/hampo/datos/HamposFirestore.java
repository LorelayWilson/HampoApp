package org.example.lf52.hampo.datos;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.example.lf52.hampo.modelo.Hampo;

public class HamposFirestore implements HamposAsinc {
    private CollectionReference hampos;
    public FirebaseFirestore db;

    public HamposFirestore() {
        db = FirebaseFirestore.getInstance();
        hampos = db.collection("hampos");
    }

    @Override
    public void elemento(String id_jaula, EscuchadorElemento escuchador) {
        hampos.document(id_jaula).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Hampo hampo = task.getResult().toObject(Hampo.class);
                escuchador.onRespuesta(hampo);
            } else {
                Log.e("Firebase", "Error al leer", task.getException());
                escuchador.onRespuesta(null);
            }
        });
    }

    @Override
    public void anade(Hampo hampo) {
        hampos.document().set(hampo);
    }

    @Override
    public String nuevo() {
        return hampos.document().getId();
    }

    @Override
    public void borrar(String id_jaula) {
        hampos.document(id_jaula).delete();

    }

    @Override
    public void actualiza(String id_jaula, Hampo hampo) {
        hampos.document(id_jaula).set(hampo);
    }

    @Override
    public void tamano(EscuchadorTamano escuchador) {
        hampos.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                escuchador.onRespuesta(task.getResult().size());
            } else {
                Log.e("Firebase", "Error en tamaño", task.getException());
                escuchador.onRespuesta(-1);
            }
        });

    }
}
