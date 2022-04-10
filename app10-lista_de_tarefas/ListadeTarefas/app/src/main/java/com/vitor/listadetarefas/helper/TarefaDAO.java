package com.vitor.listadetarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vitor.listadetarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements  iTarefaDAO{

    private SQLiteDatabase escrever;
    private SQLiteDatabase ler;
    private ContentValues cv = new ContentValues();

    public TarefaDAO(Context context) {
        DBHelper db = new DBHelper(context);
        escrever = db.getWritableDatabase();
        ler = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {
        cv.put("nome", tarefa.getNomeTarefa());
        try {
            escrever.insert(DBHelper.TABELA_TAREFAS, null, cv);
            Log.i("INFO DB", "Tarefa salva com sucesso!");
        }catch (Exception e){
            Log.e("INFO DB", "Erro ao salvar tarefa: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {
        cv.put("nome", tarefa.getNomeTarefa());
        try {
            String[] args = {tarefa.getId().toString()};
            escrever.update(DBHelper.TABELA_TAREFAS, cv, "id=?", args);
        }catch (Exception e){
            Log.e("INFO DB", "Erro ao atualizar tarefa: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {

        try {
            String[] args = {tarefa.getId().toString()};
            escrever.delete(DBHelper.TABELA_TAREFAS, "id=?", args);
            Log.i("INFO DB", "Tarefa removida com sucesso!");
        }catch (Exception e){
            Log.e("INFO DB", "Erro ao deletar tarefa: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Tarefa> listar() {
        List<Tarefa> listaTtarefas = new ArrayList<>();

        try {
            String sql = "SELECT * FROM " + DBHelper.TABELA_TAREFAS + " ;";
            Cursor cursor = ler.rawQuery(sql, null);
            cursor.moveToFirst();
            do {
                Tarefa t = new Tarefa();
                t.setId(cursor.getLong(cursor.getColumnIndexOrThrow("id")));
                t.setNomeTarefa(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
                listaTtarefas.add(t);
            }while (cursor.moveToNext());
        }catch (Exception e){
            Log.e("INFO DB", "Erro ao listar tarefas: " + e.getMessage());
        }
        return listaTtarefas;
    }
}
