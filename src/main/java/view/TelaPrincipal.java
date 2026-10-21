
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

// JFrame é a janela principal da aplicação Swing.
// Esta classe reúne as telas administrativas do sistema.
public class TelaPrincipal extends JFrame {

    // =========================
    // SERVICES
    // =========================

    // Objetos responsáveis por executar as operações de cada cadastro.
    private ClienteService clienteService;
    private AnimalService animalService;
    private ServicoService servicoService;
    private AgendamentoService agendamentoService;
    private AdministradorService administradorService;

    // =========================
    // CLIENTES
    // =========================

    // Campos utilizados no formulário de clientes.
    private JTextField txtNomeCliente;
    private JTextField txtTelefoneCliente;
    private JTextField txtEmailCliente;
    private JTextField txtSenhaCliente;

    // Modelo responsável pelos dados e tabela que os apresenta.
    private DefaultTableModel modeloClientes;
    private JTable tabelaClientes;

    // =========================
    // ANIMAIS
    // =========================

    // Campos utilizados no formulário de animais.
    private JTextField txtNomeAnimal;
    private JTextField txtEspecie;
    private JTextField txtRaca;
    private JTextField txtPorte;
    private JTextField txtCodigoClienteAnimal;

    // Modelo e tabela dos animais cadastrados.
    private DefaultTableModel modeloAnimais;
    private JTable tabelaAnimais;

    // =========================
    // SERVICOS
    // =========================

    // Campos para cadastrar serviços, preços por porte e duração.
    private JTextField txtNomeServico;
    private JTextField txtDescricaoServico;
    private JTextField txtValorPServico;
    private JTextField txtValorMServico;
    private JTextField txtValorGServico;
    private JTextField txtDuracaoServico;

    // Modelo e tabela dos serviços cadastrados.
    private DefaultTableModel modeloServicos;
    private JTable tabelaServicos;

    // =========================
    // AGENDAMENTOS
    // =========================

    // Campos utilizados no cadastro de agendamentos.
    private JTextField txtDataAgendamento;
    private JTextField txtHoraAgendamento;
    private JTextField txtCodigoAnimalAgendamento;
    private JTextField txtCodigoServicoAgendamento;

    // Caixa de seleção para escolher o status do agendamento.
    private JComboBox<String> cbStatusAgendamento;

    // Modelo e tabela dos agendamentos cadastrados.
    private DefaultTableModel modeloAgendamentos;
    private JTable tabelaAgendamentos;

    // =========================
    // ADMINISTRADORES
    // =========================

    // Campos utilizados no formulário de administradores.
    private JTextField txtNomeAdministrador;
    private JTextField txtEmailAdministrador;
    private JTextField txtSenhaAdministrador;

    // Modelo e tabela dos administradores cadastrados.
    private DefaultTableModel modeloAdministradores;
    private JTable tabelaAdministradores;

    // =========================
    // CONSTRUTOR
    // =========================

    // Executado quando um objeto TelaPrincipal é criado.
    // Prepara os Services, a janela, as abas e as tabelas.
    public TelaPrincipal() {

        // Inicializa os Services utilizados pela interface.
        clienteService = new ClienteService();
        animalService = new AnimalService();
        servicoService = new ServicoService();
        agendamentoService = new AgendamentoService();
        administradorService = new AdministradorService();

        // Recupera os dados já armazenados nos arquivos CSV.
        carregarDados();

        // Define o título, tamanho, posição e comportamento da janela.
        setTitle("Studio de Beleza Animal");
        setSize(950, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // JTabbedPane permite organizar diferentes telas em abas.
        JTabbedPane abas = new JTabbedPane();

        // Cada método cria o painel correspondente a uma aba.
        abas.addTab("Clientes", criarPainelClientes());
        abas.addTab("Animais", criarPainelAnimais());
        abas.addTab("Serviços", criarPainelServicos());
        abas.addTab("Agendamentos", criarPainelAgendamentos());
        abas.addTab("Administradores", criarPainelAdministradores());

        // Adiciona o conjunto de abas à janela principal.
        add(abas);

        // Preenche as tabelas com os dados carregados.
        atualizarTodasTabelas();
    }

    // =========================
    // CARREGAR DADOS
    // =========================

    // Recupera os cadastros salvos anteriormente.
    private void carregarDados() {

        try {

            // Cada Service carrega os dados de sua responsabilidade.
            clienteService.carregar();
            animalService.carregar();
            servicoService.carregar();
            agendamentoService.carregar();
            administradorService.carregar();

        } catch (Exception e) {

            // Exibe uma janela de erro caso algum carregamento falhe.
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

    // Monta o painel de cadastro e consulta de clientes.
    private JPanel criarPainelClientes() {

        // BorderLayout organiza o formulário e a tabela em regiões.
        JPanel painel = new JPanel(new BorderLayout(10, 10));

        // Adiciona espaçamento interno ao painel.
        painel.setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        // GridLayout organiza os campos em cinco linhas e duas colunas.
        JPanel formulario = new JPanel(new GridLayout(5, 2, 5, 5));

        // Cria os campos nos quais o usuário digita os dados.
        txtNomeCliente = new JTextField();
        txtTelefoneCliente = new JTextField();
        txtEmailCliente = new JTextField();
        txtSenhaCliente = new JTextField();

        JButton btnCadastrar = new JButton("Cadastrar Cliente");

        // Adiciona os rótulos e campos ao formulário.
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

        // Define as colunas da tabela de clientes.
        modeloClientes = new DefaultTableModel(
            new Object[] {
                "Código",
                "Nome",
                "Telefone",
                "E-mail"
            },
            0
        );

        // Cria a tabela utilizando o modelo definido.
        tabelaClientes = new JTable(modeloClientes);

        // Coloca o formulário na parte superior e a tabela no centro.
        painel.add(formulario, BorderLayout.NORTH);
        painel.add(
            new JScrollPane(tabelaClientes),
            BorderLayout.CENTER
        );

        // Quando o botão é clicado, executa o método cadastrarCliente().
        btnCadastrar.addActionListener(e -> cadastrarCliente());

        return painel;
    }

    // Lê os campos, valida e cadastra um novo cliente.
    private void cadastrarCliente() {

        // getText() recupera o texto e trim() remove espaços nas extremidades.
        String nome = txtNomeCliente.getText().trim();
        String telefone = txtTelefoneCliente.getText().trim();
        String email = txtEmailCliente.getText().trim();
        String senha = txtSenhaCliente.getText().trim();

        // Impede o cadastro quando algum campo obrigatório está vazio.
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

        // Cria um objeto Cliente com os dados informados.
        Cliente cliente = new Cliente(
            nome,
            telefone,
            email,
            senha
        );

        // Envia o objeto para o Service responsável pelo cadastro.
        clienteService.adicionar(cliente);

        try {

            // Salva os dados e atualiza a interface.
            clienteService.salvar();

            atualizarTabelaClientes();

            limparCamposCliente();

            JOptionPane.showMessageDialog(
                this,
                "Cliente cadastrado com sucesso."
            );

        } catch (Exception e) {

            // Informa caso ocorra um erro durante o salvamento.
            JOptionPane.showMessageDialog(
                this,
                "Erro ao salvar cliente:\n" + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Atualiza a tabela para mostrar os clientes cadastrados.
    private void atualizarTabelaClientes() {

        // Evita utilizar o modelo caso ainda não tenha sido criado.
        if (modeloClientes == null) {
            return;
        }

        // Remove as linhas antigas para evitar registros duplicados na tela.
        modeloClientes.setRowCount(0);

        // Percorre os clientes e adiciona uma linha para cada um.
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

    // Limpa os campos após o cadastro.
    private void limparCamposCliente() {

        txtNomeCliente.setText("");
        txtTelefoneCliente.setText("");
        txtEmailCliente.setText("");
        txtSenhaCliente.setText("");
    }

    // ============================================================
    // ANIMAIS
    // ============================================================

    // Monta o painel de cadastro e consulta de animais.
    private JPanel criarPainelAnimais() {

        JPanel painel = new JPanel(new BorderLayout(10, 10));

        painel.setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        // Organiza os campos do formulário em seis linhas e duas colunas.
        JPanel formulario = new JPanel(new GridLayout(6, 2, 5, 5));

        // Cria os campos para os dados do animal e o código do cliente.
        txtNomeAnimal = new JTextField();
        txtEspecie = new JTextField();
        txtRaca = new JTextField();
        txtPorte = new JTextField();
        txtCodigoClienteAnimal = new JTextField();

        JButton btnCadastrar = new JButton("Cadastrar Animal");

        // Adiciona os campos e seus respectivos rótulos.
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

        // Define as colunas da tabela de animais.
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

        // Organiza o formulário e a tabela dentro do painel.
        painel.add(formulario, BorderLayout.NORTH);

        painel.add(
            new JScrollPane(tabelaAnimais),
            BorderLayout.CENTER
        );

        // Associa o botão ao método responsável pelo cadastro.
        btnCadastrar.addActionListener(e -> cadastrarAnimal());

        return painel;
    }

    // Cadastra um animal e associa seu registro a um cliente existente.
    private void cadastrarAnimal() {

        // Recupera as informações preenchidas.
        String nome = txtNomeAnimal.getText().trim();
        String especie = txtEspecie.getText().trim();
        String raca = txtRaca.getText().trim();
        String porte = txtPorte.getText().trim();

        // Verifica se todos os campos foram preenchidos.
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

            // Converte o código do cliente de texto para número inteiro.
            int codigoCliente = Integer.parseInt(
                txtCodigoClienteAnimal.getText().trim()
            );

            // Procura o cliente informado.
            Cliente cliente =
                    clienteService.buscarPorId(codigoCliente);

            // Impede associar o animal a um cliente inexistente.
            if (cliente == null) {

                JOptionPane.showMessageDialog(
                    this,
                    "Cliente não encontrado."
                );

                return;
            }

            // Cria o animal com os dados informados e o código do cliente.
            Animal animal = new Animal(
                nome,
                especie,
                raca,
                porte,
                codigoCliente
            );

            // Cadastra o animal e salva os dados.
            animalService.adicionar(animal);
            animalService.salvar();

            // Atualiza a tabela e limpa o formulário.
            atualizarTabelaAnimais();

            limparCamposAnimal();

            JOptionPane.showMessageDialog(
                this,
                "Animal cadastrado com sucesso."
            );

        } catch (NumberFormatException e) {

            // Trata o erro quando o código do cliente não é um número.
            JOptionPane.showMessageDialog(
                this,
                "O código do cliente deve ser um número."
            );

        } catch (Exception e) {

            // Trata outros erros que possam ocorrer durante o cadastro.
            JOptionPane.showMessageDialog(
                this,
                "Erro ao salvar animal:\n" + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Atualiza a tabela com os animais cadastrados.
    private void atualizarTabelaAnimais() {

        if (modeloAnimais == null) {
            return;
        }

        // Limpa as linhas anteriores antes de carregar os registros.
        modeloAnimais.setRowCount(0);

        // Percorre a lista de animais e adiciona os dados na tabela.
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

    // Limpa os campos do formulário de animais.
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

    // Monta o painel de cadastro e consulta dos serviços oferecidos.
    private JPanel criarPainelServicos() {

        JPanel painel = new JPanel(new BorderLayout(10, 10));

        painel.setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        // Organiza o formulário em sete linhas e duas colunas.
        JPanel formulario = new JPanel(new GridLayout(7, 2, 5, 5));

        // Cria os campos de nome, descrição, valores por porte e duração.
        txtNomeServico = new JTextField();
        txtDescricaoServico = new JTextField();
        txtValorPServico = new JTextField();
        txtValorMServico = new JTextField();
        txtValorGServico = new JTextField();
        txtDuracaoServico = new JTextField();

        JButton btnCadastrar = new JButton("Cadastrar Serviço");

        formulario.add(new JLabel("Nome:"));
        formulario.add(txtNomeServico);

        formulario.add(new JLabel("Descrição:"));
        formulario.add(txtDescricaoServico);

        formulario.add(new JLabel("Valor P (R$):"));
        formulario.add(txtValorPServico);

        formulario.add(new JLabel("Valor M (R$):"));
        formulario.add(txtValorMServico);

        formulario.add(new JLabel("Valor G (R$):"));
        formulario.add(txtValorGServico);

        formulario.add(new JLabel("Duração (minutos):"));
        formulario.add(txtDuracaoServico);

        formulario.add(new JLabel(""));
        formulario.add(btnCadastrar);

        // Define as colunas utilizadas para apresentar os serviços.
        modeloServicos = new DefaultTableModel(
            new Object[] {
                "Código",
                "Nome",
                "Descrição",
                "Valor P",
                "Valor M",
                "Valor G",
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

        // Executa o cadastro quando o botão é pressionado.
        btnCadastrar.addActionListener(e -> cadastrarServico());

        return painel;
    }

    // Valida os dados e cadastra um novo serviço.
    private void cadastrarServico() {

        String nome = txtNomeServico.getText().trim();
        String descricao = txtDescricaoServico.getText().trim();

        // Verifica se os campos obrigatórios foram preenchidos.
        if (nome.isEmpty()
                || txtValorPServico.getText().trim().isEmpty()
                || txtValorMServico.getText().trim().isEmpty()
                || txtValorGServico.getText().trim().isEmpty()
                || txtDuracaoServico.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "Preencha os campos obrigatórios do serviço."
            );

            return;
        }

        try {

            // Converte os valores digitados para double.
            // replace() permite aceitar vírgula como separador decimal.
            double valorP = Double.parseDouble(
                txtValorPServico.getText().trim().replace(",", ".")
            );

            double valorM = Double.parseDouble(
                txtValorMServico.getText().trim().replace(",", ".")
            );

            double valorG = Double.parseDouble(
                txtValorGServico.getText().trim().replace(",", ".")
            );

            // Converte a duração do serviço para número inteiro.
            int duracao = Integer.parseInt(
                txtDuracaoServico.getText().trim()
            );

            // Impede valores negativos e duração menor ou igual a zero.
            if (valorP < 0 || valorM < 0 || valorG < 0 || duracao <= 0) {
                JOptionPane.showMessageDialog(
                    this,
                    "Os valores não podem ser negativos e a duração deve ser maior que zero."
                );
                return;
            }

            // Cria o objeto Serviço com os preços para cada porte.
            Servico servico = new Servico(
                nome,
                descricao,
                valorP,
                valorM,
                valorG,
                duracao
            );

            // Adiciona e salva o serviço cadastrado.
            servicoService.adicionar(servico);
            servicoService.salvar();

            // Atualiza a tabela e limpa os campos.
            atualizarTabelaServicos();
            limparCamposServico();

            JOptionPane.showMessageDialog(
                this,
                "Serviço cadastrado com sucesso."
            );

        } catch (NumberFormatException e) {

            // Informa quando um valor ou a duração não é um número válido.
            JOptionPane.showMessageDialog(
                this,
                "Valores e duração precisam ser números."
            );

        } catch (Exception e) {

            // Trata erros durante o salvamento.
            JOptionPane.showMessageDialog(
                this,
                "Erro ao salvar serviço:\n" + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Atualiza a tabela com os serviços cadastrados.
    private void atualizarTabelaServicos() {

        if (modeloServicos == null) {
            return;
        }

        // Remove as linhas antigas antes de adicionar os dados atuais.
        modeloServicos.setRowCount(0);

        for (Servico servico : servicoService.listar()) {

            modeloServicos.addRow(
                new Object[] {
                    servico.getCodigo(),
                    servico.getNome(),
                    servico.getDescricao(),
                    servico.getValorP(),
                    servico.getValorM(),
                    servico.getValorG(),
                    servico.getDuracao()
                }
            );
        }
    }

    // Limpa o formulário após cadastrar um serviço.
    private void limparCamposServico() {

        txtNomeServico.setText("");
        txtDescricaoServico.setText("");
        txtValorPServico.setText("");
        txtValorMServico.setText("");
        txtValorGServico.setText("");
        txtDuracaoServico.setText("");
    }

    // ============================================================
    // AGENDAMENTOS
    // ============================================================

    // Monta o painel para cadastrar, visualizar e cancelar agendamentos.
    private JPanel criarPainelAgendamentos() {

        JPanel painel = new JPanel(new BorderLayout(10, 10));

        painel.setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        JPanel formulario = new JPanel(new GridLayout(6, 2, 5, 5));

        // Cria os campos de data e horário.
        txtDataAgendamento = new JTextField();
        txtHoraAgendamento = new JTextField();

        // Cria a caixa de seleção com os status disponíveis.
        cbStatusAgendamento = new JComboBox<>(
            new String[] {
                "Confirmado",
                "Pendente"
            }
        );

        // Campos para identificar o animal e o serviço desejados.
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

        // Define as colunas da tabela de agendamentos.
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

        // Cria o botão utilizado para cancelar um agendamento selecionado.
        JButton btnCancelar =
            new JButton("Cancelar Agendamento Selecionado");

        // Painel inferior que contém o botão de cancelamento.
        JPanel painelSul = new JPanel();

        painelSul.add(btnCancelar);

        // Organiza formulário, tabela e botão dentro do painel principal.
        painel.add(formulario, BorderLayout.NORTH);

        painel.add(
            new JScrollPane(tabelaAgendamentos),
            BorderLayout.CENTER
        );

        painel.add(painelSul, BorderLayout.SOUTH);

        // Associa cada botão ao método responsável pela operação.
        btnAgendar.addActionListener(
            e -> cadastrarAgendamento()
        );

        btnCancelar.addActionListener(
            e -> cancelarAgendamento()
        );

        return painel;
    }

    // Cadastra um agendamento utilizando os dados informados.
    private void cadastrarAgendamento() {

        // Recupera a data, a hora e o status escolhido.
        String data =
            txtDataAgendamento.getText().trim();

        String hora =
            txtHoraAgendamento.getText().trim();

        String status =
            (String) cbStatusAgendamento.getSelectedItem();

        // Verifica se os campos obrigatórios foram preenchidos.
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

            // Converte os códigos informados para números inteiros.
            int codigoAnimal = Integer.parseInt(
                txtCodigoAnimalAgendamento.getText().trim()
            );

            int codigoServico = Integer.parseInt(
                txtCodigoServicoAgendamento.getText().trim()
            );

            // Busca o animal correspondente ao código informado.
            Animal animal =
                    animalService.buscarPorId(codigoAnimal);

            // Impede agendar para um animal inexistente.
            if (animal == null) {

                JOptionPane.showMessageDialog(
                    this,
                    "Animal não encontrado."
                );

                return;
            }

            // Busca o serviço correspondente ao código informado.
            Servico servico =
                    servicoService.buscarPorId(codigoServico);

            // Impede agendar um serviço inexistente.
            if (servico == null) {

                JOptionPane.showMessageDialog(
                    this,
                    "Serviço não encontrado."
                );

                return;
            }

            // Cria o objeto Agendamento com os dados preenchidos.
            
            boolean levaETraz = false;
            Agendamento agendamento = new Agendamento(
                data,
                hora,
                status,
                codigoAnimal,
                codigoServico,
                levaETraz
            );

            // Solicita ao Service a inclusão do agendamento.
            // O retorno boolean indica se a operação foi aceita.
            boolean adicionado =
                agendamentoService.adicionar(agendamento);

            // Se a inclusão for recusada, mostra uma mensagem ao usuário.
            if (!adicionado) {

                JOptionPane.showMessageDialog(
                    this,
                    "Este horário já possui um agendamento."
                );

                return;
            }

            // Salva o agendamento e atualiza a tabela.
            agendamentoService.salvar();

            atualizarTabelaAgendamentos();

            // Limpa os campos para um próximo cadastro.
            limparCamposAgendamento();

            JOptionPane.showMessageDialog(
                this,
                "Agendamento cadastrado com sucesso."
            );

        } catch (NumberFormatException e) {

            // Trata códigos de animal ou serviço que não sejam números.
            JOptionPane.showMessageDialog(
                this,
                "Os códigos do animal e do serviço devem ser números."
            );

        } catch (Exception e) {

            // Trata outros erros ocorridos durante o cadastro.
            JOptionPane.showMessageDialog(
                this,
                "Erro ao salvar agendamento:\n" + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Cancela o agendamento selecionado na tabela.
    private void cancelarAgendamento() {

        // Recupera o número da linha selecionada pelo usuário.
        int linhaSelecionada =
            tabelaAgendamentos.getSelectedRow();

        // O valor -1 indica que nenhuma linha foi selecionada.
        if (linhaSelecionada == -1) {

            JOptionPane.showMessageDialog(
                this,
                "Selecione um agendamento na tabela."
            );

            return;
        }

        // Recupera o código do agendamento na primeira coluna da linha.
        int codigo = (int) modeloAgendamentos.getValueAt(
            linhaSelecionada,
            0
        );

        // Solicita ao Service o cancelamento do agendamento.
        boolean cancelado =
            agendamentoService.cancelar(codigo);

        // Verifica se o cancelamento foi realizado.
        if (!cancelado) {

            JOptionPane.showMessageDialog(
                this,
                "Agendamento não encontrado."
            );

            return;
        }

        try {

            // Salva a alteração e atualiza a tabela.
            agendamentoService.salvar();

            atualizarTabelaAgendamentos();

            JOptionPane.showMessageDialog(
                this,
                "Agendamento cancelado."
            );

        } catch (Exception e) {

            // Informa caso ocorra um erro ao salvar o cancelamento.
            JOptionPane.showMessageDialog(
                this,
                "Erro ao salvar cancelamento:\n" + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Atualiza a tabela com os agendamentos registrados.
    private void atualizarTabelaAgendamentos() {

        if (modeloAgendamentos == null) {
            return;
        }

        // Limpa a tabela antes de inserir os dados atuais.
        modeloAgendamentos.setRowCount(0);

        // Percorre os agendamentos e adiciona uma linha para cada registro.
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

    // Limpa os campos e retorna o status à primeira opção.
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

    // Monta o painel para cadastrar e visualizar administradores.
    private JPanel criarPainelAdministradores() {

        JPanel painel = new JPanel(new BorderLayout(10, 10));

        painel.setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        JPanel formulario = new JPanel(new GridLayout(4, 2, 5, 5));

        // Cria os campos do formulário de administradores.
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

        // Define as colunas da tabela de administradores.
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

        // Executa o cadastro quando o botão é clicado.
        btnCadastrar.addActionListener(e -> cadastrarAdministrador());

        return painel;
    }

    // Lê os campos e cadastra um novo administrador.
    private void cadastrarAdministrador() {

        String nome = txtNomeAdministrador.getText().trim();
        String email = txtEmailAdministrador.getText().trim();
        String senha = txtSenhaAdministrador.getText().trim();

        // Verifica se todos os campos obrigatórios foram preenchidos.
        if (nome.isEmpty()
                || email.isEmpty()
                || senha.isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "Preencha todos os campos do administrador."
            );

            return;
        }

        // Cria um objeto Administrador com os dados informados.
        Administrador administrador = new Administrador(
            nome,
            email,
            senha
        );

        // Adiciona o administrador por meio do Service.
        administradorService.adicionar(administrador);

        try {

            // Salva o cadastro e atualiza a interface.
            administradorService.salvar();

            atualizarTabelaAdministradores();

            limparCamposAdministrador();

            JOptionPane.showMessageDialog(
                this,
                "Administrador cadastrado com sucesso."
            );

        } catch (Exception e) {

            // Informa caso ocorra um erro durante o salvamento.
            JOptionPane.showMessageDialog(
                this,
                "Erro ao salvar administrador:\n" + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Atualiza a tabela com os administradores cadastrados.
    private void atualizarTabelaAdministradores() {

        if (modeloAdministradores == null) {
            return;
        }

        // Remove as linhas anteriores para evitar duplicações na tela.
        modeloAdministradores.setRowCount(0);

        // Adiciona uma linha para cada administrador encontrado.
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

    // Limpa os campos após cadastrar um administrador.
    private void limparCamposAdministrador() {

        txtNomeAdministrador.setText("");
        txtEmailAdministrador.setText("");
        txtSenhaAdministrador.setText("");
    }

    // ============================================================
    // ATUALIZAR TODAS AS TABELAS
    // ============================================================

    // Atualiza todas as tabelas da interface administrativa.
    // Este método é chamado no construtor após o carregamento dos dados.
    private void atualizarTodasTabelas() {

        atualizarTabelaClientes();
        atualizarTabelaAnimais();
        atualizarTabelaServicos();
        atualizarTabelaAgendamentos();
        atualizarTabelaAdministradores();
    }

}
