package map.wayne.com.plantdominator2099;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Random;

public class BackgroundSoundService extends Service {
    private static final String TAG = null;
    MediaPlayer player;
    private int length;
    private int i;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Random r = new Random();
        i = r.nextInt(4 - 0) + 0;
        switch (i) {
            case 0:
                player = MediaPlayer.create(this, R.raw.town);
                break;
            case 1:
                player = MediaPlayer.create(this, R.raw.town_1);
                break;
            case 2:
                player = MediaPlayer.create(this, R.raw.town_2);
                break;
            case 3:
                player = MediaPlayer.create(this, R.raw.town_3);
                break;
            case 4:
                player = MediaPlayer.create(this, R.raw.town_4);
                break;
        }
        player.setLooping(true); // Set looping
        player.setVolume(100, 100);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return 1;
    }

    public void onStart(Intent intent, int startId) {
        // TO DO
    }

    public IBinder onUnBind(Intent arg0) {
        // TO DO Auto-generated method
        return null;
    }

    public void onStop() {
    }

    public void onPause() {
    }

    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }

    @Override
    public void onLowMemory() {
    }
}
