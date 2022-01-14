package com.example.parkweiser;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DescuentosActivity extends AppCompatActivity {

    private String tag = "DescuentosActivity";
    private RecyclerView recyclerDescuentos;
    private DescuentosAdapter descuentosAdapter;
    private List<Descuento> descuentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descuentos);
        initElementos();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);
    }

    private void initElementos(){
        recyclerDescuentos = findViewById(R.id.recyclerDescuentos);
        recyclerDescuentos.setLayoutManager(new LinearLayoutManager(this));
        descuentosAdapter = new DescuentosAdapter(descuentos, this);
        recyclerDescuentos.setAdapter(descuentosAdapter);
    }

}