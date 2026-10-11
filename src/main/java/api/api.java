
package api;

import io.javalin.Javalin;
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

public class api {

    public static void main(String[] args) throws Exception {

        // =========================
        // SERVICES
        // =========================

        // Cria os objetos responsáveis pelas operações de cada parte do sistema.
        // A API recebe as requisições e utiliza esses Services para executar as ações.

        ClienteService clienteService =
                new ClienteService();

        AnimalService animalService =
                new AnimalService();

        ServicoService servicoService =
                new ServicoService();

        AgendamentoService agendamentoService =
                new AgendamentoService();

        AdministradorService administradorService =
                new AdministradorService();

        // =========================
        // CARREGAR CSV
        // =========================

        // Carrega os dados salvos nos arquivos CSV para a memória do sistema.
        // Isso permite recuperar os cadastros quando o servidor é iniciado novamente.

        clienteService.carregar();
        animalService.carregar();
        servicoService.carregar();
        agendamentoService.carregar();
        administradorService.carregar();

        // =========================
        // JAVALIN
        // =========================

        // Cria o servidor utilizando o framework Javalin.
        // O CORS permite que o frontend, aberto em outra porta, acesse a API.

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(rule -> {
                    rule.allowHost("http://127.0.0.1:5500");
                });
            });
        });

        // =========================
        // ROTA INICIAL
        // =========================

        // GET: utilizado para consultar informações.
        // Esta rota mostra uma mensagem quando alguém acessa o endereço principal da API.

        app.get("/", ctx -> {

            ctx.result(
                "API Studio de Beleza Animal"
            );
        });

        // =========================
        // CLIENTES
        // =========================

        // GET /clientes: consulta todos os clientes cadastrados.
        // StringBuilder é utilizado para montar a resposta em formato de texto.

        app.get("/clientes", ctx -> {

            StringBuilder resposta =
                    new StringBuilder();

            // Percorre a lista de clientes retornada pelo ClienteService.
            for (Cliente cliente :
                    clienteService.listar()) {

                resposta
                    .append(cliente.getCodigo())
                    .append(" - ")
                    .append(cliente.getNome())
                    .append(" - ")
                    .append(cliente.getTelefone())
                    .append(" - ")
                    .append(cliente.getEmail())
                    .append("\n");
            }

            // Envia o texto montado como resposta da requisição.
            ctx.result(resposta.toString());
        });

        // GET /clientes/{id}: procura um cliente pelo seu código.
        // O {id} representa um valor recebido diretamente pela URL.

        app.get("/clientes/{id}", ctx -> {

            // Recupera o ID da URL e converte de String para int.
            int id = Integer.parseInt(
                ctx.pathParam("id")
            );

            // Solicita ao Service a busca do cliente.
            Cliente cliente =
                    clienteService.buscarPorId(id);

            // Se não encontrar o cliente, retorna o código HTTP 404.
            if (cliente == null) {

                ctx.status(404);

                ctx.result(
                    "Cliente não encontrado."
                );

                return;
            }

            // Se encontrar, retorna os dados do cliente em formato de texto.
            ctx.result(
                cliente.getCodigo()
                + " - "
                + cliente.getNome()
                + " - "
                + cliente.getTelefone()
                + " - "
                + cliente.getEmail()
            );
        });

        // POST /clientes: recebe os dados enviados pelo formulário de cadastro.
        // POST é utilizado para cadastrar novas informações.

        app.post("/clientes", ctx -> {

            // Converte o JSON recebido em um objeto da classe Cliente.
            Cliente cliente = ctx.bodyAsClass(Cliente.class);

            // Solicita ao ClienteService a validação dos dados recebidos.
            if (!clienteService.validarCliente(cliente)) {
                // HTTP 400 indica que os dados enviados são inválidos.
                ctx.status(400);
                ctx.result("Dados do cliente inválidos.");
                return;
            }

            // Adiciona o cliente e salva os dados no CSV.
            clienteService.adicionar(cliente);
            clienteService.salvar();

            // HTTP 201 indica que um novo recurso foi criado.
            ctx.status(201);

            // Retorna os dados do cliente em JSON, sem incluir a senha.
            ctx.json(java.util.Map.of(
                "codigo", cliente.getCodigo(),
                "nome", cliente.getNome(),
                "telefone", cliente.getTelefone(),
                "email", cliente.getEmail()
            ));

        });

        // POST /login: recebe e-mail e senha para autenticar o cliente.
        // Esta rota é utilizada pelo arquivo login.js do frontend.

        app.post("/login", ctx -> {

            // Recebe os dados enviados em JSON e os organiza em um Map.
            java.util.Map<String, String> dados = ctx.bodyAsClass(java.util.Map.class);

            // Recupera o e-mail e a senha enviados pelo formulário.
            String email = dados.get("email");
            String senha = dados.get("senha");

            // O ClienteService verifica se as credenciais correspondem a um cliente.
            Cliente cliente = clienteService.autenticar(email, senha);

            // Se a autenticação falhar, retorna HTTP 401.
            if (cliente == null) {
                ctx.status(401);
                ctx.result("E-mail ou senha incorretos.");
                return;
            }

            // Se o login for aceito, retorna HTTP 200 e os dados do cliente.
            // O frontend utiliza o código recebido para identificar o cliente.
            ctx.status(200);
            ctx.json(java.util.Map.of(
                "codigo", cliente.getCodigo(),
                "nome", cliente.getNome(),
                "email", cliente.getEmail()
            ));
        });

        // =========================
        // ANIMAIS
        // =========================

        // GET /animais: retorna todos os animais cadastrados em formato JSON.
        // O frontend utiliza essa lista para mostrar os animais do cliente.

        app.get("/animais", ctx -> {

            ctx.json(animalService.listar());

        });

        // GET /animais/{id}: consulta um animal específico pelo código.

        app.get("/animais/{id}", ctx -> {

            // Recupera o código informado na URL.
            int id = Integer.parseInt(
                ctx.pathParam("id")
            );

            // Busca o animal utilizando o AnimalService.
            Animal animal =
                    animalService.buscarPorId(id);

            // Se o animal não existir, retorna HTTP 404.
            if (animal == null) {

                ctx.status(404);
                ctx.result("Animal não encontrado.");

                return;
            }

            // Retorna o objeto Animal convertido automaticamente para JSON.
            ctx.json(animal);
        });

        // POST /animais: cadastra um novo animal associado a um cliente.

        app.post("/animais", ctx -> {

            // Converte o JSON recebido em um objeto Animal.
            Animal animal = ctx.bodyAsClass(Animal.class);

            // Verifica se o código do cliente informado pertence a um cliente existente.
            Cliente cliente = clienteService.buscarPorId(animal.getCodigoCliente());

            // Impede o cadastro quando o cliente não é encontrado.
            if (cliente == null) {
                ctx.status(400);
                ctx.result("Cliente não encontrado.");
                return;
            }

            // Solicita ao AnimalService a validação das informações do animal.
            boolean animalValido = animalService.validarAnimal(animal);

            // Se os dados forem inválidos, interrompe o cadastro.
            if (!animalValido) {
                ctx.status(400);
                ctx.result("Dados do animal inválidos.");
                return;
            }

            // Se a raça não for preenchida, utiliza um valor padrão.
            if (animal.getRaca() == null || animal.getRaca().isBlank()) {
                animal.setRaca("Não informada");
            }

            // Adiciona o animal e salva os dados no CSV.
            animalService.adicionar(animal);
            animalService.salvar();

            // Confirma o cadastro e retorna o animal criado em JSON.
            ctx.status(201);
            ctx.json(animal);

        });

        // PUT /animais/{id}: atualiza os dados de um animal existente.
        // PUT é utilizado para enviar informações atualizadas.

        app.put("/animais/{id}", ctx -> {

            // Identifica qual animal será atualizado.
            int id = Integer.parseInt(ctx.pathParam("id"));

            // Converte o JSON recebido em um objeto com os novos dados.
            Animal dadosAtualizados = ctx.bodyAsClass(Animal.class);

            // Verifica se os novos dados são válidos.
            boolean animalValido = animalService.validarAnimal(dadosAtualizados);

            if (!animalValido) {
                ctx.status(400);
                ctx.result("Dados do animal inválidos.");
                return;
            }

            // Define uma raça padrão caso esse campo esteja vazio.
            if (dadosAtualizados.getRaca() == null || dadosAtualizados.getRaca().isBlank()) {
                dadosAtualizados.setRaca("Não informada");
            }

            // Solicita ao Service a atualização do animal identificado pelo ID.
            boolean atualizado = animalService.atualizar(id, dadosAtualizados);

            // Se o animal não existir, retorna HTTP 404.
            if (!atualizado) {
                ctx.status(404);
                ctx.result("Animal não encontrado.");
                return;
            }

            // Salva as alterações e retorna os dados atualizados.
            animalService.salvar();
            ctx.json(animalService.buscarPorId(id));

        });

        // DELETE /animais/{id}: exclui um animal pelo código informado na URL.

        app.delete("/animais/{id}", ctx -> {

            // Recupera o código do animal que será excluído.
            int id = Integer.parseInt(ctx.pathParam("id"));

            // Solicita ao AnimalService a exclusão do cadastro.
            boolean excluido = animalService.excluir(id);

            // Se não encontrar o animal, retorna HTTP 404.
            if (!excluido) {
                ctx.status(404);
                ctx.result("Animal não encontrado.");
                return;
            }

            // Salva a exclusão no CSV.
            animalService.salvar();

            // HTTP 204 indica sucesso sem conteúdo na resposta.
            ctx.status(204);

        });

        // =========================
        // SERVICOS
        // =========================

        // GET /servicos: retorna todos os serviços cadastrados em JSON.
        // O frontend utiliza esses dados para preencher as opções de agendamento.

        app.get("/servicos", ctx -> {
            ctx.json(servicoService.listar());
        });

        // GET /servicos/{id}: busca um serviço específico pelo código.

        app.get("/servicos/{id}", ctx -> {

            // Recupera o código informado na URL.
            int id = Integer.parseInt(ctx.pathParam("id"));

            // Consulta o serviço utilizando o ServicoService.
            Servico servico = servicoService.buscarPorId(id);

            // Retorna HTTP 404 caso o serviço não exista.
            if (servico == null) {
                ctx.status(404);
                ctx.result("Serviço não encontrado.");
                return;
            }

            // Retorna os dados do serviço em JSON.
            ctx.json(servico);
        });

        // =========================
        // AGENDAMENTOS
        // =========================

        // GET /agendamentos: retorna todos os agendamentos em JSON.
        // O frontend utiliza essa resposta para montar a lista de agendamentos.

        app.get("/agendamentos", ctx -> {
            ctx.json(agendamentoService.listar());
        });

        // GET /agendamentos/{id}: consulta um agendamento específico.

        app.get("/agendamentos/{id}", ctx -> {

            // Recupera o código do agendamento informado na URL.
            int id = Integer.parseInt(
                ctx.pathParam("id")
            );

            // Busca o agendamento utilizando o AgendamentoService.
            Agendamento agendamento =
                    agendamentoService.buscarPorId(id);

            // Se o agendamento não existir, retorna HTTP 404.
            if (agendamento == null) {

                ctx.status(404);

                ctx.result(
                    "Agendamento não encontrado."
                );

                return;
            }

            // Retorna o código, a data, a hora e o status do agendamento.
            ctx.result(
                agendamento.getCodigo()
                + " - "
                + agendamento.getData()
                + " "
                + agendamento.getHora()
                + " - "
                + agendamento.getStatus()
            );
        });

        // POST /agendamentos: recebe os dados enviados pelo formulário de agendamento.

        app.post("/agendamentos", ctx -> {

            // Converte o JSON recebido em um objeto Agendamento.
            Agendamento agendamento = ctx.bodyAsClass(Agendamento.class);

            // Busca o animal e o serviço selecionados para verificar se existem.
            Animal animal = animalService.buscarPorId(agendamento.getCodigoAnimal());
            Servico servico = servicoService.buscarPorId(agendamento.getCodigoServico());

            // Impede o agendamento se o animal ou o serviço não existir.
            if (animal == null || servico == null) {
                ctx.status(400);
                ctx.result("Animal ou serviço inválido.");
                return;
            }

            // Verifica se a data e o horário foram informados.
            if (agendamento.getData() == null || agendamento.getData().isBlank()
                    || agendamento.getHora() == null || agendamento.getHora().isBlank()) {
                ctx.status(400);
                ctx.result("Data ou horário inválido.");
                return;
            }

            // Define o status inicial do novo agendamento.
            agendamento.setStatus("Agendado");

            // Solicita ao AgendamentoService a inclusão do agendamento.
            // O retorno boolean informa se a operação foi aceita.
            boolean adicionado = agendamentoService.adicionar(agendamento);

            // Se o Service recusar a inclusão, informa conflito de horário.
            // HTTP 409 representa um conflito com o estado atual do sistema.
            if (!adicionado) {
                ctx.status(409);
                ctx.result("Horário lotado. Escolha outro horário.");
                return;
            }

            // Salva o agendamento no CSV.
            agendamentoService.salvar();

            // Retorna HTTP 201 e os dados do agendamento criado.
            ctx.status(201);
            ctx.json(agendamento);
        });

        // =========================
        // ADMINISTRADORES
        // =========================

        // GET /administradores: consulta todos os administradores cadastrados.
        // A resposta é montada em texto utilizando StringBuilder.

        app.get("/administradores", ctx -> {

            StringBuilder resposta =
                    new StringBuilder();

            // Percorre a lista de administradores recebida do Service.
            for (Administrador administrador :
                    administradorService.listar()) {

                resposta
                    .append(administrador.getCodigo())
                    .append(" - ")
                    .append(administrador.getNome())
                    .append(" - ")
                    .append(administrador.getEmail())
                    .append("\n");
            }

            // Envia a lista montada como resposta.
            ctx.result(resposta.toString());
        });

        // GET /administradores/{id}: procura um administrador pelo código.

        app.get("/administradores/{id}", ctx -> {

            // Recupera o código informado na URL.
            int id = Integer.parseInt(
                ctx.pathParam("id")
            );

            // Solicita ao AdministradorService a busca pelo código.
            Administrador administrador =
                    administradorService.buscarPorId(id);

            // Retorna HTTP 404 se o administrador não for encontrado.
            if (administrador == null) {

                ctx.status(404);

                ctx.result(
                    "Administrador não encontrado."
                );

                return;
            }

            // Retorna os dados do administrador em formato de texto.
            ctx.result(
                administrador.getCodigo()
                + " - "
                + administrador.getNome()
                + " - "
                + administrador.getEmail()
            );
        });

        // =========================
        // INICIAR SERVIDOR
        // =========================

        // Inicia o servidor Javalin na porta 7070.
        // O frontend utiliza http://localhost:7070 para se comunicar com esta API.
        app.start(7070);
    }

}
