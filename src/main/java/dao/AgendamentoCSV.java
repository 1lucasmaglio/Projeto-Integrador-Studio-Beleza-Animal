package dao;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import model.Agendamento;

public class AgendamentoCSV {

    private Path caminho;

    public AgendamentoCSV() {
        caminho = Paths.get("dados/agendamentos.csv");
    }

    public void salvar(List<Agendamento> agendamentos) throws Exception {

        List<String> linhas = new ArrayList<>();

        linhas.add(
            "codigo;data;hora;status;codigoAnimal;codigoServico"
        );

        for (Agendamento agendamento : agendamentos) {

            String linha =
                    agendamento.getCodigo() + ";" +
                    agendamento.getData() + ";" +
                    agendamento.getHora() + ";" +
                    agendamento.getStatus() + ";" +
                    agendamento.getCodigoAnimal() + ";" +
                    agendamento.getCodigoServico();

            linhas.add(linha);
        }

        Files.createDirectories(caminho.getParent());
        Files.write(caminho, linhas);
    }

    public List<Agendamento> listar() throws Exception {

        List<Agendamento> agendamentos = new ArrayList<>();

        if (!Files.exists(caminho)) {
            return agendamentos;
        }

        List<String> linhas = Files.readAllLines(caminho);

        for (int i = 1; i < linhas.size(); i++) {

            String[] dados = linhas.get(i).split(";", -1);

            int codigo = Integer.parseInt(dados[0]);
            String data = dados[1];
            String hora = dados[2];
            String status = dados[3];
            int codigoAnimal = Integer.parseInt(dados[4]);
            int codigoServico = Integer.parseInt(dados[5]);

            agendamentos.add(
                new Agendamento(
                    codigo,
                    data,
                    hora,
                    status,
                    codigoAnimal,
                    codigoServico
                )
            );
        }

        return agendamentos;
    }
}