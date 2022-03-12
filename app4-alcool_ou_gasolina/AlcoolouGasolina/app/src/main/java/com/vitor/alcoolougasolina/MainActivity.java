package com.vitor.alcoolougasolina;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText alcool, gasolina;
    private TextView resultado;
    private Button botao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alcool = findViewById(R.id.editAlcool);
        gasolina = findViewById(R.id.editGasolina);
        resultado = findViewById(R.id.txtResultado);
        botao = findViewById(R.id.btnCalcular);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String precoAlcool = alcool.getText().toString();
                String precoGasolina = gasolina.getText().toString();

                Boolean validarDados = validar(precoAlcool, precoGasolina);
                if( validarDados ){

                    if (Double.parseDouble(precoAlcool) / Double.parseDouble(precoGasolina) >= 0.7){
                        resultado.setText("Melhor utilizar GASOLINA!");
                    }else{
                        resultado.setText("Melhor utilizar ALCOOL!");
                    }
                }
            }
        });
    }

    public Boolean validar(String alcool, String gasolina){

        Boolean validarDados = true;

        if (alcool == null || alcool.equals("")){
            validarDados = false;
            Toast.makeText(this, "Preencha os preços primeiro!", Toast.LENGTH_SHORT).show();
        }else if (gasolina == null || gasolina.equals("")){
            validarDados = false;
            Toast.makeText(this, "Preencha os preços primeiro!", Toast.LENGTH_SHORT).show();
        }

        return validarDados;

    }

}