package com.example.parkweiser;

import android.util.Log;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class ConfiguracionThread extends Thread{

    private ConfiguracionActivity activity;
    private String tag = "ConfiguracionThread";
    private String urlStr = "";

    public ConfiguracionThread(ConfiguracionActivity activ, String url) {
        activity = activ;
        urlStr = url;
        start();
    }

    @Override
    public void run() {
        String response = "";
        try {
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response =  Ctes.convertStreamToString(in);
            Log.d(tag, "Respuesta JSON registro: " + response);

            activity.setConductor(response);
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}



