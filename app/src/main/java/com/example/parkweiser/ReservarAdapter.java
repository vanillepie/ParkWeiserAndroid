package com.example.parkweiser;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.List;

public class ReservarAdapter extends RecyclerView.Adapter <ReservarAdapter.ViewHolder> {

    private String tag = "ReservarAdapter";
    private List<Coche> cocheList;
    private Context context;
    private String diaReservaSesion = "";
    private ReservarActivity reservarActivity;
    private Boolean reservaPosible = false;

    public ReservarAdapter(List<Coche> cocheList, Context context){
        this.cocheList = cocheList;
        this.context = context;
        this.reservarActivity = (ReservarActivity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_coche_reserva, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textMatriculaCocheReserva.setText(cocheList.get(position).getMatricula());
    }

    @Override
    public int getItemCount() {
        return cocheList.size();
    }


    //TODO ver si se puede hacer non static
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textMatriculaCocheReserva;
        private Button buttonAniadirReserva;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            textMatriculaCocheReserva = itemView.findViewById(R.id.textMatriculaCocheReserva);
            buttonAniadirReserva = itemView.findViewById(R.id.buttonAniadirReserva);

            itemView.findViewById(R.id.buttonAniadirReserva).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v){
                    Log.i(tag, "Intento de reservar");

                    String horaEntrada = reservarActivity.getHoraEntrada();
                    String minutosEntrada = reservarActivity.getMinutosEntrada();
                    String horaSalida = reservarActivity.getHoraSalida();
                    String minutosSalida = reservarActivity.getMinutosSalida();

                    if (Ctes.esFechaValida(horaEntrada, minutosEntrada, horaSalida, minutosSalida)){

                        getReserva(textMatriculaCocheReserva.getText().toString(), horaEntrada, minutosEntrada, horaSalida, minutosSalida);

                        if(reservaPosible) {
                            Intent i = new Intent(reservarActivity, CalendarioReservasActivity.class);
                            reservarActivity.startActivity(i);
                            reservarActivity.finish();
                        }
                        else{
                            Toast.makeText(reservarActivity, "No se puedo realizar la reserva.", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(reservarActivity, "Fecha no correcta.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
    private void getReserva(String matricula, String horaEntrada, String minutosEntrada, String horaSalida, String minutosSalida){
        diaReservaSesion = reservarActivity.getDiaReservaSesion();
        String fechaEntrada = diaReservaSesion + "'T'" + horaEntrada + ":" + minutosEntrada;
        String fechaSalida = diaReservaSesion + "'T'" + horaSalida + ":" + minutosSalida;

        String url = Ctes.SERVIDOR + "ReservarEstacionamiento?matricula=" + matricula + "&entrada=" + fechaEntrada +"&salida=" + fechaSalida;
        ReservarThread thread = new ReservarThread(this, url);
        try {
            thread.join();
        }catch (InterruptedException e){}
    }

    public void confirmaReserva(String response) throws JSONException {
        // TODO comprobar si se pasa bien
        if (response.equals("1")){
            reservaPosible = true;
        }
        else{
            reservaPosible = false;
        }
    }

}