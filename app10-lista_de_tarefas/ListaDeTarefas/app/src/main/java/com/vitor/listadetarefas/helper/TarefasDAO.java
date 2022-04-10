package com.vitor.listadetarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vitor.listadetarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefasDAO implements iTarefaDAO{

    private SQLiteDatabase escrever, ler;

    public TarefasDAO(Context context) {
        DBHelper db = new DBHelper(context);
        escrever = db.getWritableDatabase();
        ler = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());
        try{
            escrever.insert(DBHelper.TABELA_TAREFAS, null, cv);
            Log.i("INFO DB", "Sucesso ao salvar tarefa!");

        }catch (Exception e){
            Log.e("INFO DB", "Erro ao salvar tarefa: " + e.getMessage());
            return false;
        }
        escrever.close();
        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {
        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());

        try {
            String[] args = {tarefa.getId().toString()};
            escrever.update(DBHelper.TABELA_TAREFAS, cv, "id = ?", args);
            Log.i("INFO DB", "Sucesso ao atualizar tarefa!");
        }catch (Exception e){
            Log.e("INFO DB", "Erro ao atualizar tarefa: " + e.getMessage());
            return false;
        }
        escrever.close();
        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {
        try {
            String[] args = {tarefa.getId().toString()};
            escrever.delete(DBHelper.TABELA_TAREFAS, "id=?", args);
            Log.i("INFO DB", "Sucesso ao excluir tarefa!");
        }catch (Exception e){
            Log.e("INFO DB", "Erro ao excluir tarefa: " + e.getMessage());
            return false;
        }
        escrever.close();
        return true;
    }

    @Override
    public List<Tarefa> listar() {
        List<Tarefa> tarefaList = new ArrayList<>();

        String sql = "SELECT * FROM " + DBHelper.TABELA_TAREFAS + " ;";
        try {
            Cursor cursor = ler.rawQuery(sql, null);
            if(cursor.moveToFirst()){
                do {
                    Tarefa tarefa = new Tarefa();

                    Long id = cursor.getLong( cursor.getColumnIndexOrThrow("id") );
                    String nomeTarefa = cursor.getString( cursor.getColumnIndexOrThrow("nome") );

                    tarefa.setId(id);
                    tarefa.setNomeTarefa(nomeTarefa);

                    tarefaList.add(tarefa);
                }while (cursor.moveToNext());
            }
            cursor.close();
            ler.close();
            Log.e("INFO DB", "Sucesso ao listar tarefa!");
        }catch (Exception e){
            Log.e("INFO DB", "Erro ao listar tarefa: " + e.getMessage());
        }

        return tarefaList;
    }
}
