package com.vittor.pedrapepeloutesoura;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void pedraSelecionada(View view){
        this.opcaoSelecionada("pedra");
    }
    public void papelSelecionado(View view){
        this.opcaoSelecionada("papel");
    }

    public void tesouraSeleciondada(View view){
        this.opcaoSelecionada("tesoura");
    }
    public void opcaoSelecionada(String opcaoSelecionada){
        ImageView img = findViewById(R.id.imgResultado);
        TextView textoResultado = findViewById(R.id.txtResultado);
        int numero = new Random().nextInt(3);
        String[] opcoes = {"pedra", "papel", "tesoura"};
        String opcaoApp = opcoes[numero];

        switch (opcaoApp){
            case "pedra":
                img.setImageResource(R.drawable.pedra);
                break;
            case "papel":
                img.setImageResource(R.drawable.papel);
                break;
            case "tesoura":
                img.setImageResource(R.drawable.tesoura);
                break;
        }
        if ((opcaoSelecionada == "pedra" && opcaoApp == "papel") ||
                (opcaoSelecionada == "papel" && opcaoApp == "tesoura") ||
                (opcaoSelecionada == "tesoura" && opcaoApp == "pedra")){//App ganhador
            textoResultado.setText("Você perdeu :(");

        }else if((opcaoApp == "pedra" && opcaoSelecionada == "papel") ||
                (opcaoApp == "papel" && opcaoSelecionada == "tesoura") ||
                (opcaoApp == "tesoura" && opcaoSelecionada == "pedra")){//Usuario ganhador
            textoResultado.setText("Você ganhou :)");
        }else{//Empate
            textoResultado.setText("Empate :/");
        }
    }
}