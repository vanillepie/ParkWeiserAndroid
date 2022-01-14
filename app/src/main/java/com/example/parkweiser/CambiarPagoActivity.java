package com.example.parkweiser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class CambiarPagoActivity extends AppCompatActivity {

    private String tag = "CambiarPagoActivity";
    private String dniSesion = "";
    private String tarjetaSesion = "";
    private Button buttonCambiarTarjeta;
    private TextView editTextTarjeta;
    private String conductorSesion;
    private Boolean cambioPosible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        hideActionBar();
        initElementos();

        buttonCambiarTarjeta.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "Intento de cambiar tarjeta");

                String tarjeta = editTextTarjeta.getText().toString();

                if (haCambiadoTarjeta(tarjeta) && isTarjetaValida(tarjeta)){
                    getConductor(tarjeta);

                    if(cambioPosible) {
                        Intent i = new Intent(CambiarPagoActivity.this, PagosActivity.class);
                        i.putExtra(Ctes.CONDUCTOR_SESION, conductorSesion);
                        Log.i(tag, "Conductor anaidido a sesion");
                        startActivity(i);
                        finish();
                    }
                    else{
                        Toast.makeText(CambiarPagoActivity.this, "No se pudo realizar cambio.", Toast.LENGTH_LONG).show();
                    }
                }

                else {
                    Toast.makeText(CambiarPagoActivity.this, "Ningún dato fue cambiado o la tarjeta no es correcta.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getConductor(String tarjeta){
        // TODO pasar datos y nombre servlet
        String url = Ctes.SERVIDOR + "?DNI=" + dniSesion + "&tarjeta=" + tarjeta;
        CambiarPagoThread thread = new CambiarPagoThread(this, url);
        try {
            thread.join();
        }catch (InterruptedException e){}
    }

    public void setConductor(String response) throws JSONException {
        conductorSesion = response;
        JSONObject conductorJSON = new JSONObject(response);

        String dni = conductorJSON.getString("DNI");
        if (!dni.equals("-1")){
            cambioPosible = true;
        }
        else{
            cambioPosible = false;
        }
    }

    private Boolean isTarjetaValida(String tarjetaStr){
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
    private Boolean haCambiadoTarjeta(String tarjeta){
        Boolean valido = false;
        if (!this.tarjetaSesion.equals(tarjeta)){
            valido = true;
        }
        return valido;
    }

    private void hideActionBar(){
        final ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void initElementos(){

        this.buttonCambiarTarjeta = this.findViewById(R.id.buttonCambiarTarjeta);
        this.editTextTarjeta = this.findViewById(R.id.editTextTarjeta);

        try {
            Conductor conductor = Ctes.getConductorJSON(getIntent().getStringExtra(Ctes.CONDUCTOR_SESION));
            dniSesion = conductor.getDNI();
            // TODO tarjeta del conductor
            // TODO settear la tarjeta
        } catch (JSONException e) {
            Log.i(tag, "Error al sacar conductor de sesion: " + e);
            Toast.makeText(CambiarPagoActivity.this, "Ocurrió un problema.", Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        Log.i(tag, "Ir a Pagos");
        Intent i = new Intent(CambiarPagoActivity.this, PagosActivity.class);
        startActivity(i);
        finish();
    }
}