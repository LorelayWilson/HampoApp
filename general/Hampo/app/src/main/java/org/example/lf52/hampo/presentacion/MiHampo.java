package org.example.lf52.hampo.presentacion;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.example.lf52.hampo.Aplicacion;
import org.example.lf52.hampo.R;
import org.example.lf52.hampo.modelo.Hampo;
import org.example.lf52.hampo.modelo.Lectura;


public class MiHampo extends AppCompatActivity {


    private String id;
    private String idJaula;

    private CollectionReference hampo;
    private DocumentReference jaula;

    private String nombre;
    private String raza;

    private String TAG = "Coso";

    private TextView aux;

    private TextView progresoComida;
    private TextView progresoActividad;
    private TextView progresoBebida;
    private ConstraintLayout totalComida;
    private ConstraintLayout totalActividad;
    private ConstraintLayout totalBebida;

    private CardView botonEditar;
    private CardView botonBorrar;

    private Hampo h;
    private Lectura lectura;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mi_hampo);


        Bundle extras = getIntent().getExtras();
        idJaula = extras.getString("id");
        Log.d(TAG, extras.getString("id"));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        hampo = db.collection(id);
        jaula = hampo.document(idJaula);

        id = ((Aplicacion)getApplication()).id;

        progresoComida = findViewById(R.id.progresoComida);
        progresoActividad = findViewById(R.id.progresoActividad);
        progresoBebida = findViewById(R.id.progresoBebida);
        totalComida = findViewById(R.id.fondoComida);
        totalActividad = findViewById(R.id.fondoActividad);
        totalBebida = findViewById(R.id.fondoBebida);

        botonEditar = findViewById(R.id.botonEditar);
        botonBorrar = findViewById(R.id.botonBorrar);


        botonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarEditar(v);
            }
        });


        botonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmacionBorrado();
            }
        });


        jaula.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        nombre = document.getData().get("nombre").toString();
                        //Log.d(TAG, nombre);
                        aux = findViewById(R.id.nombreHampo);
                        aux.setText(nombre);

                        raza = document.getData().get("raza").toString();
                        //Log.d(TAG, raza);
                        aux = findViewById(R.id.razaHampo);
                        aux.setText(raza);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        Query lecturas = db.collection(id).document(idJaula).collection("Lecturas").orderBy("Fecha").limit(1);

        lecturas.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().size() == 1) {

                        Log.d(TAG, task.getResult().getDocuments().get(0).getData().get("Humedad").toString()+ "jaja");

                        aux = findViewById(R.id.porcentajeIluminacion);
                        aux.setText(task.getResult().getDocuments().get(0).getData().get("Iluminacion").toString() + "%");

                        aux = findViewById(R.id.temperatura);
                        aux.setText(task.getResult().getDocuments().get(0).getData().get("Temperatura").toString());

                        aux = findViewById(R.id.porcentajeHumedad);
                        aux.setText(task.getResult().getDocuments().get(0).getData().get("Humedad").toString() + "%");

                        aux = findViewById(R.id.porcentajeComida);
                        aux.setText(task.getResult().getDocuments().get(0).getData().get("Comedero").toString() + "%");

                        aux = findViewById(R.id.porcentajeBebida);
                        aux.setText(task.getResult().getDocuments().get(0).getData().get("Bebedero").toString() + "%");

                        aux = findViewById(R.id.porcentajeActividad);
                        aux.setText(task.getResult().getDocuments().get(0).getData().get("Actividad").toString() + "%");

                        actualizarBarras(task.getResult().getDocuments().get(0).getData().get("Bebedero").toString(), task.getResult().getDocuments().get(0).getData().get("Comedero").toString(), task.getResult().getDocuments().get(0).getData().get("Actividad").toString());
                    }
                } else {
                    Log.e("Firebase", "Error al leer", task.getException());
                }
            }
        });

    }

    private void actualizarBarras(String b, String c, String a) {

        int totalB = totalBebida.getWidth();
        int totalA = totalActividad.getWidth();
        int totalC = totalComida.getWidth();
        int porcentajeB = totalB * Integer.parseInt(b) / 100;
        int porcentajeC = totalC * Integer.parseInt(c) / 100;
        int porcentajeA = totalA * Integer.parseInt(a) / 100;


        ConstraintLayout.LayoutParams lpC = (ConstraintLayout.LayoutParams) progresoComida.getLayoutParams();
        lpC.width = porcentajeC;
        progresoComida.setLayoutParams(lpC);

        ConstraintLayout.LayoutParams lpA = (ConstraintLayout.LayoutParams) progresoActividad.getLayoutParams();
        lpA.width = porcentajeA;
        progresoActividad.setLayoutParams(lpA);

        ConstraintLayout.LayoutParams lpB = (ConstraintLayout.LayoutParams) progresoBebida.getLayoutParams();
        lpB.width = porcentajeB;
        progresoBebida.setLayoutParams(lpB);

    }

    private void lanzarEditar(View view){
        Intent i = new Intent(view.getContext(),EditHampoActivity.class);
        i.putExtra("idJaula","taDVh1M7JolXGXCDziQn");
        startActivity(i);

    }

    private void lanzarBorrar(){
        hampo.document(idJaula).delete();
        finish();
    }

    public void confirmacionBorrado(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Borrado de hampo");

        builder.setMessage("¿Estás seguro que quieres eliminar el hamop?");

        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                lanzarBorrar();
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

}
