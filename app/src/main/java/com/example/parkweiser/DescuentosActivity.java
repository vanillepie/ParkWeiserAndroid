package com.example.parkweiser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class DescuentosActivity extends AppCompatActivity {

    private String tag = "DescuentosActivity";
    private String dniSesion = "";
    private RecyclerView recyclerDescuentos;
    private DescuentosAdapter descuentosAdapter;
    private List<Descuento> descuentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descuentos);

        hideActionBar();
        initElementos();
    }


    @Override
    public void onBackPressed() {
        Log.i(tag, "Ir a Pagina Principal");
        Intent i = new Intent(DescuentosActivity.this, PaginaPrincipalActivity.class);
        i.putExtra(Ctes.CONDUCTOR_SESION, getIntent().getStringExtra(Ctes.CONDUCTOR_SESION));
        startActivity(i);
        finish();
    }

    private void getDescuentos(){
        // TODO poner nombre servlet
        String url = Ctes.SERVIDOR + "?DNI=" + dniSesion;
        DescuentosThread thread = new DescuentosThread(this, url);
        try {
            thread.join();
        }catch (InterruptedException e){}
    }

    public void setDescuentos(String response) throws JSONException {
        // TODO sacar lista de pagos
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
            Toast.makeText(DescuentosActivity.this, "Ocurrió un problema.", Toast.LENGTH_LONG).show();
        }
        getDescuentos();

        recyclerDescuentos = findViewById(R.id.recyclerDescuentos);
        recyclerDescuentos.setLayoutManager(new LinearLayoutManager(this));
        descuentosAdapter = new DescuentosAdapter(descuentos, this);
        recyclerDescuentos.setAdapter(descuentosAdapter);
    }

}