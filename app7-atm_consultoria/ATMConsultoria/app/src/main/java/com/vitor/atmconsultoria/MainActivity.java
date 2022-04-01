package com.vitor.atmconsultoria;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.vitor.atmconsultoria.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarEmail();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_principal, R.id.nav_servico, R.id.nav_clientes, R.id.nav_contato, R.id.nav_sobre)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    public void enviarEmail(){
        // String celular = "tel:11977099694";
        // String imagem = "http://i.mlcdn.com.br/portaldalu/fotosconteudo/12123.jpg";
        // String endereco = "https://www.google.com/maps/place/Parque+Ibirapuera/@-23.5874162,-46.6576336,15z/data=!4m5!3m4!1s0x0:0xcb936109af9ce541!8m2!3d-23.5874162!4d-46.6576336";
        //Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(celular)); ABRE TELEFONE COM O NUMERO PRE DEFINIDO
        // Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imagem)); ABRE UMA IMAGEM NO NAVEGADOR
        // Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(endereco)); ABRE UM DETERMINADO ENDERECO
        Intent intent = new Intent(Intent.ACTION_SEND); // ENVIO DE EMAILS
        intent.putExtra( Intent.EXTRA_EMAIL, new String[]{"dev.vitor1@gmail.com"} );
        intent.putExtra( Intent.EXTRA_SUBJECT, "App#7 ATM Consultoria" );
        intent.putExtra( Intent.EXTRA_TEXT, "Mensagem automática" );
        //MIME TYPES
        //intent.setType("message/rfc822"); TYPE DE EMAIL
        // intent.setType("text/plain"); TYPE DE APP DE TEXTO
        // intent.setType("image/*"); // TYPE PARA APP DE IMAGEM
        intent.setType("application/pdf"); // TYPE PARA PDF
        startActivity(Intent.createChooser( intent, "Compartilhar" ));
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}