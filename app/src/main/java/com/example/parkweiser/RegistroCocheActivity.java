package com.example.parkweiser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class RegistroCocheActivity extends AppCompatActivity {

    private String tag = "RegistroCocheActivity";
    private String dniSesion = "";
    private Button buttonRegistroCoche;
    private TextView editTextMatriculaRegistroCoche;
    private RadioButton radioButtonElectricoRegistroCoche;
    private RadioButton radioButtonMinusvalidosRegistroCoche;
    private Boolean registroPosible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_coche);

        hideActionBar();
        initElementos();

        buttonRegistroCoche.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "Intento de registro de coche");

                String matricula = editTextMatriculaRegistroCoche.getText().toString();
                Boolean electrico = radioButtonElectricoRegistroCoche.isChecked();
                Boolean minusvalidos = radioButtonMinusvalidosRegistroCoche.isChecked();

                if (Ctes.esMatriculaValida(matricula)){
                    getCoche(matricula, electrico, minusvalidos);

                    if(registroPosible) {
                        Intent i = new Intent(RegistroCocheActivity.this, PaginaPrincipalActivity.class);
                        i.putExtra(Ctes.CONDUCTOR_SESION, getIntent().getStringExtra(Ctes.CONDUCTOR_SESION));
                        startActivity(i);
                        finish();
                    }
                    else{
                        Toast.makeText(RegistroCocheActivity.this, "No se pudo realizar registro del coche.", Toast.LENGTH_LONG).show();
                    }
                }

                else {
                    Toast.makeText(RegistroCocheActivity.this, "Matricula no es correcta.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getCoche(String matricula, Boolean electrico, Boolean minusvalidos){
        int tipo = 1;
        if(electrico){
            tipo = 2;
        }
        else if (minusvalidos){
            tipo = 3;
        }

        String url = Ctes.SERVIDOR + "RegistrarVehiculo?matricula=" + matricula + "&DNI=" + dniSesion + "&tipo=" + tipo;
        RegistroCocheThread thread = new RegistroCocheThread(this, url);
        try {
            thread.join();
        }catch (InterruptedException e){}
    }
    public void confirmaRegistro(String response) throws JSONException {
        if (response.contains("1")){
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
        try {
            Conductor conductor = Ctes.getConductorJSON(getIntent().getStringExtra(Ctes.CONDUCTOR_SESION));
            dniSesion = conductor.getDNI();
        } catch (JSONException e) {
            Log.i(tag, "Error al sacar conductor de sesion: " + e);
            Toast.makeText(RegistroCocheActivity.this, "Ocurrió un problema.", Toast.LENGTH_LONG).show();
        }

        this.buttonRegistroCoche = this.findViewById(R.id.buttonRegistroCoche);
        this.editTextMatriculaRegistroCoche = this.findViewById(R.id.editTextMatriculaRegistroCoche);
        this.radioButtonElectricoRegistroCoche = this.findViewById(R.id.radioButtonElectricoRegistroCoche);
        this.radioButtonMinusvalidosRegistroCoche = this.findViewById(R.id.radioButtonMinusvalidosRegistroCoche);
    }

    @Override
    public void onBackPressed() {
        Log.i(tag, "Ir a Consultar Coches");
        Intent i = new Intent(RegistroCocheActivity.this, ConsultarCochesActivity.class);
        i.putExtra(Ctes.CONDUCTOR_SESION, getIntent().getStringExtra(Ctes.CONDUCTOR_SESION));
        startActivity(i);
        finish();
    }
}