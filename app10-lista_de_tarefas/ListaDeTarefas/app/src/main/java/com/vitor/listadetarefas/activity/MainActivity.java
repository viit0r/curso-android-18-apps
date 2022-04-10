package com.vitor.listadetarefas.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vitor.listadetarefas.R;
import com.vitor.listadetarefas.adapter.TarefasAdapter;
import com.vitor.listadetarefas.databinding.ActivityMainBinding;
import com.vitor.listadetarefas.helper.DBHelper;
import com.vitor.listadetarefas.helper.RecyclerItemClickListener;
import com.vitor.listadetarefas.helper.TarefasDAO;
import com.vitor.listadetarefas.model.Tarefa;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private RecyclerView recyclerView;
    private TarefasAdapter adapter;
    private List<Tarefa> listaTarefas = new ArrayList<>();
    private Tarefa tarefaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        recyclerView = findViewById(R.id.recyclerTarefas);

        // Adicionar evento de click
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Tarefa tarefaSelecionada = listaTarefas.get(position);
                Intent intent = new Intent(MainActivity.this, AdicionarTarefaActivity.class);
                intent.putExtra("TAREFA", tarefaSelecionada);
                startActivity(intent);

            }

            @Override
            public void onLongItemClick(View view, int position) {

                // Recuperar tarefa selecionada
                tarefaSelecionada = listaTarefas.get(position);

                //ALERT DIALOG
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                //Configurar titulo e mensagem
                dialog.setTitle("Confirmar exclusão");
                dialog.setMessage("Deseja excluir a tarefa "+ tarefaSelecionada.getNomeTarefa()  + "?");
                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TarefasDAO tarefasDAO = new TarefasDAO(getApplicationContext());
                        if (tarefasDAO.deletar(tarefaSelecionada)){
                            carregarListaTarefas();
                            Toast.makeText(getApplicationContext(), "Sucesso ao excluir tarefa!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Erro ao excluir tarefa!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.setNegativeButton("Não", null);
                dialog.create();
                dialog.show();
            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        }
        ));

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AdicionarTarefaActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        carregarListaTarefas();
        super.onStart();
    }

    public void carregarListaTarefas(){
        // Listar tarefas
        // Inserção das tarefas
        TarefasDAO tarefasDAO = new TarefasDAO(getApplicationContext());
        listaTarefas = tarefasDAO.listar();

        // Cpnfiguração do apapter
        adapter = new TarefasAdapter( listaTarefas );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter( adapter );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}