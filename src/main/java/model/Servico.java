package model;

public class Servico {

    private int codigo;
    private String nome;
    private String descricao;
    private double valor;
    private int duracao;

    public Servico() {
    }

    public Servico(int codigo, String nome, String descricao,
                   double valor, int duracao) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.duracao = duracao;
    }

    public Servico(String nome, String descricao,
                   double valor, int duracao) {
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.duracao = duracao;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }
}