package service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

        int quantidade = 0;

        for (Agendamento agendamento : agendamentos) {

            if (agendamento.getData().equals(data)
                    && agendamento.getHora().equals(hora)
                    && !agendamento.getStatus()
                        .equalsIgnoreCase("Cancelado")) {

                quantidade++;
            }
        }

        return quantidade < 2;
    }

    public boolean diaAberto(String data) {

    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
    LocalDate dataAgendamento = LocalDate.parse(data, formato);

    DayOfWeek diaSemana = dataAgendamento.getDayOfWeek();

    return diaSemana != DayOfWeek.SATURDAY
            && diaSemana != DayOfWeek.SUNDAY
            && diaSemana != DayOfWeek.MONDAY;
    }   

    public boolean horarioFuncionamento(String hora) {

        LocalTime horario = LocalTime.parse(hora);

        LocalTime abertura = LocalTime.of(7, 0);
        LocalTime fechamento = LocalTime.of(18, 0);

        return !horario.isBefore(abertura)
                && horario.isBefore(fechamento);
    }

    public boolean adicionar(Agendamento agendamento) {

        if (!diaAberto(agendamento.getData())) {
        return false;
        }

        if (!horarioFuncionamento(agendamento.getHora())) {
        return false;
        }

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