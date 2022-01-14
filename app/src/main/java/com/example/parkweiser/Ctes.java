package com.example.parkweiser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Ctes {

    public static String SERVIDOR = "http://192.168.0.26:8080/ParkWeiserServer/";

    public static final String LETRAS_DNI="TRWAGMYFPDXBNJZSQVHLCKE";

    public static final String CONDUCTOR_SESION = "Conductor";
    public static final String FECHA_RESERVA_SESION = "FechaReserva";

    public static final SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    public static final SimpleDateFormat FORMATO_FECHA_SERVLET = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault());
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
                Integer.parseInt(conductorJSON.getString("telefono")),
                conductorJSON.getString("tarjeta"));
        return conductor;
    }

    public static List<Coche> getCochesJSON(String cochesResponse) throws JSONException {
        List<Coche> coches = new ArrayList<>();
        JSONArray cochesJSON = new JSONArray(cochesResponse);
        int tipo = 1;

        for (int i = 0; i < cochesJSON.length(); i++){
            JSONObject cocheJSON = cochesJSON.getJSONObject(i);
            tipo = Integer.parseInt(cocheJSON.getString("tipo"));
            Coche coche;
            if (tipo == 3){
                coche = new Coche(cocheJSON.getString("matricula"), true, true);
            }
            else if (tipo == 2){
                coche = new Coche(cocheJSON.getString("matricula"), true, false);
            }
            else{
                coche = new Coche(cocheJSON.getString("matricula"), false, false);
            }
            coches.add(coche);
        }
        return coches;
    }

    public static List<AparcaEn> getPagosJSON(String pagosResponse) throws JSONException {
        List<AparcaEn> pagos = new ArrayList<>();
        JSONArray pagosJSON = new JSONArray(pagosResponse);
        String entrada;
        String salida;

        for (int i = 0; i < pagosJSON.length(); i++){
            JSONObject pagoJSON = pagosJSON.getJSONObject(i);
            // entrada = FORMATO_FECHA.format((Timestamp) pagoJSON.getString("entrada"));
            // salida = FORMATO_FECHA.format((Timestamp) pagoJSON.getString("salida"));

            AparcaEn pago = new AparcaEn(pagoJSON.getString("matricula"),
                    Integer.parseInt(pagoJSON.getString("parking")),
                    pagoJSON.getString("entrada"),
                    pagoJSON.getString("salida"),
                    Float.parseFloat(pagoJSON.getString("precio")));
            pagos.add(pago);
        }
        return pagos;
    }
    public static List<String> getDiasConcurridosJSON(String diasConcurridosResponse) throws JSONException {
        List<String> diasConcurridos = new ArrayList<>();
        JSONArray diasConcurridosJSON = new JSONArray(diasConcurridosResponse);

        for (int i = 0; i < diasConcurridosJSON.length(); i++){
            JSONObject diaConcurridoJSON = diasConcurridosJSON.getJSONObject(i);
            diasConcurridos.add(diaConcurridoJSON.toString());
        }
        return diasConcurridos;
    }

    public static List<ReservaEn> getReservasJSON(String reservasResponse) throws JSONException {
        List<ReservaEn> reservas = new ArrayList<>();
        JSONArray reservasJSON = new JSONArray(reservasResponse);

        for (int i = 0; i < reservasJSON.length(); i++){
            JSONObject reservaJSON = reservasJSON.getJSONObject(i);
            ReservaEn reserva = new ReservaEn(reservaJSON.getString("matricula"),
                    reservaJSON.getString("entrada"),
                    reservaJSON.getString("salida"));
            reservas.add(reserva);
        }
        return reservas;
    }

    public static List<OcupacionFecha> getOcupacionesJSON(String ocupacionResponse) throws JSONException {
        List<OcupacionFecha> ocupaciones = new ArrayList<>();
        JSONArray ocupacionesJSON = new JSONArray(ocupacionResponse);

        for (int i = 0; i < ocupacionesJSON.length(); i++){
            JSONObject ocupacionJSON = ocupacionesJSON.getJSONObject(i);
            OcupacionFecha ocupacionFecha = new OcupacionFecha(Integer.parseInt(ocupacionJSON.getString("id")),
                    ocupacionJSON.getString("fecha"),
                    Float.parseFloat(ocupacionJSON.getString("ocupacion")));
            ocupaciones.add(ocupacionFecha);
        }
        return ocupaciones;
    }

    public static Boolean esDniValido(String dni){
        Boolean valido = true;

        String numDniStr = dni.trim().replaceAll(" ", "").substring(0, 7);
        char letraDNI = dni.charAt(8);
        int valNumDni = Integer.parseInt(numDniStr) % 23;

        //if (dni.length()!= 9 && esNumDniValido(numDniStr) == false && Ctes.LETRAS_DNI.charAt(valNumDni)!= letraDNI) {
        if (dni.length()!= 9 && esNumDniValido(numDniStr) == false) {
            valido = false;
        }
        return valido;
    }

    public static Boolean esNumDniValido(String numDniStr){
        Boolean valido = true;
        int numDni;
        try{
            numDni = Integer.parseInt(numDniStr);
            if (0 > numDni|| numDni > 99999999){
                valido = false;
            }
        }
        catch(Exception e){
            valido = false;
        }
        return valido;
    }

    public static Boolean esTelValido(String telStr){
        Boolean valido = true;
        int tel;
        try{
            tel = Integer.parseInt(telStr);
            if (0 > tel|| tel > 999999999){
                valido = false;
            }
        }
        catch(Exception e){
            valido = false;
        }
        return valido;
    }

    public static Boolean isTarjetaValida(String tarjetaStr){
        Boolean valido = false;
        long tarjeta;
        try{
            tarjeta = Long.parseLong(tarjetaStr);
            if (0 > tarjeta|| tarjeta > 9999999999999999l){
                valido = false;
            }
        }
        catch(Exception e){
            valido = false;
        }
        return valido;
    }

    public static Boolean esMinutosValido(String tiempoStr){
        boolean valido = true;
        int tiempo;
        try{
            tiempo = Integer.parseInt(tiempoStr);
            if (0 > tiempo || tiempo >= 60){
                valido = false;
            }
        }
        catch(Exception e){
            valido = false;
        }
        return valido;
    }
    public static Boolean esHorasValido(String tiempoStr){
        boolean valido = true;
        int tiempo;
        try{
            tiempo = Integer.parseInt(tiempoStr);
            if (0 > tiempo || tiempo >= 23){
                valido = false;
            }
        }
        catch(Exception e){
            valido = false;
        }
        return valido;
    }
    public static Boolean esFechaValida(String horaEntrada, String minutosEntrada, String horaSalida, String minutosSalida){
        boolean valido = false;

        if (esHorasValido(horaEntrada) && esHorasValido(horaSalida) && esMinutosValido(minutosEntrada) && esMinutosValido(minutosSalida)){
            try {
                Date entrada = Ctes.FORMATO_HORA.parse(horaEntrada + ":" + minutosEntrada);
                Date salida = Ctes.FORMATO_HORA.parse(horaSalida + ":" + minutosSalida);

                if(entrada.before(salida)){
                    valido = true;
                }

            } catch (ParseException e) {
                valido = false;
            }
        }

        return valido;
    }

    public static Boolean esMatriculaValida(String matricula){
        Boolean valido = true;

        String letras1 = matricula.trim().replaceAll(" ", "").substring(0, 2);
        String numeros = matricula.trim().replaceAll(" ", "").substring(2, 5);
        String letras2 = matricula.trim().replaceAll(" ", "").substring(5);

        if (esLetrasValido(letras1) == false && esLetrasValido(letras2) == false && esNumMatriculaValido(numeros) == false ) {
            valido = false;
        }
        return valido;
    }

    public static Boolean esLetrasValido(String letras){
        Boolean valido = true;
        letras = letras.toUpperCase();
        char[] charArray = letras.toCharArray();
        if (charArray.length == 2){
            for (int i = 0; i < charArray.length; i++) {
                char ch = charArray[i];
                if (!(ch >= 'A' && ch <= 'Z')) {
                    valido = false;
                }
            }
        }
        else{
            valido = false;
        }

        return valido;
    }

    public static Boolean esNumMatriculaValido(String numMatrStr){
        Boolean valido = true;
        int numMatricula;
        try{
            numMatricula = Integer.parseInt(numMatrStr);
            if (0 > numMatricula|| numMatricula > 999){
                valido = false;
            }
        }
        catch(Exception e){
            valido = false;
        }
        return valido;
    }
}
