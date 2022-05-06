package com.vitor.organizze.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.vitor.organizze.R;
import com.vitor.organizze.adapter.AdapterMovimentacao;
import com.vitor.organizze.config.FirebaseConfig;
import com.vitor.organizze.databinding.ActivityPrincipalBinding;
import com.vitor.organizze.helper.Base64Custom;
import com.vitor.organizze.model.Movimentacao;
import com.vitor.organizze.model.Usuario;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity {

    private ActivityPrincipalBinding binding;
    private MaterialCalendarView calendarView;
    private TextView textoSaudacao, textoSaldo;
    private FirebaseAuth auth = FirebaseConfig.getAuth();
    private DatabaseReference databaseReference = FirebaseConfig.getFirebaseDatabase();
    private DatabaseReference usuario;
    private ValueEventListener valueEventListenerUser, valueEventListenerMovimentacoes;
    private Double despesaTotal = 0.0, receitaTotal = 0.0, resumoUser = 0.0;
    private RecyclerView recyclerView;
    private AdapterMovimentacao adapterMovimentacao;
    private List<Movimentacao> movimentacoes = new ArrayList<>();
    private Movimentacao move;
    private DatabaseReference movimentacao;
    private String mesAnoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_principal);
        binding = ActivityPrincipalBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.toolbar.setTitle("");
        setSupportActionBar(binding.toolbar);

        textoSaudacao = findViewById(R.id.txtSaudacao);
        textoSaldo = findViewById(R.id.txtSaldo);
        calendarView = findViewById(R.id.calendarMeses);
        recyclerView = findViewById(R.id.rvMovimentos);
        configuraCalendarView();
        swipe();

        adapterMovimentacao = new AdapterMovimentacao(movimentacoes, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterMovimentacao);
    }

    public void swipe() {
        ItemTouchHelper.Callback itemTouch = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.ACTION_STATE_IDLE;
                int swipeFlags = ItemTouchHelper.START;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                excluirMovimentacao(viewHolder);
            }
        };
        new ItemTouchHelper(itemTouch).attachToRecyclerView(recyclerView);
    }

    public void excluirMovimentacao(RecyclerView.ViewHolder viewHolder) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Excluir movimentação da conta");
        alertDialog.setMessage("Tem certeza que deseja excluir essa movimentação da sua conta?");
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int position = viewHolder.getAdapterPosition();
                move = movimentacoes.get(position);
                String idUser = auth.getCurrentUser().getEmail();
                movimentacao = databaseReference.child("movimentacao").child(Base64Custom.toBase64(idUser)).child(mesAnoSelecionado);
                movimentacao.child(move.getKey()).removeValue();
                adapterMovimentacao.notifyItemRemoved(position);
                atualizarSaldo();
                Snackbar.make(viewHolder.itemView, "Excluido com sucesso", Snackbar.LENGTH_LONG).setAction("DESFAZER", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        movimentacao.push().setValue(move);
                        desfazerExclusao();
                    }
                }).show();
            }
        });
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                adapterMovimentacao.notifyDataSetChanged();
            }
        });
        alertDialog.create().show();
    }

    public void adicionarReceita(View view) {
        startActivity(new Intent(this, ReceitasActivity.class));
    }

    public void adicionarDespesa(View view) {
        startActivity(new Intent(this, DespesasActivity.class));
    }

    public void atualizarSaldo() {
        String idUser = auth.getCurrentUser().getEmail();
        usuario = databaseReference.child("usuarios").child(Base64Custom.toBase64(idUser));
        if (move.getTipo().equals("r")) {
            receitaTotal = receitaTotal - move.getValor();
            usuario.child("receitaTotal").setValue(receitaTotal);
        } else if (move.getTipo().equals("d")) {
            despesaTotal = despesaTotal - move.getValor();
            usuario.child("despesaTotal").setValue(despesaTotal);
        }
    }

    public void desfazerExclusao() {
        String idUser = auth.getCurrentUser().getEmail();
        usuario = databaseReference.child("usuarios").child(Base64Custom.toBase64(idUser));
        if (move.getTipo().equals("r")) {
            receitaTotal = receitaTotal + move.getValor();
            usuario.child("receitaTotal").setValue(receitaTotal);
        } else if (move.getTipo().equals("d")) {
            despesaTotal = despesaTotal + move.getValor();
            usuario.child("despesaTotal").setValue(despesaTotal);
        }
    }

    public void recuperarMovimentacoes() {
        String idUser = auth.getCurrentUser().getEmail();
        movimentacao = databaseReference.child("movimentacao").child(Base64Custom.toBase64(idUser)).child(mesAnoSelecionado);
        valueEventListenerMovimentacoes = movimentacao.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                movimentacoes.clear();
                for (DataSnapshot dados : snapshot.getChildren()) {
                    Movimentacao m = dados.getValue(Movimentacao.class);
                    m.setKey(dados.getKey());
                    movimentacoes.add(m);
                }
                adapterMovimentacao.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void recuperarResumo() {
        String idUser = auth.getCurrentUser().getEmail();
        usuario = databaseReference.child("usuarios").child(Base64Custom.toBase64(idUser));
        valueEventListenerUser = usuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario u = snapshot.getValue(Usuario.class);
                despesaTotal = u.getDespesaTotal();
                receitaTotal = u.getReceitaTotal();
                resumoUser = receitaTotal - despesaTotal;

                DecimalFormat decimalFormat = new DecimalFormat("0.00");

                textoSaudacao.setText("Olá, " + u.getNome());
                textoSaldo.setText("R$ " + decimalFormat.format(resumoUser));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void configuraCalendarView() {
        CharSequence[] meses = {"Janeiro", "Fevereiro", "Março", "Abril",
                "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro",
                "Novembro", "Dezembro"};

        calendarView.setTitleMonths(meses);

        CalendarDay dataAtual = calendarView.getCurrentDate();
        String mesSelecionado = String.format("%02d", dataAtual.getMonth());
        mesAnoSelecionado = mesSelecionado + "" + dataAtual.getYear();

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                String mesSelecionado = String.format("%02d", date.getMonth());
                mesAnoSelecionado = mesSelecionado + "" + date.getYear();

                movimentacao.removeEventListener(valueEventListenerMovimentacoes);
                recuperarMovimentacoes();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sair:
                auth.signOut();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarResumo();
        recuperarMovimentacoes();
    }

    @Override
    protected void onStop() {
        super.onStop();
        usuario.removeEventListener(valueEventListenerUser);
        movimentacao.removeEventListener(valueEventListenerMovimentacoes);
    }
}