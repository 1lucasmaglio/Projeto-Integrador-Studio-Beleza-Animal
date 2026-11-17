package model;

public class Animal {

    private int codigo;
    private String nome;
    private String especie;
    private String raca;
    private String porte;
    private int codigoCliente;

    public Animal() {
    }

    public Animal(int codigo, String nome, String especie,
                  String raca, String porte, int codigoCliente) {
        this.codigo = codigo;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.porte = porte;
        this.codigoCliente = codigoCliente;
    }

    public Animal(String nome, String especie,
                  String raca, String porte, int codigoCliente) {
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.porte = porte;
        this.codigoCliente = codigoCliente;
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

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getPorte() {
        return porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }
}