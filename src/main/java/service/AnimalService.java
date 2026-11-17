package service;

import java.util.ArrayList;
import java.util.List;

import dao.AnimalCSV;
import model.Animal;

public class AnimalService {

    private List<Animal> animais;
    private AnimalCSV dao;

    public AnimalService() {
        animais = new ArrayList<>();
        dao = new AnimalCSV();
    }

    public void carregar() throws Exception {
        animais = dao.listar();
    }

    public void salvar() throws Exception {
        dao.salvar(animais);
    }

    public void adicionar(Animal animal) {

        int maiorId = 0;

        for (Animal a : animais) {
            if (a.getCodigo() > maiorId) {
                maiorId = a.getCodigo();
            }
        }

        animal.setCodigo(maiorId + 1);
        animais.add(animal);
    }

    public List<Animal> listar() {
        return animais;
    }

    public boolean atualizar(int codigo, Animal dadosAtualizados) {

    Animal animal = buscarPorId(codigo);

    if (animal == null) {
        return false;
    }

    animal.setNome(dadosAtualizados.getNome());
    animal.setEspecie(dadosAtualizados.getEspecie());
    animal.setRaca(dadosAtualizados.getRaca());
    animal.setPorte(dadosAtualizados.getPorte());

    return true;

    
    }

    public boolean excluir(int codigo) {

        Animal animal = buscarPorId(codigo);

        if (animal == null) {
            return false;
        }

        animais.remove(animal);

        return true;
    }

    public Animal buscarPorId(int codigo) {

        for (Animal animal : animais) {
            if (animal.getCodigo() == codigo) {
                return animal;
            }
        }

        return null;
    }

    public List<Animal> listarPorCliente(int codigoCliente) {

        List<Animal> resultado = new ArrayList<>();

        for (Animal animal : animais) {
            if (animal.getCodigoCliente() == codigoCliente) {
                resultado.add(animal);
            }
        }

        return resultado;
    }

    public boolean validarAnimal(Animal animal) {

        if (animal.getNome() == null || animal.getNome().isBlank()) {
            return false;
        }

        boolean especieValida = "Cachorro".equals(animal.getEspecie()) ||
                                    "Gato".equals(animal.getEspecie());

        if (!especieValida) {
            return false;
        }

        String porte = animal.getPorte();

        boolean porteValido = "P".equals(porte) ||
                              "M".equals(porte) ||
                              "G".equals(porte);

            if (!porteValido) {
                return false;
            }

        return true;
    }
    
}