package com.github.furikuri.dota2calendar;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

public class HelloActivity extends Activity {
    public static final String TAG = HelloActivity.class.getSimpleName();

    private TextView textView;
    private MatchAdapter matchAdapter;
    private ListView lv;

    private MatchUpdateService.LocalBinder mServiceBinder;

    private final Handler mUiServiceCallbackHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG, "Handler->handleMessage(): entered...");
            final Bundle bundle = msg.getData();
            if (bundle != null) {
                final MatchList rueckmeldung = bundle.getParcelable("rueckmeldung");
                aktualisiereActivity(rueckmeldung);
            }
        }
    };

    private ServiceConnection mLocalServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder binder) {
            Log.d(TAG,
                    "localServiceConnection->onServiceConnected(): entered...");
            mServiceBinder = (MatchUpdateService.LocalBinder) binder;
            mServiceBinder.setCallbackHandler(mUiServiceCallbackHandler);
        }

        public void onServiceDisconnected(ComponentName className) { }
    };

    private void aktualisiereActivity(MatchList text) {
        Log.d(TAG, "update view");
        textView.setText("Updated " + new Date());
        matchAdapter.setNewMatches(text.getMatches());
        matchAdapter.notifyDataSetChanged();
    }

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello_layout);
        final Intent intent = new Intent(this, MatchUpdateService.class);
        Log.d(TAG, "bind service");
        bindService(intent, mLocalServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(mLocalServiceConnection);
        super.onDestroy();
    }

    static final String[] FRUITS = new String[] { "Apple", "Avocado", "Banana",
            "Blueberry", "Coconut", "Durian", "Guava", "Kiwifruit",
            "Jackfruit", "Mango", "Olive", "Pear", "Sugar-apple" };


    @Override
	public void onStart() {
        super.onStart();
        Log.d(TAG, "Start rest request");

         textView = (TextView) findViewById(R.id.text_view);
        textView.setText("Hello world: ");

        lv = (ListView) this.findViewById(R.id.match_list);
        matchAdapter = new MatchAdapter(this, new ArrayList<Match>());
        lv.setAdapter(matchAdapter);
        lv.setTextFilterEnabled(true);

    }

    public void onClickLocalService(View v) {
        Log.d(TAG, "onClickLocalService(): fuehre langwierige Operation aus...");
        if (v.getId() == R.id.opt_startService) mServiceBinder.updateMatches();
    }

}
