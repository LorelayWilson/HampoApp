package org.example.lf52.hampo;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class ServicioMusica extends Service {
    MediaPlayer reproductor;
    @Override public void onCreate() {

        //reproductor = MediaPlayer.create(this, R.raw.audio);
    }
    @Override
    public int onStartCommand(Intent intenc, int flags, int idArranque) {

        reproductor.start();
        return START_STICKY;
    }
    @Override public void onDestroy() {

        reproductor.stop();
        reproductor.release();
    }
    @Override public IBinder onBind(Intent intencion) {
        return null;
    }
}