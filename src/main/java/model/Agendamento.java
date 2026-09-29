package model;

public class Agendamento {

    private int codigo;
    private String data;
    private String hora;
    private String status;
    private int codigoAnimal;
    private int codigoServico;

    public Agendamento() {
    }

    public Agendamento(int codigo, String data, String hora,
                       String status, int codigoAnimal,
                       int codigoServico) {
        this.codigo = codigo;
        this.data = data;
        this.hora = hora;
        this.status = status;
        this.codigoAnimal = codigoAnimal;
        this.codigoServico = codigoServico;
    }

    public Agendamento(String data, String hora,
                       String status, int codigoAnimal,
                       int codigoServico) {
        this.data = data;
        this.hora = hora;
        this.status = status;
        this.codigoAnimal = codigoAnimal;
        this.codigoServico = codigoServico;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCodigoAnimal() {
        return codigoAnimal;
    }

    public void setCodigoAnimal(int codigoAnimal) {
        this.codigoAnimal = codigoAnimal;
    }

    public int getCodigoServico() {
        return codigoServico;
    }

    public void setCodigoServico(int codigoServico) {
        this.codigoServico = codigoServico;
    }
}