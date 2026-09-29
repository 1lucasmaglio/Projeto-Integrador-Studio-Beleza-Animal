package dao;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import model.Servico;

public class ServicoCSV {

    private Path caminho;

    public ServicoCSV() {
        caminho = Paths.get("dados/servicos.csv");
    }

    public void salvar(List<Servico> servicos) throws Exception {

        List<String> linhas = new ArrayList<>();

        linhas.add("codigo;nome;descricao;valor;duracao");

        for (Servico servico : servicos) {

            String linha =
                    servico.getCodigo() + ";" +
                    servico.getNome() + ";" +
                    servico.getDescricao() + ";" +
                    servico.getValor() + ";" +
                    servico.getDuracao();

            linhas.add(linha);
        }

        Files.createDirectories(caminho.getParent());
        Files.write(caminho, linhas);
    }

    public List<Servico> listar() throws Exception {

        List<Servico> servicos = new ArrayList<>();

        if (!Files.exists(caminho)) {
            return servicos;
        }

        List<String> linhas = Files.readAllLines(caminho);

        for (int i = 1; i < linhas.size(); i++) {

            String[] dados = linhas.get(i).split(";", -1);

            int codigo = Integer.parseInt(dados[0]);
            String nome = dados[1];
            String descricao = dados[2];
            double valor = Double.parseDouble(dados[3]);
            int duracao = Integer.parseInt(dados[4]);

            servicos.add(
                new Servico(
                    codigo,
                    nome,
                    descricao,
                    valor,
                    duracao
                )
            );
        }

        return servicos;
    }
}