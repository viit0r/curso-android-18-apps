package com.vitor.caraoucoroa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ResultadoActivity extends AppCompatActivity {

    ImageView resultado, voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        resultado = findViewById(R.id.imgResultado);
        voltar = findViewById(R.id.imgVoltar);
        Bundle bundle = getIntent().getExtras();

        if (bundle.getInt("numero") == 0){
            resultado.setImageResource(R.drawable.moeda_cara);
        }else{
            resultado.setImageResource(R.drawable.moeda_coroa);
        }

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}