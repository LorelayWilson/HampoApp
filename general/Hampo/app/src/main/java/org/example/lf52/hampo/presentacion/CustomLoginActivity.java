package org.example.lf52.hampo.presentacion;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.example.lf52.hampo.R;

public class CustomLoginActivity extends Activity {
    private FirebaseAuth mAuth;
    private GoogleApiClient googleApiClient;
    private String idToken;
    private String TAG = "ActivityLogin";
    private TextView correo;
    private TextView contraseña;
    private TextInputLayout tilCorreo;
    private TextInputLayout tilContraseña;
    private ProgressDialog dialogo;
    private ViewGroup contenedor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        correo = findViewById(R.id.correo);
        contraseña = findViewById(R.id.contraseña);
        tilCorreo = findViewById(R.id.til_correo);
        tilContraseña = findViewById(R.id.til_contraseña);
        contenedor = (ViewGroup) findViewById(R.id.contenedor);
        dialogo = new ProgressDialog(this);
        dialogo.setTitle("Verificando usuario");
        dialogo.setMessage("Por favor espere...");


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("771706807040-vvbk8qt6j0ss6gspln56fijgtvdkpls4.apps.googleusercontent.com")//you can also use R.string.default_web_client_id
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    public void iniciarSesion(View view) {
        Log.d(TAG, correo.getText() + "");
        if (correo.getText().toString().isEmpty() || contraseña.getText().toString().isEmpty()) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Por favor introduzca un correo y contraseña válido");
            dlgAlert.setTitle("Rellena todos los campos");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        } else {

            String email = correo.getText().toString();
            String password = contraseña.getText().toString();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                killActivity();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());

                                if ("The password is invalid or the user does not have a password." == task.getException().getMessage()) {
                                    Snackbar.make(view, "Contraseña incorrecta o el correo no está asignado a ninguna cuenta.", Snackbar.LENGTH_LONG)
                                            .show();
                                } else if ("There is no user record corresponding to this identifier. The user may have been deleted." == task.getException().getMessage()) {
                                    Snackbar.make(view, "Contraseña incorrecta o el correo no está asignado a ninguna cuenta.", Snackbar.LENGTH_LONG)
                                            .show();
                                } else {
                                    Toast.makeText(CustomLoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }

    }

    public void registrar(View view) {
        if (correo.getText().toString().isEmpty() || contraseña.getText().toString().isEmpty()) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Por favor introduzca un correo y contraseña válido");
            dlgAlert.setTitle("Rellena todos los campos");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        } else {
            String email = correo.getText().toString();
            String password = contraseña.getText().toString();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                killActivity();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure");
                                Log.d(TAG, task.getException().getMessage());
                                if ("The email address is already in use by another account." == task.getException().getMessage()) {
                                    Snackbar.make(view, "Este correo ya esta registrado", Snackbar.LENGTH_LONG)
                                            .show();
                                } else {

                                    Toast.makeText(CustomLoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    });

        }
    }

    public void reestablecerContraseña(View v) {
        String email = correo.getText().toString();
        tilCorreo.setError("");
        if (email.isEmpty()) {
            tilCorreo.setError("Introduce un correo");
        } else if (!email.matches(".+@.+[.].+")) {
            tilCorreo.setError("Correo no válido");
        } else {
            dialogo.show();
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            dialogo.dismiss();
                            if (task.isSuccessful()) {
                                mensaje("Verifica tu correo para cambiar contraseña.");
                            } else {
                                mensaje("ERROR al mandar correo para cambiar contraseña");
                            }
                        }
                    });
        }
    }

    private void mensaje(String mensaje) {
        Snackbar.make(contenedor, mensaje, Snackbar.LENGTH_LONG).show();
    }


    public void iniciarConGoogle(View view) {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, 1234);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            idToken = account.getIdToken();
            //name = account.getDisplayName();
            //email = account.getEmail();
            // you can store user data to SharedPreference
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
            firebaseAuthWithGoogle(credential);
        } else {
            // Google Sign In failed, update UI appropriately
            Log.e(TAG, "Login Unsuccessful. " + result);
            Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(AuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful()) {
                            Toast.makeText(getBaseContext(), "Login successful", Toast.LENGTH_SHORT).show();
                            killActivity();
                        } else {
                            Log.w(TAG, "signInWithCredential" + task.getException().getMessage());
                            task.getException().printStackTrace();
                            Toast.makeText(getBaseContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


    private void killActivity() {
        finish();
    }
}
