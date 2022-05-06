package com.vitor.organizze.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.vitor.organizze.R;
import com.vitor.organizze.config.FirebaseConfig;
import com.vitor.organizze.helper.Base64Custom;
import com.vitor.organizze.helper.DateCustom;
import com.vitor.organizze.model.Movimentacao;
import com.vitor.organizze.model.Usuario;

public class DespesasActivity extends AppCompatActivity {

    private TextInputEditText campoData, campoCategoria, campoDescricao;
    private EditText campoValor;
    private FloatingActionButton buttonSalvar;
    private Movimentacao movimentacao;
    private DatabaseReference databaseReference = FirebaseConfig.getFirebaseDatabase();
    private FirebaseAuth auth = FirebaseConfig.getAuth();
    private Double despesaTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);

        campoValor = findViewById(R.id.editValorDespesa);
        campoData = findViewById(R.id.editDataDespesa);
        campoCategoria = findViewById(R.id.editCategoriaDespesa);
        campoDescricao = findViewById(R.id.editDescricao);
        //buttonSalvar = findViewById(R.id.fabSalvarDespesa);

        campoData.setText(DateCustom.dataAtual());
        recuperarDespesa();

    }

    public void salvarDespesa(View view) {
        if (validarDespesa()) {
            movimentacao = new Movimentacao();
            String data = campoData.getText().toString();
            Double valorRecuperado = Double.parseDouble(campoValor.getText().toString());
            movimentacao.setValor(valorRecuperado);
            movimentacao.setCategoria(campoCategoria.getText().toString());
            movimentacao.setDescricao(campoDescricao.getText().toString());
            movimentacao.setData(data);
            movimentacao.setTipo("d");

            Double despesaAtualizada = despesaTotal + valorRecuperado;
            atualizarDespesa(despesaAtualizada);

            movimentacao.salvar(data);
            finish();
        }
    }

    public Boolean validarDespesa() {
        String textoValor = campoValor.getText().toString();
        String textoData = campoData.getText().toString();
        String textoCategoria = campoCategoria.getText().toString();
        String textoDescricao = campoDescricao.getText().toString();

        if (!textoValor.isEmpty()) {
            if (!textoData.isEmpty()) {
                if (!textoCategoria.isEmpty()) {
                    if (!textoDescricao.isEmpty()) {
                        return true;
                    } else {
                        Toast.makeText(DespesasActivity.this, "Descrição não foi preenchida!", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    Toast.makeText(DespesasActivity.this, "Categoria não foi preenchido!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                Toast.makeText(DespesasActivity.this, "Data não foi preenchida!", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(DespesasActivity.this, "Valor não foi preenchido!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void recuperarDespesa() {
        String idUser = auth.getCurrentUser().getEmail();
        DatabaseReference usuario = databaseReference.child("usuarios").child(Base64Custom.toBase64(idUser));
        usuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario u = snapshot.getValue(Usuario.class);
                despesaTotal = u.getDespesaTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void atualizarDespesa(Double despesa){
        String idUser = auth.getCurrentUser().getEmail();
        DatabaseReference usuario = databaseReference.child("usuarios").child(Base64Custom.toBase64(idUser));

        usuario.child("despesaTotal").setValue(despesa);
    }
}