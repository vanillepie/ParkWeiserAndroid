package com.example.parkweiser;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.List;

public class ConsultarCochesAdapter extends RecyclerView.Adapter <ConsultarCochesAdapter.ViewHolder> {

    private String tag = "ConsultarCochesAdapter";
    private List<Coche> cocheList;
    private Context context;
    private Boolean borradoCorrecto = false;
    private ConsultarCochesActivity consultarCochesActivity;

    public ConsultarCochesAdapter(List<Coche> cocheList, Context context){
        this.cocheList = cocheList;
        this.context = context;
        this.consultarCochesActivity = (ConsultarCochesActivity) context;
    }

    private void borrarCoche(String matricula){
        String url = Ctes.SERVIDOR + "EliminarVehiculo?matricula=" + matricula;
        BorrarCocheThread thread = new BorrarCocheThread(this, url);
        try {
            thread.join();
        }catch (InterruptedException e){}
    }

    public void confimarBorrado(String response) throws JSONException {
        if (response.contains("1")){
            borradoCorrecto = true;
        }
        else{
            borradoCorrecto = false;
        }
    }

    private String setTextElectrico(Boolean esElectrico){
        String textElectrico = "Es eléctrico: ";
        if (esElectrico){
            textElectrico += "Sí";
        }
        else{
            textElectrico += "No";
        }

        return textElectrico;
    }

    private String setTextMinusvalidos(Boolean esMinusvalidos){
        String textMinusvalidos = "Es de minusválidos: ";
        if (esMinusvalidos){
            textMinusvalidos += "Sí";
        }
        else{
            textMinusvalidos += "No";
        }

        return textMinusvalidos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_coche, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textMatricula.setText(cocheList.get(position).getMatricula());
        holder.textElectrico.setText(setTextElectrico(cocheList.get(position).getEsElectrico()));
        holder.textMinusvalidos.setText(setTextMinusvalidos(cocheList.get(position).getEsMinusvalidos()));
    }

    @Override
    public int getItemCount() {
        return cocheList.size();
    }


    //TODO ver si se puede hacer non static
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textMatricula;
        private TextView textElectrico;
        private TextView textMinusvalidos;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            textMatricula = itemView.findViewById(R.id.textMatriculaCoche);
            textElectrico = itemView.findViewById(R.id.textElectricoCoche);
            textMinusvalidos = itemView.findViewById(R.id.textMinusvalidosCoche);

            itemView.findViewById(R.id.buttonEliminarCoche).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v){
                    borrarCoche((String) textMatricula.getText());
                    if(borradoCorrecto) {
                        Log.i(tag, "Ir a registrar coche");
                        Intent i = new Intent(consultarCochesActivity, ConsultarCochesActivity.class);
                        i.putExtra(Ctes.CONDUCTOR_SESION, consultarCochesActivity.getIntent().getStringExtra(Ctes.CONDUCTOR_SESION));
                        consultarCochesActivity.startActivity(i);
                        consultarCochesActivity.finish();

                        Toast.makeText(context, "Coche eliminada correctamente.", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(context, "No se pudo eliminar reserva.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }


}