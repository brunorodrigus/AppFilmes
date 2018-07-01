package com.martin.filme;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.martin.filme.adaptador.AdapterListaResultados;
import com.martin.filme.entidade.Filme;
import com.martin.filme.persistencia.DbConexao;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.Collections;
import java.util.List;

import  android.widget.ImageView ;

import cz.msebera.android.httpclient.Header;

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton adicaoButton, removerButton, editarButton;
    private EditText tituloEditText, urlEditText, generoEditText,avaliacaoEditText, lancamentoEditText;
    private AdapterListaResultados adapterListaResultados;
    private boolean inserir = true;
    private long idAlteracao;
    private Context context = this;

    ImageView ivImageFromUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);


        adicaoButton = (ImageButton) findViewById(R.id.adicaoImageButton);
        adicaoButton.setOnClickListener(this);

        removerButton = (ImageButton) findViewById(R.id.removerImageButton);
        removerButton.setOnClickListener(this);

        editarButton = (ImageButton) findViewById(R.id.editarImageButton);
        editarButton.setOnClickListener(this);

        tituloEditText = (EditText) findViewById(R.id.tituloEditText);
        urlEditText = (EditText) findViewById(R.id.urlEditText);
        generoEditText = (EditText) findViewById(R.id.generoEditText);
        avaliacaoEditText = (EditText) findViewById(R.id.avaliacaoEditText);
        lancamentoEditText = (EditText) findViewById(R.id.lancamentoEditText);


        ListView listView = (ListView) findViewById(R.id.listView);
        adapterListaResultados = new AdapterListaResultados(this);
        listView.setAdapter(adapterListaResultados);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int posicao, long id) {
                Filme filme = (Filme) adapterListaResultados.getItem(posicao);
                tituloEditText.setText(filme.getTitulo());
                urlEditText.setText(filme.getImagem());
                generoEditText.setText(filme.getGenresSTR());
                avaliacaoEditText.setText(filme.getAvaliacao());
                lancamentoEditText.setText(filme.getLancamento());
                inserir = false;
                idAlteracao = id;
            }
        });

        consultasWS();
    }

    public void consultasWS() {

        AsyncHttpClient cliente = new AsyncHttpClient();
        String url = "https://api.androidhive.info/json/movies.json";
        cliente.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Gson gson = new GsonBuilder().create();
                int qtdeFilmes = response.length();
                if (qtdeFilmes > 0) {
                    for (int i = 0; i < qtdeFilmes; i++) {
                        try {
                            String obj = response.getJSONObject(i).toString();
                            Filme filme = gson.fromJson(obj, Filme.class);
                            new DbConexao(context).inserir(filme);
                        } catch (JSONException e) {
                            Log.e("MoviesHome", e.toString());
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        String titulo = String.valueOf(tituloEditText.getText().toString());
        String url = String.valueOf(urlEditText.getText().toString());
        List <String> genero = Collections.singletonList(String.valueOf(generoEditText.getText().toString()));
        String avaliacao = String.valueOf(avaliacaoEditText.getText().toString());
        String lancamento = String.valueOf(lancamentoEditText.getText().toString());


        Filme filme = new Filme(titulo, url, genero, avaliacao, lancamento);
        DbConexao dbConexao = new DbConexao(this);

        switch (view.getId()){
            case R.id.adicaoImageButton:
                if(inserir)
                    dbConexao.inserir(filme);
                else {
                    break;
                }
                proLimpaEditText();
                break;
            case R.id.editarImageButton:
                filme.setId(idAlteracao);
                dbConexao.alterar(filme);
                inserir = true;
                proLimpaEditText();
                break;
            case R.id.removerImageButton:
                filme.setId(idAlteracao);
                dbConexao.excluir(idAlteracao);
                proLimpaEditText();
                break;
        }

        adapterListaResultados.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapterListaResultados.notifyDataSetChanged();
    }

    void proLimpaEditText(){
        tituloEditText.setText("");
        urlEditText.setText("");
        generoEditText.setText("");
        avaliacaoEditText.setText("");
        lancamentoEditText.setText("");
    }

}








