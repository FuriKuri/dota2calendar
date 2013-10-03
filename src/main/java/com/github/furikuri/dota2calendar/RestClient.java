package com.github.furikuri.dota2calendar;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;

public class RestClient {
    public static final String TAG = RestClient.class.getSimpleName();

    public MatchList getRest() {
        final HttpParams httpParams = new BasicHttpParams();
        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(httpParams, "utf-8");
        ConnManagerParams.setMaxTotalConnections(httpParams, 2);
        final SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));

        final ThreadSafeClientConnManager manager =
                new ThreadSafeClientConnManager(httpParams, registry);

        DefaultHttpClient client = new DefaultHttpClient(manager, httpParams);

        try {
            final HttpGet request = new HttpGet();
            request.setURI(new URI("http://dota-2-cs.herokuapp.com/matches"));
            request.setHeader("Accept", "application/json");
            final HttpResponse response = client.execute(request);

            final BufferedReader br = new BufferedReader
                    (new InputStreamReader(response.getEntity().getContent()));

            final StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            br.close();
            Log.i(TAG, "HTML: " + sb.toString());
            List<Match> matches = new GsonBuilder().setDateFormat("yyyy-MM-dd").create().fromJson(sb.toString(), new TypeToken<List<Match>>() {
            }.getType());
            Log.i(TAG, "Rest Object: " + matches);
            return new MatchList(matches);

        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.toString());
        }
        return null;
    }
}
