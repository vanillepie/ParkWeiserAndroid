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
import org.w3c.dom.Text;

public class RegistroActivity extends AppCompatActivity {

    private String tag = "RegistroActivity";
    private Button buttonRegistro;
    private EditText editTextRegistroDNI;
    private EditText editTextRegistroClave;
    private EditText editTextRegistroNombre;
    private EditText editTextRegistroTelefono;
    private String conductorSesion;
    private Boolean registroPosible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        hideActionBar();
        initElementos();

        buttonRegistro.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "Intento de registro");

                String dni = editTextRegistroDNI.getText().toString();
                String clave = editTextRegistroClave.getText().toString();
                String tel = editTextRegistroTelefono.getText().toString();
                String nombre = editTextRegistroNombre.getText().toString();

                if (Ctes.esDniValido(dni) && Ctes.esTelValido(tel)){
                    getConductor(dni, clave, tel, nombre);
                    if(registroPosible) {
                        Intent i = new Intent(RegistroActivity.this, PaginaPrincipalActivity.class);
                        i.putExtra(Ctes.CONDUCTOR_SESION, conductorSesion);
                        startActivity(i);
                        finish();
                    }
                    else{
                        Toast.makeText(RegistroActivity.this, "No se pudo realizar registro.", Toast.LENGTH_LONG).show();
                    }
                }

                else {
                    Toast.makeText(RegistroActivity.this, "DNI o teléfono no son correctos.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getConductor(String dni, String clave, String tel, String nombre){
        String url = Ctes.SERVIDOR + "RegistrarUsuario?DNI=" + dni + "&clave=" + clave + "&nombre=" + nombre + "&telefono=" + tel;
        RegistroThread thread = new RegistroThread(this, url);
        try {
            thread.join();
        }catch (InterruptedException e){}
    }

    public void setConductor(String response) throws JSONException {
        conductorSesion = response;
        JSONObject conductorJSON = new JSONObject(response);

        String dni = conductorJSON.getString("DNI");
        if (!dni.equals("-1")){
            registroPosible = true;
        }
        else{
            registroPosible = false;
        }
    }

    private void hideActionBar(){
        final ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void initElementos(){
        this.buttonRegistro = this.findViewById(R.id.buttonRegistro);
        this.editTextRegistroDNI = this.findViewById(R.id.editTextRegistroDNI);
        this.editTextRegistroClave = this.findViewById(R.id.editTextRegistroClave);
        this.editTextRegistroNombre = this.findViewById(R.id.editTextRegistroNombre);
        this.editTextRegistroTelefono = this.findViewById(R.id.editTextRegistroTelefono);
    }

    @Override
    public void onBackPressed() {
        Log.i(tag, "Ir a Main");
        Intent i = new Intent(RegistroActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}