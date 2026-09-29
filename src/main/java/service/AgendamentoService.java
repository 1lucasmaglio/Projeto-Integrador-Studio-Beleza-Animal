package service;

import java.util.ArrayList;
import java.util.List;

import dao.AgendamentoCSV;
import model.Agendamento;

public class AgendamentoService {

    private List<Agendamento> agendamentos;
    private AgendamentoCSV dao;

    public AgendamentoService() {
        agendamentos = new ArrayList<>();
        dao = new AgendamentoCSV();
    }

    public void carregar() throws Exception {
        agendamentos = dao.listar();
    }

    public void salvar() throws Exception {
        dao.salvar(agendamentos);
    }

    public boolean horarioDisponivel(String data, String hora) {

        for (Agendamento agendamento : agendamentos) {

            if (agendamento.getData().equals(data)
                    && agendamento.getHora().equals(hora)
                    && !agendamento.getStatus()
                        .equalsIgnoreCase("Cancelado")) {

                return false;
            }
        }

        return true;
    }

    public boolean adicionar(Agendamento agendamento) {

        if (!horarioDisponivel(
                agendamento.getData(),
                agendamento.getHora())) {

            return false;
        }

        int maiorId = 0;

        for (Agendamento a : agendamentos) {
            if (a.getCodigo() > maiorId) {
                maiorId = a.getCodigo();
            }
        }

        agendamento.setCodigo(maiorId + 1);
        agendamentos.add(agendamento);

        return true;
    }

    public List<Agendamento> listar() {
        return agendamentos;
    }

    public Agendamento buscarPorId(int codigo) {

        for (Agendamento agendamento : agendamentos) {
            if (agendamento.getCodigo() == codigo) {
                return agendamento;
            }
        }

        return null;
    }

    public boolean cancelar(int codigo) {

        Agendamento agendamento = buscarPorId(codigo);

        if (agendamento == null) {
            return false;
        }

        agendamento.setStatus("Cancelado");
        return true;
    }
}