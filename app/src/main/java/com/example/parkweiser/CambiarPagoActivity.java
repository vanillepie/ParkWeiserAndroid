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
        setContentView(R.layout.activity_cambiar_pago);

        hideActionBar();
        initElementos();

        buttonCambiarTarjeta.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "Intento de cambiar tarjeta");

                String tarjeta = editTextTarjeta.getText().toString();

                getConductor(tarjeta);

                if(cambioPosible) {
                    Intent i = new Intent(CambiarPagoActivity.this, PaginaPrincipalActivity.class);
                    i.putExtra(Ctes.CONDUCTOR_SESION, conductorSesion);
                    Log.i(tag, "Conductor anaidido a sesion");
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(CambiarPagoActivity.this, "No se pudo realizar cambio.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void getConductor(String tarjeta){
        String url = Ctes.SERVIDOR + "RegistrarMetodoPago?DNI=" + dniSesion + "&tarjeta=" + tarjeta;
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
            tarjetaSesion = conductor.getTarjeta();
        } catch (JSONException e) {
            Log.i(tag, "Error al sacar conductor de sesion: " + e);
            Toast.makeText(CambiarPagoActivity.this, "Ocurrió un problema.", Toast.LENGTH_LONG).show();
        }
        editTextTarjeta.setText(tarjetaSesion);
    }

    @Override
    public void onBackPressed() {
        Log.i(tag, "Ir a Pagos");
        Intent i = new Intent(CambiarPagoActivity.this, PagosActivity.class);
        i.putExtra(Ctes.CONDUCTOR_SESION, getIntent().getStringExtra(Ctes.CONDUCTOR_SESION));
        startActivity(i);
        finish();
    }
}