
/* Armazena os dados utilizados na página Meus Agendamentos. */
let animaisDisponiveis = [];
let servicosDisponiveis = [];
let agendamentoParaCancelar = null;
let agendamentoParaEditar = null;

/* Carrega os serviços cadastrados. */
function carregarServicos() {
    fetch("http://localhost:7070/servicos")
    .then(response => response.json())
    .then(servicos => {
        servicosDisponiveis = servicos;
        carregarAnimais();
    })
    .catch(erro => {
        console.error("Erro ao carregar serviços:", erro);
    });
}

/* Carrega os animais cadastrados. */
function carregarAnimais() {
    fetch("http://localhost:7070/animais")
    .then(response => response.json())
    .then(animais => {
        animaisDisponiveis = animais;
        listarAgendamentos();
    })
    .catch(erro => {
        console.error("Erro ao carregar animais:", erro);
    });
}

/* Abre a janela de edição do agendamento. */
function abrirModalEdicao(agendamento) {
    // Armazena o agendamento selecionado para edição.
    agendamentoParaEditar = agendamento;

    // Converte a data de dd/MM/yyyy para yyyy-MM-dd.
    const [dia, mes, ano] = agendamento.data.split("/");
    const dataFormatada = `${ano}-${mes}-${dia}`;

    // Preenche os campos com a data e o horário atuais.
    document.getElementById("novaData").value = dataFormatada;
    document.getElementById("novaHora").value = agendamento.hora;

    // Exibe a janela de edição.
    document.getElementById("modalEdicao").hidden = false;
}

/* Abre a janela de confirmação de cancelamento. */
function abrirModalCancelamento(agendamento) {
    // Armazena o agendamento selecionado para cancelamento.
    agendamentoParaCancelar = agendamento;

    // Localiza a janela de confirmação.
    const modal = document.getElementById("modalCancelamento");

    // Localiza o elemento que exibe a mensagem.
    const texto = document.getElementById("textoCancelamento");

    // Restaura a cor original da mensagem.
    texto.style.color = "";

    // Exibe a data e o horário do agendamento selecionado.
    texto.textContent = "Deseja cancelar o agendamento do dia " +
        agendamento.data + " às " + agendamento.hora + "?";

    // Exibe a janela de confirmação.
    modal.hidden = false;
}

/* Fecha a janela de confirmação de cancelamento. */
function fecharModalCancelamento() {
    // Oculta a janela de confirmação.
    document.getElementById("modalCancelamento").hidden = true;

    // Limpa a referência ao agendamento selecionado.
    agendamentoParaCancelar = null;
}

/* Fecha a janela de edição do agendamento. */
function fecharModalEdicao() {
    // Oculta a janela de edição.
    document.getElementById("modalEdicao").hidden = true;

    // Limpa a referência ao agendamento selecionado.
    agendamentoParaEditar = null;
}

/* Carrega e exibe os agendamentos do cliente. */
function listarAgendamentos() {
    fetch("http://localhost:7070/agendamentos")
    .then(response => response.json())
    .then(agendamentos => {

        // Recupera o código do cliente que realizou o login.
        const codigoCliente = Number(sessionStorage.getItem("codigoCliente"));

        // Localiza o elemento que contém a lista de agendamentos.
        const lista = document.getElementById("listaAgendamentos");

        // Limpa os agendamentos exibidos anteriormente.
        lista.innerHTML = "";

        // Percorre os agendamentos recebidos da API.
        agendamentos.forEach(agendamento => {

            // Localiza o animal associado ao agendamento.
            const animal = animaisDisponiveis.find(a => a.codigo === agendamento.codigoAnimal);

            // Localiza o serviço associado ao agendamento.
            const servico = servicosDisponiveis.find(s => s.codigo === agendamento.codigoServico);

            // Ignora agendamentos de outros clientes e agendamentos cancelados.
            if (!animal || animal.codigoCliente !== codigoCliente || agendamento.status === "Cancelado") {
                return;
            }

            // Calcula o preço do serviço conforme o porte do animal.
            let valor = 0;

            if (servico && animal) {
                if (animal.porte === "P") {
                    valor = servico.valorP;
                } else if (animal.porte === "M") {
                    valor = servico.valorM;
                } else {
                    valor = servico.valorG;
                }

                // Acrescenta o valor do transporte, quando solicitado.
                if (agendamento.levaETraz) {
                    valor = valor + 10;
                }
            }

            // Cria o cartão que representa o agendamento.
            const item = document.createElement("div");

            // Define a classe CSS do cartão.
            item.className = "cartao-agendamento";

            // Cria o cabeçalho do cartão.
            const cabecalho = document.createElement("div");
            cabecalho.className = "cabecalho-agendamento";

            // Cria o elemento que exibe o nome do animal.
            const nomeAnimal = document.createElement("h3");
            nomeAnimal.textContent = animal.nome;

            // Cria o elemento que exibe o nome do serviço.
            const nomeServico = document.createElement("p");
            nomeServico.textContent = servico ? servico.nome : "Serviço não encontrado";

            // Adiciona o nome do animal e o serviço ao cabeçalho.
            cabecalho.appendChild(nomeAnimal);
            cabecalho.appendChild(nomeServico);

            // Adiciona o cabeçalho ao cartão.
            item.appendChild(cabecalho);

            // Cria a área que contém os detalhes do agendamento.
            const detalhes = document.createElement("div");
            detalhes.className = "detalhes-agendamento";

            // Cria o elemento que exibe a data e o horário.
            const dataHorario = document.createElement("p");
            dataHorario.textContent = "Data: " + agendamento.data +
                " às " + agendamento.hora;

            // Cria o elemento que exibe o status.
            const status = document.createElement("p");
            status.textContent = "Status: " + agendamento.status;

            // Cria o elemento que informa se o transporte foi solicitado.
            const transporte = document.createElement("p");
            transporte.textContent = "Leva e Traz: " +
                (agendamento.levaETraz ? "Sim" : "Não");

            // Cria o elemento que exibe o preço formatado.
            const preco = document.createElement("p");
            preco.textContent = "Preço: R$ " + valor.toFixed(2).replace(".", ",");

            // Adiciona as informações à área de detalhes.
            detalhes.appendChild(dataHorario);
            detalhes.appendChild(status);
            detalhes.appendChild(transporte);
            detalhes.appendChild(preco);

            // Adiciona a área de detalhes ao cartão.
            item.appendChild(detalhes);

            // Cria os botões de edição e cancelamento para agendamentos ativos.
            if (agendamento.status === "Agendado") {

                // Cria e configura o botão de edição.
                const botaoEditar = document.createElement("button");
                botaoEditar.textContent = "Editar";
                botaoEditar.className = "botao-editar";
                botaoEditar.title = "Editar agendamento";

                // Abre a janela de edição ao clicar no botão.
                botaoEditar.addEventListener("click", function() {
                    abrirModalEdicao(agendamento);
                });

                // Adiciona o botão de edição ao cartão.
                item.appendChild(botaoEditar);

                // Cria e configura o botão de cancelamento.
                const botaoCancelar = document.createElement("button");
                botaoCancelar.textContent = "✕";
                botaoCancelar.className = "botao-cancelar";
                botaoCancelar.title = "Cancelar agendamento";

                // Abre a janela de confirmação ao clicar no botão.
                botaoCancelar.addEventListener("click", function() {
                    abrirModalCancelamento(agendamento);
                });

                // Adiciona o botão de cancelamento ao cartão.
                item.appendChild(botaoCancelar);
            }

            // Adiciona o cartão à lista de agendamentos.
            lista.appendChild(item);
        });
    })
    .catch(erro => {
        console.error("Erro ao carregar agendamentos:", erro);
    });
}

// Fecha a janela de cancelamento quando o cliente clica em Voltar.
document.getElementById("voltarCancelamento").addEventListener("click", function() {
    fecharModalCancelamento();
});

// Fecha a janela de edição quando o cliente clica em Voltar.
document.getElementById("voltarEdicao").addEventListener("click", function() {
    fecharModalEdicao();
});

// Confirma o cancelamento do agendamento selecionado.
document.getElementById("confirmarCancelamento").addEventListener("click", function() {

    // Verifica se existe um agendamento selecionado para cancelamento.
    if (agendamentoParaCancelar === null) {
        return;
    }

    // Recupera o código do agendamento selecionado.
    const codigo = agendamentoParaCancelar.codigo;

    // Envia a solicitação de cancelamento para a API Java.
    fetch(`http://localhost:7070/agendamentos/${codigo}/cancelar`, {
        method: "PUT"
    })
    .then(async response => {
        // Verifica se a API recusou o cancelamento.
        if (!response.ok) {
            const erro = await response.text();
            throw new Error(erro);
        }

        // Recupera a mensagem retornada pela API.
        return response.text();
    })
    .then(mensagem => {
        // Fecha a janela e atualiza a lista de agendamentos.
        fecharModalCancelamento();
        listarAgendamentos();

        // Exibe a mensagem de confirmação no console.
        console.log(mensagem);
    })
    .catch(erro => {
        // Exibe a mensagem de erro na janela de cancelamento.
        const texto = document.getElementById("textoCancelamento");
        texto.textContent = erro.message;
        texto.style.color = "red";
    });
});

// Salva as alterações realizadas no agendamento.
document.getElementById("confirmarEdicao").addEventListener("click", function() {

    // Verifica se existe um agendamento selecionado.
    if (agendamentoParaEditar === null) {
        return;
    }

    // Recupera o código do agendamento selecionado.
    const codigo = agendamentoParaEditar.codigo;

    // Recupera a nova data e o novo horário.
    const dataSelecionada = document.getElementById("novaData").value;
    const novaHora = document.getElementById("novaHora").value;

    // Verifica se os campos foram preenchidos.
    if (!dataSelecionada || !novaHora) {
        document.getElementById("mensagemEdicao").textContent =
            "Preencha a data e o horário.";
        return;
    }

    // Converte a data de yyyy-MM-dd para dd/MM/yyyy.
    const [ano, mes, dia] = dataSelecionada.split("-");
    const novaData = `${dia}/${mes}/${ano}`;

    // Envia os novos dados para a API Java.
    fetch(`http://localhost:7070/agendamentos/${codigo}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            data: novaData,
            hora: novaHora
        })
    })
    .then(async response => {
        // Verifica se a API recusou a atualização.
        if (!response.ok) {
            const erro = await response.text();
            throw new Error(erro);
        }

        // Converte a resposta da API em um objeto JavaScript.
        return response.json();
    })
    .then(agendamentoAtualizado => {
        // Fecha a janela e atualiza a lista.
        fecharModalEdicao();
        listarAgendamentos();

        // Exibe o agendamento atualizado no console.
        console.log(agendamentoAtualizado);
    })
    .catch(erro => {
        // Exibe a mensagem de erro na janela de edição.
        document.getElementById("mensagemEdicao").textContent = erro.message;
    });
});

// Inicia o carregamento dos dados da página.
carregarServicos();
