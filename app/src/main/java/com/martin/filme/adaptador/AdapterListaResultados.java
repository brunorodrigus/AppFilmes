package com.martin.filme.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.martin.filme.R;
import com.martin.filme.entidade.Filme;
import com.martin.filme.persistencia.DbConexao;
import com.squareup.picasso.Picasso;

public class AdapterListaResultados extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;

    public AdapterListaResultados(Context context){
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return new DbConexao(context).getQuantidade();
    }

    @Override
    public Object getItem(int i) {
        return new DbConexao(context).getItem(i);
    }

    @Override
    public long getItemId(int i) {
        return new DbConexao(context).getItem(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = layoutInflater.inflate(R.layout.item_listview, null);

        TextView tituloTextView = (TextView) view.findViewById(R.id.tituloTextView);
        TextView generoTextView = (TextView) view.findViewById(R.id.generoTextView);
        TextView avaliacaoTextView = (TextView) view.findViewById(R.id.avaliacaoTextView);
        TextView lancamentoTextView = (TextView) view.findViewById(R.id.lancamentoTextView);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);

        Filme filme = (Filme) getItem(i);
        if(filme.getImagem() != null && !filme.getImagem().isEmpty())
            Picasso.get().load(filme.getImagem()).into(imageView);
        tituloTextView.setText(filme.getTitulo());
        generoTextView.setText(filme.getGenresSTR());
        avaliacaoTextView.setText(filme.getAvaliacao());
        lancamentoTextView.setText(filme.getLancamento());

        return view;
    }
}
