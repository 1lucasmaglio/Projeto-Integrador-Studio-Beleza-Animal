package service;

import java.util.ArrayList;
import java.util.List;

import dao.AdministradorCSV;
import model.Administrador;

public class AdministradorService {

    private List<Administrador> administradores;
    private AdministradorCSV dao;

    public AdministradorService() {
        administradores = new ArrayList<>();
        dao = new AdministradorCSV();
    }

    public void carregar() throws Exception {
        administradores = dao.listar();
    }

    public void salvar() throws Exception {
        dao.salvar(administradores);
    }

    public void adicionar(Administrador administrador) {

        int maiorId = 0;

        for (Administrador a : administradores) {
            if (a.getCodigo() > maiorId) {
                maiorId = a.getCodigo();
            }
        }

        administrador.setCodigo(maiorId + 1);
        administradores.add(administrador);
    }

    public List<Administrador> listar() {
        return administradores;
    }

    public Administrador buscarPorId(int codigo) {

        for (Administrador administrador : administradores) {
            if (administrador.getCodigo() == codigo) {
                return administrador;
            }
        }

        return null;
    }

    public Administrador autenticar(String email, String senha) {

        for (Administrador administrador : administradores) {

            if (administrador.getEmail().equalsIgnoreCase(email)
                    && administrador.getSenha().equals(senha)) {

                return administrador;
            }
        }

        return null;
    }
}