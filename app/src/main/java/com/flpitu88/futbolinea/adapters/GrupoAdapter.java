package com.flpitu88.futbolinea.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flpitu88.futbolinea.R;
import com.flpitu88.futbolinea.model.Grupo;

import java.util.List;

public class GrupoAdapter extends RecyclerView.Adapter<GrupoAdapter.ProductViewHolder>{

    private Context context;
    private List<Grupo> gruposLista;

    public GrupoAdapter(Context context, List<Grupo> listaGrupos) {
        this.context = context;
        this.gruposLista = listaGrupos;
    }

    @NonNull
    @Override
    public GrupoAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_item_grupo,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GrupoAdapter.ProductViewHolder holder, int position) {
        Grupo grupo = gruposLista.get(position);
        holder.itemGrupo.setText(grupo.getTitulo());
    }

    @Override
    public int getItemCount() {
        return gruposLista.size();
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView itemGrupo;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            itemGrupo = itemView.findViewById(R.id.itemGrupo);
        }

    }
}
