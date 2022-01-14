package com.example.parkweiser;

import android.util.Log;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class BorrarCocheThread extends Thread{

    private ConsultarCochesAdapter adapter;
    private String tag = "BorrarCocheThread";
    private String urlStr = "";

    public BorrarCocheThread(ConsultarCochesAdapter adapter, String url) {
        adapter = adapter;
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
            Log.d(tag, "Respuesta JSON borrar coche: " + response);

            adapter.confimarBorrado(response);
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }


}



