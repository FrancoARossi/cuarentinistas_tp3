package com.example.cuarentinistas_tp3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorInversiones extends RecyclerView.Adapter<AdaptadorInversiones.ViewHolderInversiones> {

    ArrayList<Inversion> listaInversiones;

    public AdaptadorInversiones(ArrayList<Inversion> listaInversiones) {
        this.listaInversiones = listaInversiones;
    }

    @NonNull
    @Override
    public AdaptadorInversiones.ViewHolderInversiones onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_inversiones,null,false);
        return new ViewHolderInversiones(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorInversiones.ViewHolderInversiones holder, int position) {
        holder.nombreBono.setText(listaInversiones.get(position).getNombreBono());
        holder.detalle.setText(listaInversiones.get(position).getDetalle());
    }

    @Override
    public int getItemCount() {
        return listaInversiones.size();
    }

    public class ViewHolderInversiones extends RecyclerView.ViewHolder {
        TextView nombreBono, detalle;

        public ViewHolderInversiones(@NonNull View itemView) {
            super(itemView);
            nombreBono = (TextView) itemView.findViewById(R.id.idNombreBono);
            detalle = (TextView) itemView.findViewById(R.id.idDetalleInversion);
        }
    }
}
