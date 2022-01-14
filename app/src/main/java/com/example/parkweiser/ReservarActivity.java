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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReservarActivity extends AppCompatActivity {

    private String tag = "ReservarActivity";
    private String dniSesion = "";
    private TextView textDiaReserva;
    private String diaReservaSesion = "";
    private Button buttonAniadirReserva;
    private EditText editTextHoraEntrada;
    private EditText editTextMinutosEntrada;
    private EditText editTextHoraSalida;
    private EditText editTextMinutosSalida;
    private RecyclerView recyclerCochesReserva;
    private List<Coche> coches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar);

        hideActionBar();
        initElementos();
    }

    private void getCoches(){
        String url = Ctes.SERVIDOR + "GetMisVehiculos?DNI="+ dniSesion;
        ConsultarCochesReservaThread thread = new ConsultarCochesReservaThread(this, url);
        try {
            thread.join();
        }catch (InterruptedException e){}
    }

    public void setCoches(String response) throws JSONException {
        coches = Ctes.getCochesJSON(response);
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
            Toast.makeText(ReservarActivity.this, "Ocurrió un problema.", Toast.LENGTH_LONG).show();
        }

        getCoches();
        recyclerCochesReserva = findViewById(R.id.recyclerCochesReserva);
        recyclerCochesReserva.setLayoutManager(new LinearLayoutManager(this));
        ReservarAdapter reservarAdapter = new ReservarAdapter(coches, this);
        recyclerCochesReserva.setAdapter(reservarAdapter);

        this.textDiaReserva = this.findViewById(R.id.textDiaReserva);
        this.editTextHoraEntrada = this.findViewById(R.id.editTextHoraEntrada);
        this.editTextMinutosEntrada = this.findViewById(R.id.editTextMinutosEntrada);
        this.editTextHoraSalida = this.findViewById(R.id.editTextHoraSalida);
        this.editTextMinutosSalida = this.findViewById(R.id.editTextMinutosSalida);
        this.buttonAniadirReserva = this.findViewById(R.id.buttonAniadirReserva);

        diaReservaSesion = getIntent().getStringExtra(Ctes.FECHA_RESERVA_SESION).substring(0, 10);
        textDiaReserva.setText(diaReservaSesion);
    }

    public String getHoraEntrada(){
       return editTextHoraEntrada.getText().toString();
    }
    public String getMinutosEntrada(){
        return editTextMinutosEntrada.getText().toString();
    }
    public String getHoraSalida(){
        return editTextHoraSalida.getText().toString();
    }
    public String getMinutosSalida(){
        return editTextMinutosSalida.getText().toString();
    }

    public String getDiaReservaSesion() {
        return diaReservaSesion;
    }

    @Override
    public void onBackPressed() {
        Log.i(tag, "Ir a Calendario Reservas");
        Intent i = new Intent(ReservarActivity.this, CalendarioReservasActivity.class);
        startActivity(i);
        finish();
    }
}