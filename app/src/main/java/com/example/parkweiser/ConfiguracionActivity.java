package com.example.parkweiser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class ConfiguracionActivity extends AppCompatActivity {

    private String tag = "ConfiguracionActivity";
    private Button buttonRegistro;
    private EditText editTextConfigClave;
    private EditText editTextConfigNombre;
    private EditText editTextConfigTelefono;
    private TextView textConfigDNIMostrar;
    private String dniSesion = "";
    private String claveSesion = "";
    private String nombreSesion = "";
    private String telefonoSesion = "";
    private String conductorSesion = "";
    private Boolean configuracionPosible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        hideActionBar();
        initElementos();

        buttonRegistro.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "Intento de configuracion");

                String clave = editTextConfigClave.getText().toString();
                String telefono = editTextConfigTelefono.getText().toString();
                String nombre = editTextConfigNombre.getText().toString();

                if (haCambiadoClave(clave) || haCambiadoNombre(nombre) || haCambiadoTel(telefono)){
                    getConductor(clave, telefono, nombre);

                    if(configuracionPosible) {
                        Intent i = new Intent(ConfiguracionActivity.this, PaginaPrincipalActivity.class);
                        i.putExtra(Ctes.CONDUCTOR_SESION, conductorSesion);
                        Log.i(tag, "Conductor anaidido a sesion");
                        startActivity(i);
                        finish();
                    }
                    else{
                        Toast.makeText(ConfiguracionActivity.this, "No se pudo realizar cambio.", Toast.LENGTH_LONG).show();
                    }
                }

                else {
                    Toast.makeText(ConfiguracionActivity.this, "Ningún dato fue cambiado.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getConductor(String clave, String tel, String nombre){
        String url = Ctes.SERVIDOR + "ModificarUsuario?DNI=" + dniSesion + "&clave=" + clave + "&telefono=" + tel + "&nombre=" + nombre;
        ConfiguracionThread thread = new ConfiguracionThread(this, url);
        try {
            thread.join();
        }catch (InterruptedException e){}
    }

    public void setConductor(String response) throws JSONException {
        if (response.contains("1")){
            configuracionPosible = true;
        }
        else{
            configuracionPosible = false;
        }
    }

    private Boolean haCambiadoClave(String clave){
        Boolean valido = false;
        if (!this.claveSesion.equals(clave)){
            valido = true;
        }
        return valido;
    }

    private Boolean haCambiadoNombre(String nombre){
        Boolean valido = false;
        if (!this.nombreSesion.equals(nombre)){
            valido = true;
        }
        return valido;
    }
    private Boolean haCambiadoTel(String telefono){
        Boolean valido = false;
        if (!this.telefonoSesion.equals(telefono)){
            valido = true;
        }
        return valido;
    }

    private void hideActionBar(){
        final ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void initElementos(){
        this.buttonRegistro = this.findViewById(R.id.buttonCambiarConfig);
        this.textConfigDNIMostrar = this.findViewById(R.id.textConfigDNIMostrar);
        this.editTextConfigClave = this.findViewById(R.id.editTextConfigClave);
        this.editTextConfigNombre = this.findViewById(R.id.editTextConfigNombre);
        this.editTextConfigTelefono = this.findViewById(R.id.editTextConfigTelefono);

        try {
            Conductor conductor = Ctes.getConductorJSON(getIntent().getStringExtra(Ctes.CONDUCTOR_SESION));
            dniSesion = conductor.getDNI();
            claveSesion = conductor.getClave();
            nombreSesion = conductor.getNombre();
            telefonoSesion += conductor.getTelefono();
        } catch (JSONException e) {
            Log.i(tag, "Error al sacar conductor de sesion: " + e);
            Toast.makeText(ConfiguracionActivity.this, "Ocurrió un problema.", Toast.LENGTH_LONG).show();
        }

        textConfigDNIMostrar.setText(dniSesion);
        editTextConfigClave.setText(claveSesion);
        editTextConfigNombre.setText(nombreSesion);
        editTextConfigTelefono.setText(telefonoSesion);
    }

    @Override
    public void onBackPressed() {
        Log.i(tag, "Ir a Pagina Principal");
        Intent i = new Intent(ConfiguracionActivity.this, PaginaPrincipalActivity.class);
        i.putExtra(Ctes.CONDUCTOR_SESION, getIntent().getStringExtra(Ctes.CONDUCTOR_SESION));
        startActivity(i);
        finish();
    }
}