package com.vitor.organizze.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.vitor.organizze.R;
import com.vitor.organizze.config.FirebaseConfig;
import com.vitor.organizze.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText email, senha;
    private Button btnEntrar;
    private Usuario usuario;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        email = findViewById(R.id.editEmailLogin);
        senha = findViewById(R.id.editSenhaLogin);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textoEmail = email.getText().toString();
                String textoSenha = senha.getText().toString();

                if (!textoEmail.isEmpty()){
                    if (!textoSenha.isEmpty()){
                        usuario = new Usuario();
                        usuario.setEmail(textoEmail);
                        usuario.setSenha(textoSenha);
                        validarLogin();
                    }else{
                        Toast.makeText(LoginActivity.this, "Senha obrigatória!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "Email obrigatório!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void validarLogin() {
        auth = FirebaseConfig.getAuth();
        auth.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Logado com sucesso!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, PrincipalActivity.class));
                } else {

                    String exception = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        exception = "Usuário não cadastrado no sistema!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        exception = "Email e/ou senha inválidos. Por favor, verifique e tente novamente.";
                    } catch (Exception e) {
                        exception = "Erro ao logar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this, exception, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}