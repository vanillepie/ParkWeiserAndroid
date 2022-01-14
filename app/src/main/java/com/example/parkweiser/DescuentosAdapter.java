package com.example.parkweiser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DescuentosAdapter extends RecyclerView.Adapter <DescuentosAdapter.ViewHolder> {

    private List<Descuento> descuentoList;
    private Context context;

    public DescuentosAdapter(List<Descuento> descuentoList, Context context){
        this.descuentoList = descuentoList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_descuento, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textNombreDescuento.setText(descuentoList.get(position).getNombre());
        holder.textCantidadDescuento.setText(descuentoList.get(position).getCantidad());

    }

    @Override
    public int getItemCount() {
        return descuentoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textNombreDescuento;
        private TextView textCantidadDescuento;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            textNombreDescuento = itemView.findViewById(R.id.textNombreDescuento);
            textCantidadDescuento = itemView.findViewById(R.id.textCantidadDescuento);
        }
    }
}