package com.github.furikuri.dota2calendar;

import android.app.Service;
import android.content.Intent;
import android.os.*;
import android.util.Log;

import java.util.Date;

public class MatchUpdateService extends Service {
    private static final String TAG = MatchUpdateService.class.getSimpleName();
    private final IBinder mBinder = new LocalBinder();
    private Handler mActivityCallbackHandler;

    public class LocalBinder extends Binder {
        public void setCallbackHandler(Handler uiThreadCallbackHandler) {
            mActivityCallbackHandler = uiThreadCallbackHandler;
        }

        public void updateMatches() {
            Thread thread = new Thread() {
                public void run() {
                    MatchList matchList = new RestClient().getRest();
                    String rueckmeldung = "Operation beendet!";

                    Log.d(TAG, "got result in service " + matchList);

                    Bundle bundle = new Bundle();
                    bundle.putParcelable("rueckmeldung", matchList);

                    Message message = new Message();
                    message.obj = rueckmeldung;

                    message.setData(bundle);
                    mActivityCallbackHandler.sendMessage(message);

                    Log.d(TAG, "LocalBinder->langwierigeOperation()->run(): Aufgabe beendet");
                }
            };

            thread.start();
        }
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate(): LocalService starten...");
        Log.d(TAG, "onCreate(): PID: " + android.os.Process.myPid());
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind(): entered...");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy(): LocalService beenden...");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind(): entered...");
        return mBinder;
    }
}
