package com.example.parkweiser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Ctes {

    public static String SERVIDOR = "http://192.168.1.131:8080/UbicompServerExample/";

    public static final String LETRAS_DNI="TRWAGMYFPDXBNJZSQVHLCKE";

    public static final String CONDUCTOR_SESION = "Conductor";
    public static final String FECHA_RESERVA_SESION = "FechaReserva";

    public static final SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    public static final SimpleDateFormat FORMATO_HORA = new SimpleDateFormat("HH:mm", Locale.getDefault());

    //Get the input strean and convert into String
    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static Conductor getConductorJSON(String conductorStr) throws JSONException {
        JSONObject conductorJSON = new JSONObject(conductorStr);
        Conductor conductor = new Conductor(conductorJSON.getString("DNI"),
                conductorJSON.getString("clave"),
                conductorJSON.getString("nombre"),
                Integer.parseInt(conductorJSON.getString("telefono")));
        return conductor;
    }
}
