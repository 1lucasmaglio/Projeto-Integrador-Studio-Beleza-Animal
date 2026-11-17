package service;

import java.util.List;

import dao.ClienteDAO;
import model.Cliente;

/**
 * Versão com MySQL. Não existe mais lista em memória, nem carregar() e salvar():
 * o banco é a única fonte de verdade.
 */
public class ClienteService {

    private final ClienteDAO dao = new ClienteDAO();

    public void adicionar(Cliente cliente) throws Exception {
        // O código é gerado pelo AUTO_INCREMENT e já volta preenchido no objeto.
        dao.inserir(cliente);
    }

    public boolean emailJaCadastrado(String email) throws Exception {
        return dao.buscarPorEmail(email) != null;
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

    public List<Cliente> listar() throws Exception {
        return dao.listar();
    }

    public Cliente buscarPorId(int codigo) throws Exception {
        return dao.buscarPorId(codigo);
    }

    public Cliente autenticar(String email, String senha) throws Exception {

        Cliente cliente = dao.buscarPorEmail(email);

        if (cliente != null && cliente.getSenha().equals(senha)) {
            return cliente;
        }

        return null;
    }
}