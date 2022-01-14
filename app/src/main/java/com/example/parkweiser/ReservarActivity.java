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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ReservarActivity extends AppCompatActivity {

    private String tag = "ReservarActivity";
    private TextView textDiaReserva;
    private String diaReservaSesion = "";
    private Button buttonAniadirReserva;
    private EditText editTextHoraEntrada;
    private EditText editTextMinutosEntrada;
    private EditText editTextHoraSalida;
    private EditText editTextMinutosSalida;
    private String horaEntrada;
    private String minutosEntrada;
    private String horaSalida;
    private String minutosSalida;
    private Boolean reservaPosible;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar);

        hideActionBar();
        initElementos();

        buttonAniadirReserva.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "Intento de iniciar sesion");

                String horaEntrada = editTextHoraEntrada.getText().toString();
                String minutosEntrada = editTextMinutosEntrada.getText().toString();
                String horaSalida = editTextHoraSalida.getText().toString();
                String minutosSalida = editTextMinutosSalida.getText().toString();

                if (esFechaValida(horaEntrada, minutosEntrada, horaSalida, minutosSalida)){

                    getReserva(horaEntrada, minutosEntrada, horaSalida, minutosSalida);

                    if(reservaPosible) {
                        Intent i = new Intent(ReservarActivity.this, CalendarioReservasActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else{
                        Toast.makeText(ReservarActivity.this, "No se puedo realizar la reserva.", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(ReservarActivity.this, "Fecha no correcta.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getReserva(String horaEntrada, String minutosEntrada, String horaSalida, String minutosSalida){
        // TODO poner servlet y fecha

        String fechaEntrada = diaReservaSesion + horaEntrada + ":" + minutosEntrada;
        String fechaSalida = diaReservaSesion + horaSalida + ":" + minutosSalida;

        String url = Ctes.SERVIDOR + "servlet?entrada=&salida";
        ReservarThread thread = new ReservarThread(this, url);
        try {
            thread.join();
        }catch (InterruptedException e){}
    }

    public void confirmaReserva(String response) throws JSONException {
        // TODO decir si fue bien
    }

    private Boolean esFechaValida(String horaEntrada, String minutosEntrada, String horaSalida, String minutosSalida){
        boolean valido = false;

        if (esHorasValido(horaEntrada) && esHorasValido(horaSalida) && esMinutosValido(minutosEntrada) && esMinutosValido(minutosSalida)){
            try {
                Date entrada = Ctes.FORMATO_HORA.parse(horaEntrada + ":" + minutosEntrada);
                Date salida = Ctes.FORMATO_HORA.parse(horaSalida + ":" + minutosSalida);

                if(entrada.before(salida)){
                    valido = true;
                }

            } catch (ParseException e) {
                valido = false;
            }
        }

        return valido;
    }

    private Boolean esMinutosValido(String tiempoStr){
        boolean valido = true;
        int tiempo;
        try{
            tiempo = Integer.parseInt(tiempoStr);
            if (0 > tiempo || tiempo >= 60){
                valido = false;
            }
        }
        catch(Exception e){
            valido = false;
        }
        return valido;
    }
    private Boolean esHorasValido(String tiempoStr){
        boolean valido = true;
        int tiempo;
        try{
            tiempo = Integer.parseInt(tiempoStr);
            if (0 > tiempo || tiempo >= 23){
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
        this.textDiaReserva = this.findViewById(R.id.textDiaReserva);
        this.editTextHoraEntrada = this.findViewById(R.id.editTextHoraEntrada);
        this.editTextMinutosEntrada = this.findViewById(R.id.editTextMinutosEntrada);
        this.editTextHoraSalida = this.findViewById(R.id.editTextHoraSalida);
        this.editTextMinutosSalida = this.findViewById(R.id.editTextMinutosSalida);
        this.buttonAniadirReserva = this.findViewById(R.id.buttonAniadirReserva);

        diaReservaSesion = getIntent().getStringExtra(Ctes.FECHA_RESERVA_SESION).substring(0, 10);
        textDiaReserva.setText(diaReservaSesion);
    }

    @Override
    public void onBackPressed() {
        Log.i(tag, "Ir a Calendario Reservas");
        Intent i = new Intent(ReservarActivity.this, CalendarioReservasActivity.class);
        startActivity(i);
        finish();
    }
}