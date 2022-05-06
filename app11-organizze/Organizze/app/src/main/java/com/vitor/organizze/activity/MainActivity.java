package com.vitor.organizze.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.vitor.organizze.R;
import com.vitor.organizze.activity.CadastroActivity;
import com.vitor.organizze.activity.LoginActivity;
import com.vitor.organizze.config.FirebaseConfig;

public class MainActivity extends IntroActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);

        setButtonBackVisible(false);
        setButtonNextVisible(false);

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_1)
                .build());
        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_2)
                .build());
        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_3)
                .build());
        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_4)
                .build());
        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_cadastro)
                .canGoForward(false)
                .build());
    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarUsuarioLogado();
    }

    public void cadastrar(View view) {
        startActivity(new Intent(this, CadastroActivity.class));
    }
    public void logar(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }
    public void verificarUsuarioLogado() {
        auth = FirebaseConfig.getAuth();
        // auth.signOut();
        if ( auth.getCurrentUser() != null ) {
            startActivity(new Intent(this, PrincipalActivity.class));
        }
    }
}