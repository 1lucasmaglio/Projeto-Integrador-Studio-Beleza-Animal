package model;

public class Agendamento {

    private int codigo;
    private String data;
    private String hora;
    private String status;
    private int codigoAnimal;
    private int codigoServico;
    private boolean levaETraz;

    public Agendamento() {
    }

    public Agendamento(int codigo, String data, String hora,
                       String status, int codigoAnimal,
                       int codigoServico, boolean levaETraz) {
        this.codigo = codigo;
        this.data = data;
        this.hora = hora;
        this.status = status;
        this.codigoAnimal = codigoAnimal;
        this.codigoServico = codigoServico;
        this.levaETraz = levaETraz;
    }

    public Agendamento(String data, String hora,
                       String status, int codigoAnimal,
                       int codigoServico, boolean levaETraz) {
        this.data = data;
        this.hora = hora;
        this.status = status;
        this.codigoAnimal = codigoAnimal;
        this.codigoServico = codigoServico;
        this.levaETraz = levaETraz;
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

    public boolean isLevaETraz() {
        return levaETraz;
    }

    public void setLevaETraz(boolean levaETraz) {
        this.levaETraz = levaETraz;
    }
}

