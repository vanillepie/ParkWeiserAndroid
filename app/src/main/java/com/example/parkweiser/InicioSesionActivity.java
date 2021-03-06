package com.example.parkweiser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class InicioSesionActivity extends AppCompatActivity {

    private String tag = "InicioSesionActivity";
    private Button buttonInicioSesion;
    private EditText editTextDNI;
    private EditText editTextClave;
    private String conductorSesion;
    private boolean conductorExiste = false;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        hideActionBar();
        initElementos();

        buttonInicioSesion.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "Intento de iniciar sesion");

                String dni = editTextDNI.getText().toString();
                String clave = editTextClave.getText().toString();

                if (Ctes.esDniValido(dni)){
                    getConductor(dni, clave);

                    if(conductorExiste) {
                        Intent i = new Intent(InicioSesionActivity.this, PaginaPrincipalActivity.class);
                        i.putExtra(Ctes.CONDUCTOR_SESION, conductorSesion);
                        Log.i(tag, "Conductor aniadido a sesion");
                        startActivity(i);
                        finish();
                    }
                    else{
                        Toast.makeText(InicioSesionActivity.this, "Contraseña o DNI no son correctos.", Toast.LENGTH_LONG).show();
                    }
                }

                else {
                    Toast.makeText(InicioSesionActivity.this, "DNI no es correcto.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getConductor(String dni, String clave){
        String url = Ctes.SERVIDOR + "IniciarSesion?DNI=" + dni + "&clave=" + clave;
        InicioSesionThread thread = new InicioSesionThread(this, url);
        try {
            thread.join();
        }catch (InterruptedException e){}
    }

    public void setConductor(String response) throws JSONException {
        conductorSesion = response;
        JSONObject conductorJSON = new JSONObject(response);
        String dni = conductorJSON.getString("DNI");

        if (!dni.equals("-1")){
            conductorExiste = true;
        }
        else{
            conductorExiste = false;
        }
    }

    private void hideActionBar(){
        final ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void initElementos(){
        this.buttonInicioSesion = this.findViewById(R.id.buttonInicioSesion);
        this.editTextDNI = this.findViewById(R.id.editTextInicioDNI);
        this.editTextClave = this.findViewById(R.id.editTextInicioClave);
    }

    @Override
    public void onBackPressed() {
        Log.i(tag, "Ir a Main");
        Intent i = new Intent(InicioSesionActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}