package org.example.lf52.hampo.casos_uso;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import org.example.lf52.hampo.datos.HamposAsinc;
import org.example.lf52.hampo.modelo.Hampo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class CasosUsoHampo {
    private Activity actividad;
    private HamposAsinc hampos;

    public CasosUsoHampo(Activity actividad, HamposAsinc hampos) {
        this.actividad = actividad;
        this.hampos = hampos;
    }
    // OPERACIONES BÁSICAS
    //quien haga hampo que descomente este metodo(borra este comentario una vez acabado)
   /* public void mostrar(int pos) {
        Intent i = new Intent(actividad, HampoActivity.class);
        i.putExtra("pos", pos);
        actividad.startActivity(i);
    }*/

  /*  public void borrar(String id) {
        hampos.borrar(id);
        actividad.finish();
    }*/

    //quien haga editar hampo que descomente este metodo(borra este comentario una vez acabado)
   /* public void editar(int pos, int codigo){
        Intent i = new Intent(actividad, EditarHampoActivity.class);
        i.putExtra("pos", pos);
        actividad.startActivityForResult(i, codigo);
    }*7

    public void guardar(String id, Hampo nuevoHampo) {
        hampos.actualiza(id, nuevoHampo);
    }

    // INTENCIONES

//quien haga el mapa que recuerde editar este metodo(borra este comentario una vez acabado)
    /*public final void verMapa(Hampo hampo) {
        //codigo para mapa
        actividad.startActivity(new Intent("android.intent.action.VIEW", uri));
    }*/


    public void ponerDeGaleria(int codidoSolicitud) {
        String action;
        if (android.os.Build.VERSION.SDK_INT >= 19) { // API 19 - Kitkat
            action = Intent.ACTION_OPEN_DOCUMENT;
        } else {
            action = Intent.ACTION_PICK;
        }
        Intent intent = new Intent(action,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        actividad.startActivityForResult(intent, codidoSolicitud);
    }

    public void ponerFoto(String pos, String uri, ImageView imageView) {
        /*Hampo hampo = hampos.elemento(pos);
        hampo.setFoto(uri);
        visualizarFoto(hampo, imageView);*/
    }

   /* public void visualizarFoto(Hampo hampo, ImageView imageView) {
        if (hampo.getUriFoto() != null && !hampo.getUriFoto().isEmpty()) {
            imageView.setImageBitmap(reduceBitmap(actividad,hampo.getUriFoto(),1024,1024));
        } else {
            imageView.setImageBitmap(null);
        }
    }*/

    public Uri tomarFoto(int codidoSolicitud) {
        try {
            Uri uriUltimaFoto;
            File file = File.createTempFile(
                    "img_" + (System.currentTimeMillis()/ 1000), ".jpg" ,
                    actividad.getExternalFilesDir(Environment.DIRECTORY_PICTURES));
            if (Build.VERSION.SDK_INT >= 24) {
                uriUltimaFoto = FileProvider.getUriForFile(
                        actividad, "es.lorelay.hampos.fileProvider", file);
            } else {
                uriUltimaFoto = Uri.fromFile(file);
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra (MediaStore.EXTRA_OUTPUT, uriUltimaFoto);
            actividad.startActivityForResult(intent, codidoSolicitud);
            return uriUltimaFoto;
        } catch (IOException ex) {
            Toast.makeText(actividad, "Error al crear fichero de imagen",
                    Toast.LENGTH_LONG).show();
            return null;
        }
    }

    private Bitmap reduceBitmap(Context contexto, String uri,
                                int maxAncho, int maxAlto) {
        try {
            InputStream input = null;
            Uri u = Uri.parse(uri);
            if (u.getScheme().equals("http") || u.getScheme().equals("https")) {
                input = new URL(uri).openStream();
            } else {
                input = contexto.getContentResolver().openInputStream(u);
            }
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inSampleSize = (int) Math.max(
                    Math.ceil(options.outWidth / maxAncho),
                    Math.ceil(options.outHeight / maxAlto));
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(input, null, options);
        } catch (FileNotFoundException e) {
            Toast.makeText(contexto, "Fichero/recurso de imagen no encontrado",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            Toast.makeText(contexto, "Error accediendo a imagen",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return null;
        }
    }

    public static void solicitarPermiso(final String permiso, String
            justificacion, final int requestCode, final Activity actividad) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(actividad,
                permiso)){
            new AlertDialog.Builder(actividad).setTitle("Solicitud de permiso")
                    .setMessage(justificacion)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            ActivityCompat.requestPermissions(actividad,
                                    new String[]{permiso}, requestCode);
                        }}).show();
        } else {
            ActivityCompat.requestPermissions(actividad,
                    new String[]{permiso}, requestCode);
        }
    }
}
