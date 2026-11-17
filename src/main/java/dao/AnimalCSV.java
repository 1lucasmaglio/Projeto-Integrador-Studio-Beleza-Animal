package dao;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import model.Animal;

public class AnimalCSV {

    private Path caminho;

    public AnimalCSV() {
        caminho = Paths.get("dados/animais.csv");
    }

    public void salvar(List<Animal> animais) throws Exception {

        List<String> linhas = new ArrayList<>();

        linhas.add("codigo;nome;especie;raca;porte;codigoCliente");

        for (Animal animal : animais) {

            String linha =
                    animal.getCodigo() + ";" +
                    animal.getNome() + ";" +
                    animal.getEspecie() + ";" +
                    animal.getRaca() + ";" +
                    animal.getPorte() + ";" +
                    animal.getCodigoCliente();

            linhas.add(linha);
        }

        Files.createDirectories(caminho.getParent());
        Files.write(caminho, linhas);
    }

    public List<Animal> listar() throws Exception {

        List<Animal> animais = new ArrayList<>();

        if (!Files.exists(caminho)) {
            return animais;
        }

        List<String> linhas = Files.readAllLines(caminho);

        for (int i = 1; i < linhas.size(); i++) {

            String[] dados = linhas.get(i).split(";", -1);

            int codigo = Integer.parseInt(dados[0]);
            String nome = dados[1];
            String especie = dados[2];
            String raca = dados[3];
            String porte = dados[4];
            int codigoCliente = Integer.parseInt(dados[5]);

            animais.add(
                new Animal(
                    codigo,
                    nome,
                    especie,
                    raca,
                    porte,
                    codigoCliente
                )
            );
        }

        return animais;
    }
}