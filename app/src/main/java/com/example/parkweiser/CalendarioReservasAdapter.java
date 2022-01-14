package com.example.parkweiser;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.sundeepk.compactcalendarview.domain.Event;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CalendarioReservasAdapter extends RecyclerView.Adapter <CalendarioReservasAdapter.ViewHolder> {

    private List<Event> eventList;
    private Context context;
    private boolean borradoCorrecto = false;
    private String diaSeleccionado = "";

    public CalendarioReservasAdapter(List<Event> eventList, Context context, String diaSeleccionado){
        this.eventList = eventList;
        this.context = context;
        this.diaSeleccionado = diaSeleccionado;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_coche, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textDescripcionReserva.setText(eventList.get(position).getData().toString());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textDescripcionReserva;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            textDescripcionReserva = itemView.findViewById(R.id.textDescripcionReserva);

            itemView.findViewById(R.id.buttonEliminarReserva).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v){
                    borrarReserva(textDescripcionReserva.getText().toString().substring(34),
                            diaSeleccionado.substring(0, 10) + "'T'" +
                            textDescripcionReserva.getText().toString().substring(11, 16));
                    if(borradoCorrecto) {
                        Toast.makeText(context, "Reserva eliminada correctamente.", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(context, "No se pudo eliminar reserva.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private  void borrarReserva(String matricula, String entrada){
        String url = Ctes.SERVIDOR + "EliminarReserva?matricula=" + matricula + "&entrada=" + entrada;
        BorrarReservaThread thread = new BorrarReservaThread(this, url);
        try {
            thread.join();
        }catch (InterruptedException e){}
    }

    public void confimarBorrado(String response) throws JSONException {
        // TODO comprobar si se pasa bien
        if (response.equals("1")){
            borradoCorrecto = true;
        }
        else{
            borradoCorrecto = false;
        }
    }
}