package com.github.furikuri.dota2calendar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.google.gson.Gson;

public class HelloActivity extends Activity {

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello_layout);
    }

    @Override
	public void onStart() {
        super.onStart();
        TextView textView = (TextView) findViewById(R.id.text_view);
        textView.setText("Hello world!!!! Dota" + BuildConfig.DEBUG);
        Log.d("bla", "debug" + BuildConfig.DEBUG);
        Gson gson = new Gson();
    }

}
