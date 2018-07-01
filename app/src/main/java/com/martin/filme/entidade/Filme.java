package com.martin.filme.entidade;

import java.util.ArrayList;
import java.util.List;

public class Filme {

    private Long id;
    private String title;
    private String image;
    private List<String> genre = new ArrayList<>();
    private String rating;
    private String releaseYear;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return title;
    }

    public void setTitulo(String titulo) {
        this.title = titulo;
    }

    public String getImagem() {
        return image;
    }

    public void setImagem(String imagem) { this.image = imagem; }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public String getGenresSTR(){

        String gen= "";
        for (String gen_array : this.genre){
            gen = gen.concat(gen_array.toString()+ ", ");
        }
        if(gen.length() > 0)
            gen = gen.substring(0,gen.length() - 2);
        return gen;
    }

    public void setGenresSTR(String valor){
        String[] gen = valor.split(", ");
        for(String str : gen){
            this.addgen(new String(str));
        }
    }

    public void addgen(String genre){
        this.genre.add(genre);
    }
    public String getAvaliacao() {
        return rating;
    }

    public void setAvaliacao(String avaliacao) { this.rating = avaliacao; }

    public String getLancamento() {
        return releaseYear;
    }

    public void setLancamento(String lancamento) {
        this.releaseYear = lancamento;
    }

    public Filme(){

    }

    public Filme(String titulo, String imagem, List<String> genre, String avaliacao, String lancamento) {
        this.title = titulo;
        this.image =imagem;
        this.genre = genre;
        this.rating = avaliacao;
        this.releaseYear = lancamento;
    }

    @Override
    public String toString() {
        return "Filme{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", genre=" + genre +
                ", rating='" + rating + '\'' +
                ", releaseYear='" + releaseYear + '\'' +
                '}';
    }
}
