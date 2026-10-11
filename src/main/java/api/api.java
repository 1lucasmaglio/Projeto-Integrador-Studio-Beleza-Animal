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

        clienteService.carregar();
        animalService.carregar();
        servicoService.carregar();
        agendamentoService.carregar();
        administradorService.carregar();

        // =========================
        // JAVALIN
        // =========================

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

        app.get("/", ctx -> {

            ctx.result(
                "API Studio de Beleza Animal"
            );
        });

        // =========================
        // CLIENTES
        // =========================

        app.get("/clientes", ctx -> {

            StringBuilder resposta =
                    new StringBuilder();

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

            ctx.result(resposta.toString());
        });

        app.get("/clientes/{id}", ctx -> {

            int id = Integer.parseInt(
                ctx.pathParam("id")
            );

            Cliente cliente =
                    clienteService.buscarPorId(id);

            if (cliente == null) {

                ctx.status(404);

                ctx.result(
                    "Cliente não encontrado."
                );

                return;
            }

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

        app.post("/clientes", ctx -> {

            Cliente cliente = ctx.bodyAsClass(Cliente.class);

            if (!clienteService.validarCliente(cliente)) {
                ctx.status(400);
                ctx.result("Dados do cliente inválidos.");
                return;
            }

            clienteService.adicionar(cliente);
            clienteService.salvar();

            ctx.status(201);
            ctx.json(java.util.Map.of(
                "codigo", cliente.getCodigo(),
                "nome", cliente.getNome(),
                "telefone", cliente.getTelefone(),
                "email", cliente.getEmail()
            ));
            
        });

        app.post("/login", ctx -> {

            java.util.Map<String, String> dados = ctx.bodyAsClass(java.util.Map.class);

            String email = dados.get("email");
            String senha = dados.get("senha");

            Cliente cliente = clienteService.autenticar(email, senha);

            if (cliente == null) {
                ctx.status(401);
                ctx.result("E-mail ou senha incorretos.");
                return;
            }

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

        app.get("/animais", ctx -> {

            ctx.json(animalService.listar());

        });

        app.get("/animais/{id}", ctx -> {

            int id = Integer.parseInt(
                ctx.pathParam("id")
            );

            Animal animal =
                    animalService.buscarPorId(id);

            if (animal == null) {

                ctx.status(404);
                ctx.result("Animal não encontrado.");

                return;
            }

            ctx.json(animal);
        });

        app.post("/animais", ctx -> {

            Animal animal = ctx.bodyAsClass(Animal.class);

            Cliente cliente = clienteService.buscarPorId(animal.getCodigoCliente());

            if (cliente == null) {
                ctx.status(400);
                ctx.result("Cliente não encontrado.");
                return;
            }
           
            boolean animalValido = animalService.validarAnimal(animal);

            if (!animalValido) {
                ctx.status(400);
                ctx.result("Dados do animal inválidos.");
                return;
            }
            
            if (animal.getRaca() == null || animal.getRaca().isBlank()) {
                animal.setRaca("Não informada");
            }

            animalService.adicionar(animal);
            animalService.salvar();

            ctx.status(201);
            ctx.json(animal);

        });

        app.put("/animais/{id}", ctx -> {

            int id = Integer.parseInt(ctx.pathParam("id"));
 
            Animal dadosAtualizados = ctx.bodyAsClass(Animal.class);

            boolean animalValido = animalService.validarAnimal(dadosAtualizados);

            if (!animalValido) {
                ctx.status(400);
                ctx.result("Dados do animal inválidos.");
                return;
            }

            if (dadosAtualizados.getRaca() == null || dadosAtualizados.getRaca().isBlank()) {
                dadosAtualizados.setRaca("Não informada");
            }       

            boolean atualizado = animalService.atualizar(id, dadosAtualizados);

            if (!atualizado) {
                ctx.status(404);
                ctx.result("Animal não encontrado.");
                return;
            }

            animalService.salvar();
            ctx.json(animalService.buscarPorId(id));

        });

        app.delete("/animais/{id}", ctx -> {

            int id = Integer.parseInt(ctx.pathParam("id"));

            boolean excluido = animalService.excluir(id);

            if (!excluido) {
                ctx.status(404);
                ctx.result("Animal não encontrado.");
                return;
            }

            animalService.salvar();
            ctx.status(204);

        });

        // =========================
        // SERVICOS
        // =========================

        app.get("/servicos", ctx -> {
            ctx.json(servicoService.listar());
        });

        app.get("/servicos/{id}", ctx -> {

            int id = Integer.parseInt(ctx.pathParam("id"));

            Servico servico = servicoService.buscarPorId(id);

            if (servico == null) {
                ctx.status(404);
                ctx.result("Serviço não encontrado.");
                return;
            }

            ctx.json(servico);
        });

        // =========================
        // AGENDAMENTOS
        // =========================

        app.get("/agendamentos", ctx -> {
            ctx.json(agendamentoService.listar());
        });
        
        app.get("/agendamentos/{id}", ctx -> {

            int id = Integer.parseInt(
                ctx.pathParam("id")
            );

            Agendamento agendamento =
                    agendamentoService.buscarPorId(id);

            if (agendamento == null) {

                ctx.status(404);

                ctx.result(
                    "Agendamento não encontrado."
                );

                return;
            }

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

        app.post("/agendamentos", ctx -> {

            Agendamento agendamento = ctx.bodyAsClass(Agendamento.class);

            Animal animal = animalService.buscarPorId(agendamento.getCodigoAnimal());
            Servico servico = servicoService.buscarPorId(agendamento.getCodigoServico());

            if (animal == null || servico == null) {
                ctx.status(400);
                ctx.result("Animal ou serviço inválido.");
                return;
            }

            if (agendamento.getData() == null || agendamento.getData().isBlank()
                    || agendamento.getHora() == null || agendamento.getHora().isBlank()) {
                ctx.status(400);
                ctx.result("Data ou horário inválido.");
                return;
            }

            agendamento.setStatus("Agendado");

            boolean adicionado = agendamentoService.adicionar(agendamento);

            if (!adicionado) {
                ctx.status(409);
                ctx.result("Horário lotado. Escolha outro horário.");
                return;
            }

            agendamentoService.salvar();

            ctx.status(201);
            ctx.json(agendamento);
        });

        // =========================
        // ADMINISTRADORES
        // =========================

        app.get("/administradores", ctx -> {

            StringBuilder resposta =
                    new StringBuilder();

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

            ctx.result(resposta.toString());
        });

        app.get("/administradores/{id}", ctx -> {

            int id = Integer.parseInt(
                ctx.pathParam("id")
            );

            Administrador administrador =
                    administradorService.buscarPorId(id);

            if (administrador == null) {

                ctx.status(404);

                ctx.result(
                    "Administrador não encontrado."
                );

                return;
            }

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

        app.start(7070);
    }
}