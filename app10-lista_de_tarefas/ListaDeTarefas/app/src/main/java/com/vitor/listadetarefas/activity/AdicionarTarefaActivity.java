package com.vitor.listadetarefas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vitor.listadetarefas.R;
import com.vitor.listadetarefas.helper.TarefasDAO;
import com.vitor.listadetarefas.model.Tarefa;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private TextInputEditText editTarefa;
    private Tarefa tarefaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        editTarefa = findViewById(R.id.editTarefa);

        // Recupera tarefa, caso seja edição
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("TAREFA");

        // Configura tarefa na caixa de texto
        if (tarefaAtual != null){
            editTarefa.setText(tarefaAtual.getNomeTarefa());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_tarefa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemSalvar :
                // Salvar os dados
                TarefasDAO tarefasDAO = new TarefasDAO(getApplicationContext());
                if (tarefaAtual != null){
                    if (!editTarefa.getText().toString().isEmpty()){
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(editTarefa.getText().toString());
                        tarefa.setId(tarefaAtual.getId());

                        // atualizar no banco de dados
                        if (tarefasDAO.atualizar( tarefa )){
                         finish();
                         Toast.makeText(getApplicationContext(), "Sucesso ao atualizar tarefa!", Toast.LENGTH_SHORT).show();
                        }else{


                        }
                    }
                }else{
                    if (!editTarefa.getText().toString().isEmpty()){
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(editTarefa.getText().toString());
                        if(tarefasDAO.salvar(tarefa)){
                            finish();
                            Toast.makeText(getApplicationContext(), "Sucesso ao salvar tarefa!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Erro ao salvar tarefa!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}