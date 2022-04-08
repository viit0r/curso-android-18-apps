package com.vitor.minhasanotacoes;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.vitor.minhasanotacoes.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AnotacaoPreferencias preferencias;
    private EditText anotacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        anotacao = findViewById(R.id.editAnotacao);
        preferencias = new AnotacaoPreferencias(getApplicationContext());

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (anotacao.getText().toString().equals("")){
                    Snackbar.make(view, "Preencha a anotação!", Snackbar.LENGTH_LONG).show();
                }else{
                    preferencias.salvarAnotacao(anotacao.getText().toString());
                    Snackbar.make(view, "Anotação salva com sucesso!", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        String anotacaoRecuperada = preferencias.recuperarAnotacao();
        if (!anotacaoRecuperada.equals("")){
            anotacao.setText(anotacaoRecuperada);
        }
    }
}