package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Administrador;
import model.Agendamento;
import model.Animal;
import model.Cliente;
import model.Servico;
import service.AdministradorService;
import service.AgendamentoService;
import service.AnimalService;
import service.ClienteService;
import service.ServicoService;

public class TelaPrincipal extends JFrame {

    // =========================
    // SERVICES
    // =========================

    private ClienteService clienteService;
    private AnimalService animalService;
    private ServicoService servicoService;
    private AgendamentoService agendamentoService;
    private AdministradorService administradorService;

    // =========================
    // CLIENTES
    // =========================

    private JTextField txtNomeCliente;
    private JTextField txtTelefoneCliente;
    private JTextField txtEmailCliente;
    private JTextField txtSenhaCliente;

    private DefaultTableModel modeloClientes;
    private JTable tabelaClientes;

    // =========================
    // ANIMAIS
    // =========================

    private JTextField txtNomeAnimal;
    private JTextField txtEspecie;
    private JTextField txtRaca;
    private JTextField txtPorte;
    private JTextField txtCodigoClienteAnimal;

    private DefaultTableModel modeloAnimais;
    private JTable tabelaAnimais;

    // =========================
    // SERVICOS
    // =========================

    private JTextField txtNomeServico;
    private JTextField txtDescricaoServico;
    private JTextField txtValorServico;
    private JTextField txtDuracaoServico;

    private DefaultTableModel modeloServicos;
    private JTable tabelaServicos;

    // =========================
    // AGENDAMENTOS
    // =========================

    private JTextField txtDataAgendamento;
    private JTextField txtHoraAgendamento;
    private JTextField txtCodigoAnimalAgendamento;
    private JTextField txtCodigoServicoAgendamento;

    private JComboBox<String> cbStatusAgendamento;

    private DefaultTableModel modeloAgendamentos;
    private JTable tabelaAgendamentos;

    // =========================
    // ADMINISTRADORES
    // =========================

    private JTextField txtNomeAdministrador;
    private JTextField txtEmailAdministrador;
    private JTextField txtSenhaAdministrador;

    private DefaultTableModel modeloAdministradores;
    private JTable tabelaAdministradores;

    // =========================
    // CONSTRUTOR
    // =========================

    public TelaPrincipal() {

        clienteService = new ClienteService();
        animalService = new AnimalService();
        servicoService = new ServicoService();
        agendamentoService = new AgendamentoService();
        administradorService = new AdministradorService();

        carregarDados();

        setTitle("Studio de Beleza Animal");
        setSize(950, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane abas = new JTabbedPane();

        abas.addTab("Clientes", criarPainelClientes());
        abas.addTab("Animais", criarPainelAnimais());
        abas.addTab("Serviços", criarPainelServicos());
        abas.addTab("Agendamentos", criarPainelAgendamentos());
        abas.addTab("Administradores", criarPainelAdministradores());

        add(abas);

        atualizarTodasTabelas();
    }

    // =========================
    // CARREGAR DADOS
    // =========================

    private void carregarDados() {

        try {

            clienteService.carregar();
            animalService.carregar();
            servicoService.carregar();
            agendamentoService.carregar();
            administradorService.carregar();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                this,
                "Erro ao carregar os dados:\n" + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ============================================================
    // CLIENTES
    // ============================================================

    private JPanel criarPainelClientes() {

        JPanel painel = new JPanel(new BorderLayout(10, 10));

        painel.setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        JPanel formulario = new JPanel(new GridLayout(5, 2, 5, 5));

        txtNomeCliente = new JTextField();
        txtTelefoneCliente = new JTextField();
        txtEmailCliente = new JTextField();
        txtSenhaCliente = new JTextField();

        JButton btnCadastrar = new JButton("Cadastrar Cliente");

        formulario.add(new JLabel("Nome:"));
        formulario.add(txtNomeCliente);

        formulario.add(new JLabel("Telefone:"));
        formulario.add(txtTelefoneCliente);

        formulario.add(new JLabel("E-mail:"));
        formulario.add(txtEmailCliente);

        formulario.add(new JLabel("Senha:"));
        formulario.add(txtSenhaCliente);

        formulario.add(new JLabel(""));
        formulario.add(btnCadastrar);

        modeloClientes = new DefaultTableModel(
            new Object[] {
                "Código",
                "Nome",
                "Telefone",
                "E-mail"
            },
            0
        );

        tabelaClientes = new JTable(modeloClientes);

        painel.add(formulario, BorderLayout.NORTH);
        painel.add(
            new JScrollPane(tabelaClientes),
            BorderLayout.CENTER
        );

        btnCadastrar.addActionListener(e -> cadastrarCliente());

        return painel;
    }

    private void cadastrarCliente() {

        String nome = txtNomeCliente.getText().trim();
        String telefone = txtTelefoneCliente.getText().trim();
        String email = txtEmailCliente.getText().trim();
        String senha = txtSenhaCliente.getText().trim();

        if (nome.isEmpty()
                || telefone.isEmpty()
                || email.isEmpty()
                || senha.isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "Preencha todos os campos do cliente."
            );

            return;
        }

        Cliente cliente = new Cliente(
            nome,
            telefone,
            email,
            senha
        );

        clienteService.adicionar(cliente);

        try {

            clienteService.salvar();

            atualizarTabelaClientes();

            limparCamposCliente();

            JOptionPane.showMessageDialog(
                this,
                "Cliente cadastrado com sucesso."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                this,
                "Erro ao salvar cliente:\n" + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void atualizarTabelaClientes() {

        if (modeloClientes == null) {
            return;
        }

        modeloClientes.setRowCount(0);

        for (Cliente cliente : clienteService.listar()) {

            modeloClientes.addRow(
                new Object[] {
                    cliente.getCodigo(),
                    cliente.getNome(),
                    cliente.getTelefone(),
                    cliente.getEmail()
                }
            );
        }
    }

    private void limparCamposCliente() {

        txtNomeCliente.setText("");
        txtTelefoneCliente.setText("");
        txtEmailCliente.setText("");
        txtSenhaCliente.setText("");
    }

    // ============================================================
    // ANIMAIS
    // ============================================================

    private JPanel criarPainelAnimais() {

        JPanel painel = new JPanel(new BorderLayout(10, 10));

        painel.setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        JPanel formulario = new JPanel(new GridLayout(6, 2, 5, 5));

        txtNomeAnimal = new JTextField();
        txtEspecie = new JTextField();
        txtRaca = new JTextField();
        txtPorte = new JTextField();
        txtCodigoClienteAnimal = new JTextField();

        JButton btnCadastrar = new JButton("Cadastrar Animal");

        formulario.add(new JLabel("Nome:"));
        formulario.add(txtNomeAnimal);

        formulario.add(new JLabel("Espécie:"));
        formulario.add(txtEspecie);

        formulario.add(new JLabel("Raça:"));
        formulario.add(txtRaca);

        formulario.add(new JLabel("Porte:"));
        formulario.add(txtPorte);

        formulario.add(new JLabel("Código do Cliente:"));
        formulario.add(txtCodigoClienteAnimal);

        formulario.add(new JLabel(""));
        formulario.add(btnCadastrar);

        modeloAnimais = new DefaultTableModel(
            new Object[] {
                "Código",
                "Nome",
                "Espécie",
                "Raça",
                "Porte",
                "Cliente"
            },
            0
        );

        tabelaAnimais = new JTable(modeloAnimais);

        painel.add(formulario, BorderLayout.NORTH);

        painel.add(
            new JScrollPane(tabelaAnimais),
            BorderLayout.CENTER
        );

        btnCadastrar.addActionListener(e -> cadastrarAnimal());

        return painel;
    }

    private void cadastrarAnimal() {

        String nome = txtNomeAnimal.getText().trim();
        String especie = txtEspecie.getText().trim();
        String raca = txtRaca.getText().trim();
        String porte = txtPorte.getText().trim();

        if (nome.isEmpty()
                || especie.isEmpty()
                || raca.isEmpty()
                || porte.isEmpty()
                || txtCodigoClienteAnimal.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "Preencha todos os campos do animal."
            );

            return;
        }

        try {

            int codigoCliente = Integer.parseInt(
                txtCodigoClienteAnimal.getText().trim()
            );

            Cliente cliente =
                clienteService.buscarPorId(codigoCliente);

            if (cliente == null) {

                JOptionPane.showMessageDialog(
                    this,
                    "Cliente não encontrado."
                );

                return;
            }

            Animal animal = new Animal(
                nome,
                especie,
                raca,
                porte,
                codigoCliente
            );

            animalService.adicionar(animal);
            animalService.salvar();

            atualizarTabelaAnimais();

            limparCamposAnimal();

            JOptionPane.showMessageDialog(
                this,
                "Animal cadastrado com sucesso."
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                this,
                "O código do cliente deve ser um número."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                this,
                "Erro ao salvar animal:\n" + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void atualizarTabelaAnimais() {

        if (modeloAnimais == null) {
            return;
        }

        modeloAnimais.setRowCount(0);

        for (Animal animal : animalService.listar()) {

            modeloAnimais.addRow(
                new Object[] {
                    animal.getCodigo(),
                    animal.getNome(),
                    animal.getEspecie(),
                    animal.getRaca(),
                    animal.getPorte(),
                    animal.getCodigoCliente()
                }
            );
        }
    }

    private void limparCamposAnimal() {

        txtNomeAnimal.setText("");
        txtEspecie.setText("");
        txtRaca.setText("");
        txtPorte.setText("");
        txtCodigoClienteAnimal.setText("");
    }

    // ============================================================
    // SERVICOS
    // ============================================================

    private JPanel criarPainelServicos() {

        JPanel painel = new JPanel(new BorderLayout(10, 10));

        painel.setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        JPanel formulario = new JPanel(new GridLayout(5, 2, 5, 5));

        txtNomeServico = new JTextField();
        txtDescricaoServico = new JTextField();
        txtValorServico = new JTextField();
        txtDuracaoServico = new JTextField();

        JButton btnCadastrar = new JButton("Cadastrar Serviço");

        formulario.add(new JLabel("Nome:"));
        formulario.add(txtNomeServico);

        formulario.add(new JLabel("Descrição:"));
        formulario.add(txtDescricaoServico);

        formulario.add(new JLabel("Valor:"));
        formulario.add(txtValorServico);

        formulario.add(new JLabel("Duração (minutos):"));
        formulario.add(txtDuracaoServico);

        formulario.add(new JLabel(""));
        formulario.add(btnCadastrar);

        modeloServicos = new DefaultTableModel(
            new Object[] {
                "Código",
                "Nome",
                "Descrição",
                "Valor",
                "Duração"
            },
            0
        );

        tabelaServicos = new JTable(modeloServicos);

        painel.add(formulario, BorderLayout.NORTH);

        painel.add(
            new JScrollPane(tabelaServicos),
            BorderLayout.CENTER
        );

        btnCadastrar.addActionListener(e -> cadastrarServico());

        return painel;
    }

    private void cadastrarServico() {

        String nome = txtNomeServico.getText().trim();
        String descricao = txtDescricaoServico.getText().trim();

        if (nome.isEmpty()
                || txtValorServico.getText().trim().isEmpty()
                || txtDuracaoServico.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "Preencha os campos obrigatórios do serviço."
            );

            return;
        }

        try {

            String valorTexto =
                txtValorServico.getText().trim().replace(",", ".");

            double valor = Double.parseDouble(valorTexto);

            int duracao = Integer.parseInt(
                txtDuracaoServico.getText().trim()
            );

            Servico servico = new Servico(
                nome,
                descricao,
                valor,
                duracao
            );

            servicoService.adicionar(servico);
            servicoService.salvar();

            atualizarTabelaServicos();

            limparCamposServico();

            JOptionPane.showMessageDialog(
                this,
                "Serviço cadastrado com sucesso."
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                this,
                "Valor e duração precisam ser números."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                this,
                "Erro ao salvar serviço:\n" + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void atualizarTabelaServicos() {

        if (modeloServicos == null) {
            return;
        }

        modeloServicos.setRowCount(0);

        for (Servico servico : servicoService.listar()) {

            modeloServicos.addRow(
                new Object[] {
                    servico.getCodigo(),
                    servico.getNome(),
                    servico.getDescricao(),
                    servico.getValor(),
                    servico.getDuracao()
                }
            );
        }
    }

    private void limparCamposServico() {

        txtNomeServico.setText("");
        txtDescricaoServico.setText("");
        txtValorServico.setText("");
        txtDuracaoServico.setText("");
    }

    // ============================================================
    // AGENDAMENTOS
    // ============================================================

    private JPanel criarPainelAgendamentos() {

        JPanel painel = new JPanel(new BorderLayout(10, 10));

        painel.setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        JPanel formulario = new JPanel(new GridLayout(6, 2, 5, 5));

        txtDataAgendamento = new JTextField();
        txtHoraAgendamento = new JTextField();

        cbStatusAgendamento = new JComboBox<>(
            new String[] {
                "Confirmado",
                "Pendente"
            }
        );

        txtCodigoAnimalAgendamento = new JTextField();
        txtCodigoServicoAgendamento = new JTextField();

        JButton btnAgendar =
            new JButton("Cadastrar Agendamento");

        formulario.add(new JLabel("Data (dd/mm/aaaa):"));
        formulario.add(txtDataAgendamento);

        formulario.add(new JLabel("Hora (HH:mm):"));
        formulario.add(txtHoraAgendamento);

        formulario.add(new JLabel("Status:"));
        formulario.add(cbStatusAgendamento);

        formulario.add(new JLabel("Código do Animal:"));
        formulario.add(txtCodigoAnimalAgendamento);

        formulario.add(new JLabel("Código do Serviço:"));
        formulario.add(txtCodigoServicoAgendamento);

        formulario.add(new JLabel(""));
        formulario.add(btnAgendar);

        modeloAgendamentos = new DefaultTableModel(
            new Object[] {
                "Código",
                "Data",
                "Hora",
                "Status",
                "Animal",
                "Serviço"
            },
            0
        );

        tabelaAgendamentos =
            new JTable(modeloAgendamentos);

        JButton btnCancelar =
            new JButton("Cancelar Agendamento Selecionado");

        JPanel painelSul = new JPanel();

        painelSul.add(btnCancelar);

        painel.add(formulario, BorderLayout.NORTH);

        painel.add(
            new JScrollPane(tabelaAgendamentos),
            BorderLayout.CENTER
        );

        painel.add(painelSul, BorderLayout.SOUTH);

        btnAgendar.addActionListener(
            e -> cadastrarAgendamento()
        );

        btnCancelar.addActionListener(
            e -> cancelarAgendamento()
        );

        return painel;
    }

    private void cadastrarAgendamento() {

        String data =
            txtDataAgendamento.getText().trim();

        String hora =
            txtHoraAgendamento.getText().trim();

        String status =
            (String) cbStatusAgendamento.getSelectedItem();

        if (data.isEmpty()
                || hora.isEmpty()
                || txtCodigoAnimalAgendamento
                    .getText().trim().isEmpty()
                || txtCodigoServicoAgendamento
                    .getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "Preencha todos os campos do agendamento."
            );

            return;
        }

        try {

            int codigoAnimal = Integer.parseInt(
                txtCodigoAnimalAgendamento.getText().trim()
            );

            int codigoServico = Integer.parseInt(
                txtCodigoServicoAgendamento.getText().trim()
            );

            Animal animal =
                animalService.buscarPorId(codigoAnimal);

            if (animal == null) {

                JOptionPane.showMessageDialog(
                    this,
                    "Animal não encontrado."
                );

                return;
            }

            Servico servico =
                servicoService.buscarPorId(codigoServico);

            if (servico == null) {

                JOptionPane.showMessageDialog(
                    this,
                    "Serviço não encontrado."
                );

                return;
            }

            Agendamento agendamento = new Agendamento(
                data,
                hora,
                status,
                codigoAnimal,
                codigoServico
            );

            boolean adicionado =
                agendamentoService.adicionar(agendamento);

            if (!adicionado) {

                JOptionPane.showMessageDialog(
                    this,
                    "Este horário já possui um agendamento."
                );

                return;
            }

            agendamentoService.salvar();

            atualizarTabelaAgendamentos();

            limparCamposAgendamento();

            JOptionPane.showMessageDialog(
                this,
                "Agendamento cadastrado com sucesso."
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                this,
                "Os códigos do animal e do serviço devem ser números."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                this,
                "Erro ao salvar agendamento:\n" + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void cancelarAgendamento() {

        int linhaSelecionada =
            tabelaAgendamentos.getSelectedRow();

        if (linhaSelecionada == -1) {

            JOptionPane.showMessageDialog(
                this,
                "Selecione um agendamento na tabela."
            );

            return;
        }

        int codigo = (int) modeloAgendamentos.getValueAt(
            linhaSelecionada,
            0
        );

        boolean cancelado =
            agendamentoService.cancelar(codigo);

        if (!cancelado) {

            JOptionPane.showMessageDialog(
                this,
                "Agendamento não encontrado."
            );

            return;
        }

        try {

            agendamentoService.salvar();

            atualizarTabelaAgendamentos();

            JOptionPane.showMessageDialog(
                this,
                "Agendamento cancelado."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                this,
                "Erro ao salvar cancelamento:\n" + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void atualizarTabelaAgendamentos() {

        if (modeloAgendamentos == null) {
            return;
        }

        modeloAgendamentos.setRowCount(0);

        for (Agendamento agendamento :
                agendamentoService.listar()) {

            modeloAgendamentos.addRow(
                new Object[] {
                    agendamento.getCodigo(),
                    agendamento.getData(),
                    agendamento.getHora(),
                    agendamento.getStatus(),
                    agendamento.getCodigoAnimal(),
                    agendamento.getCodigoServico()
                }
            );
        }
    }

    private void limparCamposAgendamento() {

        txtDataAgendamento.setText("");
        txtHoraAgendamento.setText("");

        cbStatusAgendamento.setSelectedIndex(0);

        txtCodigoAnimalAgendamento.setText("");
        txtCodigoServicoAgendamento.setText("");
    }

    // ============================================================
    // ADMINISTRADORES
    // ============================================================

    private JPanel criarPainelAdministradores() {

        JPanel painel = new JPanel(new BorderLayout(10, 10));

        painel.setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        JPanel formulario = new JPanel(new GridLayout(4, 2, 5, 5));

        txtNomeAdministrador = new JTextField();
        txtEmailAdministrador = new JTextField();
        txtSenhaAdministrador = new JTextField();

        JButton btnCadastrar = new JButton("Cadastrar Administrador");

        formulario.add(new JLabel("Nome:"));
        formulario.add(txtNomeAdministrador);

        formulario.add(new JLabel("E-mail:"));
        formulario.add(txtEmailAdministrador);

        formulario.add(new JLabel("Senha:"));
        formulario.add(txtSenhaAdministrador);

        formulario.add(new JLabel(""));
        formulario.add(btnCadastrar);

        modeloAdministradores = new DefaultTableModel(
            new Object[] {
                "Código",
                "Nome",
                "E-mail"
            },
            0
        );

        tabelaAdministradores = new JTable(modeloAdministradores);

        painel.add(formulario, BorderLayout.NORTH);
        painel.add(
            new JScrollPane(tabelaAdministradores),
            BorderLayout.CENTER
        );

        btnCadastrar.addActionListener(e -> cadastrarAdministrador());

        return painel;
    }

    private void cadastrarAdministrador() {

        String nome = txtNomeAdministrador.getText().trim();
        String email = txtEmailAdministrador.getText().trim();
        String senha = txtSenhaAdministrador.getText().trim();

        if (nome.isEmpty()
                || email.isEmpty()
                || senha.isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "Preencha todos os campos do administrador."
            );

            return;
        }

        Administrador administrador = new Administrador(
            nome,
            email,
            senha
        );

        administradorService.adicionar(administrador);

        try {

            administradorService.salvar();

            atualizarTabelaAdministradores();

            limparCamposAdministrador();

            JOptionPane.showMessageDialog(
                this,
                "Administrador cadastrado com sucesso."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                this,
                "Erro ao salvar administrador:\n" + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void atualizarTabelaAdministradores() {

        if (modeloAdministradores == null) {
            return;
        }

        modeloAdministradores.setRowCount(0);

        for (Administrador administrador :
                administradorService.listar()) {

            modeloAdministradores.addRow(
                new Object[] {
                    administrador.getCodigo(),
                    administrador.getNome(),
                    administrador.getEmail()
                }
            );
        }
    }

    private void limparCamposAdministrador() {

        txtNomeAdministrador.setText("");
        txtEmailAdministrador.setText("");
        txtSenhaAdministrador.setText("");
    }

    // ============================================================
    // ATUALIZAR TODAS AS TABELAS
    // ============================================================

    private void atualizarTodasTabelas() {

        atualizarTabelaClientes();
        atualizarTabelaAnimais();
        atualizarTabelaServicos();
        atualizarTabelaAgendamentos();
        atualizarTabelaAdministradores();
    }
}