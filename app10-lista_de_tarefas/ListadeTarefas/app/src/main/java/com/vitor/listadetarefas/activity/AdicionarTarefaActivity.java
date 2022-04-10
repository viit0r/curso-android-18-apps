package com.vitor.listadetarefas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vitor.listadetarefas.R;
import com.vitor.listadetarefas.helper.TarefaDAO;
import com.vitor.listadetarefas.model.Tarefa;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private TextInputEditText editTarefa;
    private Tarefa tarefaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        editTarefa = findViewById(R.id.editTarefa);

        //Recuperar tarefa, caso de edição
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("TAREFA");

        //Cpnfigura tarefa na caixa de texto
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
            case R.id.itemSalvar:
                //Execuca ação para salvar tarefa
                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
                String nomeTarefa = editTarefa.getText().toString();
                Tarefa t = new Tarefa();
                if (tarefaAtual!= null){ //Edição
                    if (!nomeTarefa.isEmpty()){
                        t.setNomeTarefa(nomeTarefa);
                        t.setId(tarefaAtual.getId());

                        //Atualizar no BD
                        if (tarefaDAO.atualizar(t)){
                            finish();
                            Toast.makeText(getApplicationContext(), "Tarefa atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Erro ao atualizar tarefa", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else{ // Criação
                    if (!nomeTarefa.isEmpty()){
                        t.setNomeTarefa(nomeTarefa);

                        if (tarefaDAO.salvar(t)){
                            finish();
                            Toast.makeText(getApplicationContext(), "Tarefa salva com sucesso!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Erro ao salvar tarefa", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}