package model;

public class Servico {

    private int codigo;
    private String nome;
    private String descricao;
    private double valorP;
    private double valorM;
    private double valorG;
    private int duracao;

    public Servico() {
    }

    public Servico(int codigo, String nome, String descricao,
                double valorP, double valorM, double valorG, int duracao) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.valorP = valorP;
        this.valorM = valorM;
        this.valorG = valorG;
        this.duracao = duracao;
    }

    public Servico(String nome, String descricao,
                double valorP, double valorM, double valorG, int duracao) {
        this.nome = nome;
        this.descricao = descricao;
        this.valorP = valorP;
        this.valorM = valorM;
        this.valorG = valorG;
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

    public double getValorP() {
        return valorP;
    }

    public void setValorP(double valorP) {
        this.valorP = valorP;
    }

    public double getValorM() {
        return valorM;
    }

    public void setValorM(double valorM) {
        this.valorM = valorM;
    }

    public double getValorG() {
        return valorG;
    }

    public void setValorG(double valorG) {
        this.valorG = valorG;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }
}