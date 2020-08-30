package com.example.cuarentinistas_tp3;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorMovimientos extends RecyclerView.Adapter<AdaptadorMovimientos.ViewHolderMovimientos>{

    ArrayList<Movimiento> listaMovimientos;

    public AdaptadorMovimientos(ArrayList<Movimiento> listaMovimientos) {
        this.listaMovimientos = listaMovimientos;
    }

    @NonNull
    @Override
    public AdaptadorMovimientos.ViewHolderMovimientos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_movimientos,null,false);
        return new ViewHolderMovimientos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorMovimientos.ViewHolderMovimientos holder, int position) {
        holder.importe.setText(listaMovimientos.get(position).getImporte());
        if (listaMovimientos.get(position).getSaliente()) {
            String rojo = "#A90A0A";
            holder.importe.setTextColor(Color.parseColor(rojo));
        } else {
            String verde = "#0AA918";
            holder.importe.setTextColor(Color.parseColor(verde));
        }
        holder.detalle.setText(listaMovimientos.get(position).getDetalle());
    }

    @Override
    public int getItemCount() {
        return listaMovimientos.size();
    }

    public class ViewHolderMovimientos extends RecyclerView.ViewHolder {

        TextView importe, detalle;

        public ViewHolderMovimientos(@NonNull View itemView) {
            super(itemView);
            importe = (TextView) itemView.findViewById(R.id.idImporte);
            detalle = (TextView) itemView.findViewById(R.id.idDetalleMovimiento);
        }
    }
}
