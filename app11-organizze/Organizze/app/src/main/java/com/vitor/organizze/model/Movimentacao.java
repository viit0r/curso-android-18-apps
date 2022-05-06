package com.vitor.organizze.model;

import com.google.firebase.database.DatabaseReference;
import com.vitor.organizze.config.FirebaseConfig;
import com.vitor.organizze.helper.Base64Custom;
import com.vitor.organizze.helper.DateCustom;

public class Movimentacao {
    private String data, categoria, descricao, tipo, key;
    private Double valor;

    public Movimentacao() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void salvar(String data) {
        DatabaseReference databaseReference = FirebaseConfig.getFirebaseDatabase();
        databaseReference.child("movimentacao")
                .child(Base64Custom.toBase64(FirebaseConfig.getAuth().getCurrentUser().getEmail()))
                .child(DateCustom.dataEscolhida( data ))
                .push()
                .setValue(this);
    }
}
