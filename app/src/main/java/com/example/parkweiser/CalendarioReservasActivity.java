package com.example.parkweiser;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CalendarioReservasActivity extends AppCompatActivity {

    private String tag = "CalendarioReservasActivity";
    private String dniSesion = "";
    private TextView textMes;
    private RecyclerView recyclerReservas;
    private CompactCalendarView calendario;
    private final SimpleDateFormat formatoMes = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    private Date ahora;
    private Date fechaSeleccionada;
    private Button reservar;
    private List<String> diasConcurridos;
    private List<ReservaEn> reservas;
    private final int[] COLORES = {Color.rgb(78, 153, 3),
            Color.rgb(176, 35, 4)};

    @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        hideActionBar();
        initElementos();

        calendario.setListener(new CompactCalendarView.CompactCalendarViewListener() {
           @Override
           public void onDayClick(Date dateClicked) {
               fechaSeleccionada = dateClicked;
               mostrarReservas(calendario.getEvents(dateClicked));
           }

           @Override
           public void onMonthScroll(Date firstDayOfNewMonth) {
               textMes.setText(formatoMes.format(firstDayOfNewMonth));
           }

        });
        reservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CalendarioReservasActivity.this, ReservarActivity.class);
                i.putExtra(Ctes.FECHA_RESERVA_SESION, Ctes.FORMATO_FECHA.format(fechaSeleccionada));
                startActivity(i);
                finish();
            }
        });
    }

    private void getReservas(){
        String url = Ctes.SERVIDOR + "GetMisReservas?DNI=" + dniSesion;
        CalendarioReservasThread thread = new CalendarioReservasThread(this, url);
        try {
            thread.join();
        }catch (InterruptedException e){}
    }

    public void setReservas(String response) throws JSONException {
        reservas = Ctes.getReservasJSON(response);
        for (int i = 0; i < reservas.size(); i++){
            setEvento(reservas.get(i).getEntrada(), reservas.get(i).getSalida(), reservas.get(i).getMatricula(), true);
        }
    }

    private void getDiasConcurridos(){
        String url = Ctes.SERVIDOR + "GetAvisosReservas";
        CalendarioConcurridoThread thread = new CalendarioConcurridoThread(this, url);
        try {
            thread.join();
        }catch (InterruptedException e){}
    }

    public void setDiasConcurridos(String response) throws JSONException {
        diasConcurridos = Ctes.getDiasConcurridosJSON(response);
        for (int i = 0; i < diasConcurridos.size(); i++){
            setEvento(diasConcurridos.get(i), "", "", false);
        }
    }

    private void hideActionBar(){
        final ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void initElementos(){
        getDiasConcurridos();

        recyclerReservas = findViewById(R.id.recyclerReservas);
        recyclerReservas.setLayoutManager(new LinearLayoutManager(this));

        textMes = findViewById(R.id.textMesReservas);
        reservar = findViewById(R.id.buttonReservar);
        calendario = (CompactCalendarView) findViewById(R.id.calendarioReservas);
        calendario.setUseThreeLetterAbbreviation(true);

        ahora = new Date();
        textMes.setText(formatoMes.format(ahora));

        try {
            dniSesion = Ctes.getConductorJSON(getIntent().getStringExtra(Ctes.CONDUCTOR_SESION)).getDNI();
        } catch (JSONException e) {
            Log.i(tag, "Error al sacar conductor de sesion: " + e);
            Toast.makeText(CalendarioReservasActivity.this, "Ocurrió un problema.", Toast.LENGTH_LONG).show();
        }
        getReservas();
    }

    private void mostrarReservas(List<Event> eventos){
        List<Event> reservas = new ArrayList<>();
        for (int i = 0; i < eventos.size(); i++){
            if (eventos.get(i).getData().toString().contains("Reserva")){
                reservas.add(eventos.get(i));
            }
        }

        CalendarioReservasAdapter calendarioReservasAdapter = new CalendarioReservasAdapter(reservas, this, Ctes.FORMATO_FECHA.format(fechaSeleccionada));
        recyclerReservas.setAdapter(calendarioReservasAdapter);
    }

    private void setEvento(String fechaEventoEmpieza, String fechaEventoTermina, String matricula, Boolean esReserva){
        int color;
        if(esReserva){
            color = COLORES[0];
        }
        else{
            color = COLORES[1];
        }

        String descripcion;
        if(esReserva){
            descripcion = "Reserva de " + fechaEventoEmpieza.substring(11) + " hasta " + fechaEventoTermina.substring(11) + " para " + matricula;
        }
        else{
            descripcion = "Día concurrido";
        }

        Calendar fechaEvento = Calendar.getInstance();
        try {
            fechaEvento.setTime(Ctes.FORMATO_FECHA.parse(fechaEventoEmpieza));
            long fechaEnMilis = fechaEvento.getTimeInMillis();
            Event evento = new Event (color, fechaEnMilis, descripcion);
            calendario.addEvent(evento, false);
        }

        catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(CalendarioReservasActivity.this, "No se pudo añadir evento.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        Log.i(tag, "Ir a Pagina Principal");
        Intent i = new Intent(CalendarioReservasActivity.this, PaginaPrincipalActivity.class);
        i.putExtra(Ctes.CONDUCTOR_SESION, getIntent().getStringExtra(Ctes.CONDUCTOR_SESION));
        startActivity(i);
        finish();
    }
}