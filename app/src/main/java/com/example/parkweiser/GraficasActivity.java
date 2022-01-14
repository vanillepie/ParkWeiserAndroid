package com.example.parkweiser;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class GraficasActivity extends AppCompatActivity {

    private String tag = "GraficasActivity";
    private SimpleDateFormat formatoMes = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private Date ahora;
    private final int[] COLORES = {Color.rgb(79, 93, 219),
            Color.rgb(79, 219, 130),
            Color.rgb(177, 219, 79),
            Color.rgb(227, 224, 54),
            Color.rgb(219, 93, 35)};

    private static List<TextView> meses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_calor);

        hideActionBar();
        initElementos();

        for (int i = 0; i < meses.size(); i++){
            int finalI = i;
            meses.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    destacarMes(finalI);
                }
            });
        }
    }

    private void destacarMes(int pos){
        for (int i = 0; i < meses.size(); i++){
            if (i == pos){
                meses.get(i).setTextSize(24);
            }
            else{
                meses.get(i).setTextSize(20);
            }
        }
    }

    private void pintarGraficas(String dia){
    }

    private void pintarPlaza(int idPlaza, float ocupacion){
        int color;
        switch((int) ocupacion * 100)
        {
            case 0 :
                color = COLORES[0];
                break;
            case 25 :
                color = COLORES[1];
                break;
            case 50 :
                color = COLORES[2];
                break;
            case 75 :
                color = COLORES[3];
                break;
            case 100 :
                color = COLORES[4];
                break;
            default :
                color = Color.GRAY;
        }
    }

    private void getPlazas(){
        // TODO poner nombre servlet
        String url = Ctes.SERVIDOR + "servlet";
        MapaCalorThread thread = new MapaCalorThread(this, url);
        try {
            thread.join();
        }catch (InterruptedException e){}
    }

    public void setPlazas(String response) throws JSONException {
        // TODO sacar lista de plazas
    }

    private void hideActionBar(){
        final ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void initElementos() {
        getPlazas();
        initMeses();
        ahora = new Date();
        pintarGraficas(formatoFecha.format(ahora));
    }

    @Override
    public void onBackPressed() {
        Log.i(tag, "Ir a Consultar Trafico");
        Intent i = new Intent(GraficasActivity.this, ConsultarTraficoActivity.class);
        startActivity(i);
        finish();
    }

    private void initMeses() {
        // TODO poner nombres

        meses = new ArrayList<>();
        meses.add(findViewById(R.id.textMesGraficas0));
        meses.add(findViewById(R.id.textMesGraficas1));
        meses.add(findViewById(R.id.textMesGraficas2));
    }
}