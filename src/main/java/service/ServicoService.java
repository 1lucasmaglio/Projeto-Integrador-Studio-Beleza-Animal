package service;

import java.util.ArrayList;
import java.util.List;

import dao.ServicoCSV;
import model.Servico;

public class ServicoService {

    private List<Servico> servicos;
    private ServicoCSV dao;

    public ServicoService() {
        servicos = new ArrayList<>();
        dao = new ServicoCSV();
    }

    public void carregar() throws Exception {
        servicos = dao.listar();
    }

    public void salvar() throws Exception {
        dao.salvar(servicos);
    }

    public void adicionar(Servico servico) {

        int maiorId = 0;

        for (Servico s : servicos) {
            if (s.getCodigo() > maiorId) {
                maiorId = s.getCodigo();
            }
        }

        servico.setCodigo(maiorId + 1);
        servicos.add(servico);
    }

    public List<Servico> listar() {
        return servicos;
    }

    public Servico buscarPorId(int codigo) {

        for (Servico servico : servicos) {
            if (servico.getCodigo() == codigo) {
                return servico;
            }
        }

        return null;
    }
}