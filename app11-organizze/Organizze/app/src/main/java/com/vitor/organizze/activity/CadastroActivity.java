package com.vitor.organizze.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.vitor.organizze.R;
import com.vitor.organizze.config.FirebaseConfig;
import com.vitor.organizze.helper.Base64Custom;
import com.vitor.organizze.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private EditText nome, email, senha;
    private Button btnCadastrar;
    private FirebaseAuth auth;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        getSupportActionBar().hide();

        nome = findViewById(R.id.editNome);
        email = findViewById(R.id.editEmail);
        senha = findViewById(R.id.editSenha);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textoNome = nome.getText().toString();
                String textoEmail = email.getText().toString();
                String textoSenha = senha.getText().toString();
                if (!textoNome.isEmpty()){
                    if (!textoEmail.isEmpty()){
                        if (!textoSenha.isEmpty()){
                            usuario = new Usuario();
                            usuario.setNome(textoNome);
                            usuario.setEmail(textoEmail);
                            usuario.setSenha(textoSenha);
                            cadastrarUsuario();
                        }else{
                            Toast.makeText(CadastroActivity.this, "Senha obrigatória!", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(CadastroActivity.this, "Email obrigatório!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CadastroActivity.this, "Nome obrigatório!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void cadastrarUsuario() {
        auth = FirebaseConfig.getAuth();
        auth.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String idUser = Base64Custom.toBase64(usuario.getEmail());
                    usuario.setIdUser( idUser );
                    usuario.salvar();
                    Toast.makeText(CadastroActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    String exception = "";
                    try {
                        throw task.getException();
                    } catch ( FirebaseAuthWeakPasswordException e) {
                        exception = "Digite uma senha mais forte!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        exception = "Por favor, digite um e-mail válido!";
                    } catch (FirebaseAuthUserCollisionException e) {
                        exception = "Conta já cadastrada no sistema!";
                    } catch (Exception e) {
                        exception = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this, exception, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}