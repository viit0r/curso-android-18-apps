package com.vitor.listadetarefas.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vitor.listadetarefas.R;
import com.vitor.listadetarefas.helper.TarefasDAO;
import com.vitor.listadetarefas.model.Tarefa;

import java.util.List;

public class TarefasAdapter extends RecyclerView.Adapter<TarefasAdapter.MyViewHolder> {

    private List<Tarefa> listTarefas;

    public TarefasAdapter(List<Tarefa> tarefas) {
        this.listTarefas = tarefas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_tarefa_adapter, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Tarefa tarefa = listTarefas.get(position);
        holder.txtTarefa.setText(tarefa.getNomeTarefa());
    }

    @Override
    public int getItemCount() {
        return this.listTarefas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtTarefa;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTarefa = itemView.findViewById(R.id.txtTarefa);

        }
    }

}
