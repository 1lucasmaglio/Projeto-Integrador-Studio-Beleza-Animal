package api;

import io.javalin.Javalin;

import model.Cliente;
import model.Animal;
import model.Servico;
import model.Agendamento;

import service.ClienteService;
import service.AnimalService;
import service.ServicoService;
import service.AgendamentoService;
import service.AdministradorService;

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

        var app = Javalin.create();

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

        // =========================
        // ANIMAIS
        // =========================

        app.get("/animais", ctx -> {

            StringBuilder resposta =
                    new StringBuilder();

            for (Animal animal :
                    animalService.listar()) {

                resposta
                    .append(animal.getCodigo())
                    .append(" - ")
                    .append(animal.getNome())
                    .append(" - ")
                    .append(animal.getEspecie())
                    .append(" - ")
                    .append(animal.getRaca())
                    .append(" - ")
                    .append(animal.getPorte())
                    .append(" - Cliente: ")
                    .append(animal.getCodigoCliente())
                    .append("\n");
            }

            ctx.result(resposta.toString());
        });

        app.get("/animais/{id}", ctx -> {

            int id = Integer.parseInt(
                ctx.pathParam("id")
            );

            Animal animal =
                    animalService.buscarPorId(id);

            if (animal == null) {

                ctx.status(404);

                ctx.result(
                    "Animal não encontrado."
                );

                return;
            }

            ctx.result(
                animal.getCodigo()
                + " - "
                + animal.getNome()
                + " - "
                + animal.getEspecie()
                + " - "
                + animal.getRaca()
                + " - "
                + animal.getPorte()
            );
        });

        // =========================
        // SERVICOS
        // =========================

        app.get("/servicos", ctx -> {

            StringBuilder resposta =
                    new StringBuilder();

            for (Servico servico :
                    servicoService.listar()) {

                resposta
                    .append(servico.getCodigo())
                    .append(" - ")
                    .append(servico.getNome())
                    .append(" - R$ ")
                    .append(servico.getValor())
                    .append(" - ")
                    .append(servico.getDuracao())
                    .append(" minutos")
                    .append("\n");
            }

            ctx.result(resposta.toString());
        });

        app.get("/servicos/{id}", ctx -> {

            int id = Integer.parseInt(
                ctx.pathParam("id")
            );

            Servico servico =
                    servicoService.buscarPorId(id);

            if (servico == null) {

                ctx.status(404);

                ctx.result(
                    "Serviço não encontrado."
                );

                return;
            }

            ctx.result(
                servico.getCodigo()
                + " - "
                + servico.getNome()
                + " - "
                + servico.getDescricao()
                + " - R$ "
                + servico.getValor()
            );
        });

        // =========================
        // AGENDAMENTOS
        // =========================

        app.get("/agendamentos", ctx -> {

            StringBuilder resposta =
                    new StringBuilder();

            for (Agendamento agendamento :
                    agendamentoService.listar()) {

                resposta
                    .append(agendamento.getCodigo())
                    .append(" - ")
                    .append(agendamento.getData())
                    .append(" ")
                    .append(agendamento.getHora())
                    .append(" - ")
                    .append(agendamento.getStatus())
                    .append(" - Animal: ")
                    .append(
                        agendamento.getCodigoAnimal()
                    )
                    .append(" - Serviço: ")
                    .append(
                        agendamento.getCodigoServico()
                    )
                    .append("\n");
            }

            ctx.result(resposta.toString());
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

        // =========================
        // INICIAR SERVIDOR
        // =========================

        app.start(7070);
    }
}