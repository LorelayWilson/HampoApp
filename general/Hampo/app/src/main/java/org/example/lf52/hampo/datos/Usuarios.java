package org.example.lf52.hampo.datos;


import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.example.lf52.hampo.modelo.Usuario;

public class Usuarios {
    public static void guardarUsuario(final FirebaseUser user) {
        Usuario usuario = new Usuario(user.getDisplayName(),user.getEmail());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Usuarios").document(user.getUid()).set(usuario);
    }
}