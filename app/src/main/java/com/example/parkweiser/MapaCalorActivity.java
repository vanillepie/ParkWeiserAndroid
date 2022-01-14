package com.example.parkweiser;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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


public class MapaCalorActivity extends AppCompatActivity {

    private String tag = "MapaCalorActivity";
    private TextView textMes;
    CompactCalendarView calendario;
    private SimpleDateFormat formatoMes = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private Date ahora;
    private final int[] COLORES = {Color.rgb(79, 93, 219),
            Color.rgb(79, 219, 130),
            Color.rgb(177, 219, 79),
            Color.rgb(227, 224, 54),
            Color.rgb(219, 93, 35)};

    private static List<TextView> mapa;
    private List<OcupacionFecha> ocupaciones;

    @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_calor);

        hideActionBar();
        initElementos();

        calendario.setListener(new CompactCalendarView.CompactCalendarViewListener() {
           @Override
           public void onDayClick(Date dateClicked) {
               pintarMapaCalor(formatoFecha.format(dateClicked));
           }

           @Override
           public void onMonthScroll(Date firstDayOfNewMonth) {
               textMes.setText(formatoMes.format(firstDayOfNewMonth));
           }
        });
    }

    private void pintarMapaCalor(String dia){
        pintarMapaCalorDefault();
        for (int i = 0; i < ocupaciones.size(); i++){
            if(ocupaciones.get(i).getFecha().contains(dia)){
                pintarPlaza(ocupaciones.get(i).getPlaza(), ocupaciones.get(i).getOcupacion());
            }
        }
    }

    private void pintarMapaCalorDefault(){
        for (int i = 0; i < mapa.size(); i++){
            pintarPlaza(i, -1);
        }
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
        mapa.get(idPlaza).setBackgroundColor(color);
    }

    private void getOcupaciones(){
        // TODO poner nombre servlet
        String url = Ctes.SERVIDOR + "GetOcupacionesPlazasPorDia";
        MapaCalorThread thread = new MapaCalorThread(this, url);
        try {
            thread.join();
        }catch (InterruptedException e){}
    }

    public void setPlazas(String response) throws JSONException {
        ocupaciones = Ctes.getOcupacionesJSON(response);
    }

    private void hideActionBar(){
        final ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void initElementos() {
        getOcupaciones();

        initMapa();
        textMes = findViewById(R.id.textMesMapa);
        ahora = new Date();
        textMes.setText(formatoMes.format(ahora));
        calendario = (CompactCalendarView) findViewById(R.id.calendarioMapa);
        calendario.setUseThreeLetterAbbreviation(true);

        pintarMapaCalor(formatoFecha.format(ahora));
    }

    @Override
    public void onBackPressed() {
        Log.i(tag, "Ir a Consultar Trafico");
        Intent i = new Intent(MapaCalorActivity.this, ConsultarTraficoActivity.class);
        startActivity(i);
        finish();
    }

    private void initMapa(){
        mapa = new ArrayList<>();
        mapa.add(findViewById(R.id.plaza0));
        mapa.add(findViewById(R.id.plaza1));
        mapa.add(findViewById(R.id.plaza2));
        mapa.add(findViewById(R.id.plaza3));
        mapa.add(findViewById(R.id.plaza4));
        mapa.add(findViewById(R.id.plaza5));
        mapa.add(findViewById(R.id.plaza6));
        mapa.add(findViewById(R.id.plaza7));
        mapa.add(findViewById(R.id.plaza8));
        mapa.add(findViewById(R.id.plaza9));
        mapa.add(findViewById(R.id.plaza10));
        mapa.add(findViewById(R.id.plaza11));
        mapa.add(findViewById(R.id.plaza12));
        mapa.add(findViewById(R.id.plaza13));
        mapa.add(findViewById(R.id.plaza14));
        mapa.add(findViewById(R.id.plaza15));
        mapa.add(findViewById(R.id.plaza16));
        mapa.add(findViewById(R.id.plaza17));
        mapa.add(findViewById(R.id.plaza18));
        mapa.add(findViewById(R.id.plaza19));
        mapa.add(findViewById(R.id.plaza20));
        mapa.add(findViewById(R.id.plaza21));
        mapa.add(findViewById(R.id.plaza22));
        mapa.add(findViewById(R.id.plaza23));
        mapa.add(findViewById(R.id.plaza24));
        mapa.add(findViewById(R.id.plaza25));
        mapa.add(findViewById(R.id.plaza26));
        mapa.add(findViewById(R.id.plaza27));
        mapa.add(findViewById(R.id.plaza28));
        mapa.add(findViewById(R.id.plaza29));
        mapa.add(findViewById(R.id.plaza30));
        mapa.add(findViewById(R.id.plaza31));
        mapa.add(findViewById(R.id.plaza32));
        mapa.add(findViewById(R.id.plaza33));
        mapa.add(findViewById(R.id.plaza34));
        mapa.add(findViewById(R.id.plaza35));
        mapa.add(findViewById(R.id.plaza36));
        mapa.add(findViewById(R.id.plaza37));
        mapa.add(findViewById(R.id.plaza38));
        mapa.add(findViewById(R.id.plaza39));
        mapa.add(findViewById(R.id.plaza40));
        mapa.add(findViewById(R.id.plaza41));
        mapa.add(findViewById(R.id.plaza42));
        mapa.add(findViewById(R.id.plaza43));
        mapa.add(findViewById(R.id.plaza44));
        mapa.add(findViewById(R.id.plaza45));
        mapa.add(findViewById(R.id.plaza46));
        mapa.add(findViewById(R.id.plaza47));
        mapa.add(findViewById(R.id.plaza48));
        mapa.add(findViewById(R.id.plaza49));
        mapa.add(findViewById(R.id.plaza50));
        mapa.add(findViewById(R.id.plaza51));
        mapa.add(findViewById(R.id.plaza52));
        mapa.add(findViewById(R.id.plaza53));
        mapa.add(findViewById(R.id.plaza54));
        mapa.add(findViewById(R.id.plaza55));
        mapa.add(findViewById(R.id.plaza56));
        mapa.add(findViewById(R.id.plaza57));
        mapa.add(findViewById(R.id.plaza58));
        mapa.add(findViewById(R.id.plaza59));
        mapa.add(findViewById(R.id.plaza60));
        mapa.add(findViewById(R.id.plaza61));
        mapa.add(findViewById(R.id.plaza62));
        mapa.add(findViewById(R.id.plaza63));
        mapa.add(findViewById(R.id.plaza64));
        mapa.add(findViewById(R.id.plaza65));
        mapa.add(findViewById(R.id.plaza66));
        mapa.add(findViewById(R.id.plaza67));
        mapa.add(findViewById(R.id.plaza68));
        mapa.add(findViewById(R.id.plaza69));
        mapa.add(findViewById(R.id.plaza70));
        mapa.add(findViewById(R.id.plaza71));
        mapa.add(findViewById(R.id.plaza72));
        mapa.add(findViewById(R.id.plaza73));
        mapa.add(findViewById(R.id.plaza74));
        mapa.add(findViewById(R.id.plaza75));
        mapa.add(findViewById(R.id.plaza76));
        mapa.add(findViewById(R.id.plaza77));
        mapa.add(findViewById(R.id.plaza78));
        mapa.add(findViewById(R.id.plaza79));
        mapa.add(findViewById(R.id.plaza80));
        mapa.add(findViewById(R.id.plaza81));
        mapa.add(findViewById(R.id.plaza82));
        mapa.add(findViewById(R.id.plaza83));
        mapa.add(findViewById(R.id.plaza84));
        mapa.add(findViewById(R.id.plaza85));
        mapa.add(findViewById(R.id.plaza86));
        mapa.add(findViewById(R.id.plaza87));
        mapa.add(findViewById(R.id.plaza88));
        mapa.add(findViewById(R.id.plaza89));
        mapa.add(findViewById(R.id.plaza90));
        mapa.add(findViewById(R.id.plaza91));
        mapa.add(findViewById(R.id.plaza92));
        mapa.add(findViewById(R.id.plaza93));
        mapa.add(findViewById(R.id.plaza94));
        mapa.add(findViewById(R.id.plaza95));
        mapa.add(findViewById(R.id.plaza96));
        mapa.add(findViewById(R.id.plaza97));
        mapa.add(findViewById(R.id.plaza98));
        mapa.add(findViewById(R.id.plaza99));
    }
}