package com.example.parkweiser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConsultarCochesActivity extends AppCompatActivity {

    private String tag = "ConsultarCochesActivity";
    private RecyclerView recyclerAutos;
    private ConsultarCochesAdapter consultarCochesAdapter;
    private Button buttonAniadirCoche;
    private List<Coche> coches;
    private String dniSesion = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_coches);

        initElementos();
        hideActionBar();

        buttonAniadirCoche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "Ir a registrar coche");
                Intent i = new Intent(ConsultarCochesActivity.this, RegistroCocheActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void hideActionBar(){
        final ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void initElementos(){
        try {
            dniSesion = Ctes.getConductorJSON(getIntent().getStringExtra(Ctes.CONDUCTOR_SESION)).getDNI();
        } catch (JSONException e) {
            Log.i(tag, "Error al sacar conductor de sesion: " + e);
            Toast.makeText(ConsultarCochesActivity.this, "Ocurrió un problema.", Toast.LENGTH_LONG).show();
        }
        getCoches();
        recyclerAutos = findViewById(R.id.recyclerCoches);
        recyclerAutos.setLayoutManager(new LinearLayoutManager(this));
        consultarCochesAdapter = new ConsultarCochesAdapter(coches, this);
        recyclerAutos.setAdapter(consultarCochesAdapter);

        this.buttonAniadirCoche = this.findViewById(R.id.buttonAniadirCoche);
    }

    private void getCoches(){
        String url = Ctes.SERVIDOR + "GetMisVehiculos?DNI="+ dniSesion;
        ConsultarCochesThread thread = new ConsultarCochesThread(this, url);
        try {
            thread.join();
        }catch (InterruptedException e){}
    }

    public void setCoches(String response) throws JSONException {
        coches = Ctes.getCochesJSON(response);
    }

    @Override
    public void onBackPressed() {
        Log.i(tag, "Ir a Pagina Principal");
        Intent i = new Intent(ConsultarCochesActivity.this, PaginaPrincipalActivity.class);
        startActivity(i);
        finish();
    }
}