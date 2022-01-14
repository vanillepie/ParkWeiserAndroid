package com.example.parkweiser;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class InicioSesionThread extends Thread{

    private InicioSesionActivity activity;
    private String tag = "InicioSesionThread";
    private String urlStr = "";

    public InicioSesionThread(InicioSesionActivity activ, String url) {
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
            Log.d(tag, "Respuesta JSON inicio sesion: " + response);

            activity.setConductor(response);
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}



