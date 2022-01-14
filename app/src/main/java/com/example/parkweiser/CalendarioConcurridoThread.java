package com.example.parkweiser;

import android.util.Log;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class CalendarioConcurridoThread extends Thread{

    private CalendarioReservasActivity activity;
    private String tag = "CalendarioConcurridoThread";
    private String urlStr = "";

    public CalendarioConcurridoThread(CalendarioReservasActivity activ, String url) {
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
            Log.d(tag, "Respuesta JSON consultar dias concurridos: " + response);

            activity.setDiasConcurridos(response);
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }


}



