package gr.edu.serres.TrancCoder_TheElucitated.Services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import gr.edu.serres.TrancCoder_TheElucitated.R;

/**
 * Created by GamerX on 18/11/2016.
 */

public class BackgroundSoundService extends Service {
    MediaPlayer player;
    public IBinder onBind(Intent arg0) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.welcome_screen);
        player.setLooping(true); // Set looping
        player.setVolume(100,100);

    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return Service.START_STICKY_COMPATIBILITY;
    }

    public IBinder onUnBind(Intent arg0) {
        // TO DO Auto-generated method
        return null;
    }

    public void onStop() {

    }
    public void start(){
        player.start();
    }
    public void pause() {
        player.pause();
    }
    @Override
    public void onDestroy() {
        player.stop();
        player.release();
        player = null;
    }

    @Override
    public void onLowMemory() {

    }
}
