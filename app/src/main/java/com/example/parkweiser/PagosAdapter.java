package com.example.parkweiser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class PagosAdapter extends RecyclerView.Adapter <PagosAdapter.ViewHolder> {

    private List<AparcaEn> AparcaEnList;
    private Context context;
    SimpleDateFormat formatoFecha = new SimpleDateFormat("hh:mm dd/MM/yyyy");

    public PagosAdapter(List<AparcaEn> AparcaEnList, Context context){
        this.AparcaEnList = AparcaEnList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pago, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textMatriculaPago.setText(AparcaEnList.get(position).getMatricula());
        holder.textPrecioPago.setText(setTextPrecio(AparcaEnList.get(position).getPrecio()));
        holder.textEntradaPago.setText(AparcaEnList.get(position).getEntrada());
        holder.textSalidaPago.setText(AparcaEnList.get(position).getSalida());
    }

    @Override
    public int getItemCount() {
        return AparcaEnList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textMatriculaPago;
        private TextView textPrecioPago;
        private TextView textEntradaPago;
        private TextView textSalidaPago;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            textMatriculaPago = itemView.findViewById(R.id.textMatriculaPago);
            textPrecioPago = itemView.findViewById(R.id.textPrecioPago);
            textEntradaPago = itemView.findViewById(R.id.textEntradaPago);
            textSalidaPago = itemView.findViewById(R.id.textSalidaPago);
        }
    }

    private String setTextPrecio(float precio){
        String textPrecio = precio + " €";
        return textPrecio;
    }
}