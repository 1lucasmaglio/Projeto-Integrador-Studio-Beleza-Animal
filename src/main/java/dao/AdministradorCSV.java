package dao;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import model.Administrador;

public class AdministradorCSV {

    private Path caminho;

    public AdministradorCSV() {
        caminho = Paths.get("dados/administradores.csv");
    }

    public void salvar(List<Administrador> administradores)
            throws Exception {

        List<String> linhas = new ArrayList<>();

        linhas.add("codigo;nome;email;senha");

        for (Administrador administrador : administradores) {

            String linha =
                    administrador.getCodigo() + ";" +
                    administrador.getNome() + ";" +
                    administrador.getEmail() + ";" +
                    administrador.getSenha();

            linhas.add(linha);
        }

        Files.createDirectories(caminho.getParent());
        Files.write(caminho, linhas);
    }

    public List<Administrador> listar() throws Exception {

        List<Administrador> administradores = new ArrayList<>();

        if (!Files.exists(caminho)) {
            return administradores;
        }

        List<String> linhas = Files.readAllLines(caminho);

        for (int i = 1; i < linhas.size(); i++) {

            String[] dados = linhas.get(i).split(";", -1);

            int codigo = Integer.parseInt(dados[0]);
            String nome = dados[1];
            String email = dados[2];
            String senha = dados[3];

            administradores.add(
                new Administrador(codigo, nome, email, senha)
            );
        }

        return administradores;
    }
}