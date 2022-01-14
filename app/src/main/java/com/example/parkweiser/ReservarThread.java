package com.example.parkweiser;

import android.util.Log;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class ReservarThread extends Thread{

    private ReservarAdapter adapter;
    private String tag = "ReservarThread";
    private String urlStr = "";

    public ReservarThread(ReservarAdapter adapter, String url) {
        this.adapter = adapter;
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
            Log.d(tag, "Respuesta JSON reserva: " + response);

            adapter.confirmaReserva(response);
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}



