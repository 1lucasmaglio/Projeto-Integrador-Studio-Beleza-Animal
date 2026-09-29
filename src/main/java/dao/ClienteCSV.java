package dao;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;

public class ClienteCSV {

    private Path caminho;

    public ClienteCSV() {
        caminho = Paths.get("dados/clientes.csv");
    }

    public void salvar(List<Cliente> clientes) throws Exception {

        List<String> linhas = new ArrayList<>();

        linhas.add("codigo;nome;telefone;email;senha");

        for (Cliente cliente : clientes) {
            String linha =
                    cliente.getCodigo() + ";" +
                    cliente.getNome() + ";" +
                    cliente.getTelefone() + ";" +
                    cliente.getEmail() + ";" +
                    cliente.getSenha();

            linhas.add(linha);
        }

        Files.createDirectories(caminho.getParent());
        Files.write(caminho, linhas);
    }

    public List<Cliente> listar() throws Exception {

        List<Cliente> clientes = new ArrayList<>();

        if (!Files.exists(caminho)) {
            return clientes;
        }

        List<String> linhas = Files.readAllLines(caminho);

        for (int i = 1; i < linhas.size(); i++) {

            String[] dados = linhas.get(i).split(";", -1);

            int codigo = Integer.parseInt(dados[0]);
            String nome = dados[1];
            String telefone = dados[2];
            String email = dados[3];
            String senha = dados[4];

            clientes.add(
                new Cliente(codigo, nome, telefone, email, senha)
            );
        }

        return clientes;
    }
}