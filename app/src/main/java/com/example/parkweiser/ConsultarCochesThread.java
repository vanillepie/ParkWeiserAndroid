package com.example.parkweiser;

import android.util.Log;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class ConsultarCochesThread extends Thread{

    private ConsultarCochesActivity activity;
    private String tag = "ConsultarCochesThread";
    private String urlStr = "";

    public ConsultarCochesThread(ConsultarCochesActivity activ, String url) {
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
            response = Ctes.convertStreamToString(in);
            Log.d(tag, "Respuesta JSON consultar coches: " + response);

            activity.setCoches(response);
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }


}



