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

                if (esMatriculaValida(matricula)){
                    getCoche(matricula, electrico, minusvalidos);

                    if(registroPosible) {
                        Intent i = new Intent(RegistroCocheActivity.this, PaginaPrincipalActivity.class);
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
        // TODO pasar datos y nombre servlet
        String url = Ctes.SERVIDOR + "InicioSesion?DNI=";
        RegistroCocheThread thread = new RegistroCocheThread(this, url);
        try {
            thread.join();
        }catch (InterruptedException e){}
    }
    public void confirmaRegistro(String response) throws JSONException {
        // TODO confirmar
    }

    private Boolean esMatriculaValida(String matricula){
        Boolean valido = true;

        String letras1 = matricula.trim().replaceAll(" ", "").substring(0, 2);
        String numeros = matricula.trim().replaceAll(" ", "").substring(2, 5);
        String letras2 = matricula.trim().replaceAll(" ", "").substring(5);

        if (esLetrasValido(letras1) == false && esLetrasValido(letras2) == false && esNumMatricula(numeros) == false ) {
            valido = false;
        }
        return valido;
    }

    private Boolean esLetrasValido(String letras){
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

    private Boolean esNumMatricula(String numMatrStr){
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

    private void hideActionBar(){
        final ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void initElementos(){
        this.buttonRegistroCoche = this.findViewById(R.id.buttonRegistroCoche);
        this.editTextMatriculaRegistroCoche = this.findViewById(R.id.editTextMatriculaRegistroCoche);
        this.radioButtonElectricoRegistroCoche = this.findViewById(R.id.radioButtonElectricoRegistroCoche);
        this.radioButtonMinusvalidosRegistroCoche = this.findViewById(R.id.radioButtonMinusvalidosRegistroCoche);
    }
}