package com.martin.filme.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.martin.filme.entidade.Filme;
import java.util.ArrayList;

public class DbConexao extends SQLiteOpenHelper {
    private static final String NOME_BASE = "db.martin";
    private static final int VARSAO_BASE = 1;

    public DbConexao(Context context) {
        super(context, NOME_BASE, null, VARSAO_BASE);
    }

    public long inserir(Filme filme){
        ContentValues values = new ContentValues();
        values.put("title", filme.getTitulo());

        if(filme.getImagem() == null || filme.getImagem().isEmpty())
            values.put("image", "https://api.androidhive.info/json/movies/15.jpg");
        else
            values.put("image", filme.getImagem());

        values.put("genre", filme.getGenresSTR());
        values.put("rating", filme.getAvaliacao());
        values.put("releaseYear", filme.getLancamento());

        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert("tb_filme", null, values);

        db.close();
        return id;
    }

    public void alterar(Filme filme) {

        ContentValues values = new ContentValues();
        values.put("title", filme.getTitulo());
        values.put("image", filme.getImagem());
        values.put("genre", filme.getGenresSTR());
        values.put("rating", filme.getAvaliacao());
        values.put("releaseYear", filme.getLancamento());
        String whare = "id = ?";

        SQLiteDatabase db = getWritableDatabase();
        int id = db.update("tb_filme", values, whare,
                new String[]{String.valueOf(filme.getId())});
        db.close();
    }

    public void excluir(long id) {

        String whare = "id = ?";

        SQLiteDatabase db = getWritableDatabase();
        int ret = db.delete("tb_filme", whare,
                new String[]{String.valueOf(id)});
        db.close();
    }

    public int getQuantidade(){
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM tb_filme";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor.getCount();
    }

    public Filme getItem(int posicao){
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM tb_filme";
        Cursor cursor = db.rawQuery(selectQuery, null);

        ArrayList<Filme> filmeLista = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Filme filme = new Filme();
                filme.setId(cursor.getLong(0));
                filme.setTitulo(cursor.getString(1));
                filme.setImagem(cursor.getString(2));
                filme.setGenresSTR(cursor.getString(3));
                filme.setAvaliacao(cursor.getString(4));
                filme.setLancamento(cursor.getString(5));
                filmeLista.add(filme);
            } while (cursor.moveToNext());
            db.close();
        }
        return filmeLista.get(posicao);
    }


   @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CRIA_TABELA_filme = "CREATE TABLE tb_filme("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + "title text, "
                + "image text, "
                + "genre text, "
                + "rating text, "
                + "releaseYear text )";
        sqLiteDatabase.execSQL(CRIA_TABELA_filme);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String Delete = "DROP TABLE IF EXISTS tb_filmes";

        sqLiteDatabase.execSQL(Delete);
        onCreate(sqLiteDatabase);

    }
}
