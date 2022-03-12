package com.vittor.calculadoradegorjeta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editValor;
    private TextView txtPorcentagem, txtTotal, txtGorjeta;
    private SeekBar seekGorjeta;

    private double porcentagem = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editValor = findViewById(R.id.editValor);
        txtPorcentagem = findViewById(R.id.txtPorcentagem);
        txtTotal = findViewById(R.id.txtTotal);
        txtGorjeta = findViewById(R.id.txtGorjeta);
        seekGorjeta = findViewById(R.id.seekBarGorjeta);

        seekGorjeta.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                porcentagem = progress;
                txtPorcentagem.setText(Math.round(porcentagem) + "%");
                calcular();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void calcular(){
        String valorRecuperado = editValor.getText().toString();
        if (valorRecuperado == null ||valorRecuperado.equals("")) {
            Toast.makeText(this, "Digite um valor primeiro!", Toast.LENGTH_SHORT).show();
        }else{
            //Converte String para Double
            double valorDigitado = Double.parseDouble(valorRecuperado);

            // Calcula gorjeta e total
            double gorjeta = valorDigitado * (porcentagem / 100);
            double total = gorjeta + valorDigitado;

            // Exibe a gorjeta e total
            System.out.println("GORJETA: " + gorjeta);
            System.out.println("TOTAL: " + total);
            txtGorjeta.setText("R$ " + gorjeta);
            txtTotal.setText("R$ " + total);
        }
    }
}