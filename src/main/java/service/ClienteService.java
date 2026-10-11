package service;

import java.util.ArrayList;
import java.util.List;

import dao.ClienteCSV;
import model.Cliente;

public class ClienteService {

    private List<Cliente> clientes;
    private ClienteCSV dao;

    public ClienteService() {
        clientes = new ArrayList<>();
        dao = new ClienteCSV();
    }

    public void carregar() throws Exception {
        clientes = dao.listar();
    }

    public void salvar() throws Exception {
        dao.salvar(clientes);
    }

    public void adicionar(Cliente cliente) {

        int maiorId = 0;

        for (Cliente c : clientes) {
            if (c.getCodigo() > maiorId) {
                maiorId = c.getCodigo();
            }
        }

        cliente.setCodigo(maiorId + 1);
        clientes.add(cliente);
    }

    public boolean validarCliente(Cliente cliente) {

    if (cliente == null) {
        return false;
    }

    if (cliente.getNome() == null || cliente.getNome().isBlank()) {
        return false;
    }

    if (cliente.getTelefone() == null || cliente.getTelefone().isBlank()) {
        return false;
    }

    if (cliente.getEmail() == null || cliente.getEmail().isBlank()) {
        return false;
    }

    if (cliente.getSenha() == null || cliente.getSenha().isBlank()) {
        return false;
    }

    return true;
    }

    public List<Cliente> listar() {
        return clientes;
    }

    public Cliente buscarPorId(int codigo) {

        for (Cliente cliente : clientes) {
            if (cliente.getCodigo() == codigo) {
                return cliente;
            }
        }

        return null;
    }

    public Cliente autenticar(String email, String senha) {

        for (Cliente cliente : clientes) {

            if (cliente.getEmail().equalsIgnoreCase(email)
                    && cliente.getSenha().equals(senha)) {

                return cliente;
            }
        }

        return null;
    }
}