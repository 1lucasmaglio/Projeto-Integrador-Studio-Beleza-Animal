
console.log("AGENDAMENTOS.JS INICIADO");

/* Armazena os animais e serviços recebidos da API para utilizá-los em outras funções. */
let animaisDisponiveis = [];
let servicosDisponiveis = [];
let agendamentoParaCancelar = null;

/* Busca os animais cadastrados e adiciona ao formulário apenas os do cliente logado. */
function carregarAnimais() {
    // Recupera o código do cliente salvo durante o login.
    const codigoCliente = Number(sessionStorage.getItem("codigoCliente"));

    // Solicita à API Java a lista de animais cadastrados.
    fetch("http://localhost:7070/animais")
    .then(response => {
        // Verifica se a consulta foi realizada com sucesso.
        if (!response.ok) {
            throw new Error("Erro ao buscar animais.");
        }

        // Converte a resposta JSON em dados JavaScript.
        return response.json();
    })
    .then(animais => {
        // Guarda os animais recebidos para utilizá-los posteriormente.
        animaisDisponiveis = animais;

        // Localiza o campo de seleção de animais no HTML.
        const selecao = document.getElementById("animal");

        // Percorre todos os animais recebidos da API.
        animais.forEach(animal => {
            // Verifica se o animal pertence ao cliente logado.
            if (animal.codigoCliente === codigoCliente) {
                // Cria uma nova opção no campo de seleção.
                const opcao = document.createElement("option");

                // Usa o código do animal como valor e seu nome como texto visível.
                opcao.value = animal.codigo;
                opcao.textContent = animal.nome;

                // Adiciona a opção ao campo de seleção.
                selecao.appendChild(opcao);
            }
        });

        // Carrega os agendamentos após obter os dados dos animais.
        listarAgendamentos();
    })
    .catch(erro => {
        // Registra possíveis erros no console.
        console.error(erro);
    });
}

/* Busca os serviços cadastrados e preenche o campo de seleção do formulário. */
function carregarServicos() {
    // Solicita à API Java a lista de serviços disponíveis.
    fetch("http://localhost:7070/servicos")
    .then(response => {
        // Verifica se ocorreu algum erro na consulta.
        if (!response.ok) {
            throw new Error("Erro ao buscar serviços.");
        }

        // Converte a resposta JSON em dados JavaScript.
        return response.json();
    })
    .then(servicos => {
        // Guarda os serviços recebidos para calcular os preços posteriormente.
        servicosDisponiveis = servicos;

        // Localiza o campo de seleção de serviços no HTML.
        const selecao = document.getElementById("servico");

        // Percorre os serviços cadastrados.
        servicos.forEach(servico => {
            // Cria uma opção para cada serviço.
            const opcao = document.createElement("option");

            // Usa o código do serviço como valor e seu nome como texto visível.
            opcao.value = servico.codigo;
            opcao.textContent = servico.nome;

            // Adiciona a opção ao campo de seleção.
            selecao.appendChild(opcao);
        });
    })
    .catch(erro => {
        // Registra possíveis erros no console.
        console.error(erro);
    });
}

/* Calcula e exibe o preço do serviço de acordo com o porte do animal. */
function atualizarPreco() {
    // Obtém os códigos do animal e do serviço selecionados.
    const codigoAnimal = Number(document.getElementById("animal").value);
    const codigoServico = Number(document.getElementById("servico").value);

    // Procura os objetos correspondentes aos códigos selecionados.
    const animal = animaisDisponiveis.find(a => a.codigo === codigoAnimal);
    const servico = servicosDisponiveis.find(s => s.codigo === codigoServico);

    // Encerra a função se o animal ou o serviço ainda não estiver selecionado.
    if (!animal || !servico) {
        return;
    }

    // Variável que receberá o preço correspondente ao porte do animal.
    let valor = 0;

    // Seleciona o preço cadastrado para o porte P, M ou G.
    if (animal.porte === "P") {
        valor = servico.valorP;
    } else if (animal.porte === "M") {
        valor = servico.valorM;
    } else {
        valor = servico.valorG;
    }

    // Acrescenta R$10 ao preço quando Leva e Traz estiver selecionado.
    if (document.getElementById("levaETraz").checked) {
        valor = valor + 10;
    }

    // Localiza o elemento HTML responsável por mostrar o preço.
    const preco = document.getElementById("precoServico");

    // Exibe o preço com duas casas decimais e vírgula no lugar do ponto.
    preco.textContent = "Preço: R$ " + valor.toFixed(2).replace(".", ",");
}

/* Abre a janela de confirmação do cancelamento. */
function abrirModalCancelamento(agendamento) {
    // Guarda o agendamento selecionado.
    agendamentoParaCancelar = agendamento;

    // Localiza a janela no HTML.
    const modal = document.getElementById("modalCancelamento");

    // Localiza o texto da janela.
    const texto = document.getElementById("textoCancelamento");

    // Mostra a data e o horário do agendamento.
    texto.textContent = "Deseja cancelar o agendamento do dia " +
        agendamento.data + " às " + agendamento.hora + "?";

    // Mostra a janela de confirmação.
    modal.hidden = false;
}

/* Fecha a janela de confirmação do cancelamento. */
function fecharModalCancelamento() {
    // Esconde a janela.
    document.getElementById("modalCancelamento").hidden = true;

    // Limpa o agendamento selecionado.
    agendamentoParaCancelar = null;
}

// Fecha a janela quando o cliente clica em Voltar.
document.getElementById("voltarCancelamento").addEventListener("click", function() {
    fecharModalCancelamento();
});

// Confirma o cancelamento do agendamento.
document.getElementById("confirmarCancelamento").addEventListener("click", function() {
    // Verifica se existe um agendamento selecionado.
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
    // Verifica se o cancelamento foi aceito.
    if (!response.ok) {
        const erro = await response.text();
        throw new Error(erro);
    }

    // Recupera a mensagem enviada pelo Java.
    return response.text();
})
.then(mensagem => {
    // Fecha a janela e atualiza os agendamentos.
    fecharModalCancelamento();
    listarAgendamentos();

    // Mostra a mensagem de sucesso na página.
    const aviso = document.getElementById("mensagemAgendamento");
    aviso.textContent = mensagem;
    aviso.style.color = "green";
})
.catch(erro => {
    // Mostra o erro dentro da janela de confirmação.
    const texto = document.getElementById("textoCancelamento");
    texto.textContent = erro.message;
    texto.style.color = "red";
});

    console.log("Agendamento selecionado para cancelamento:", codigo);
});

/* Consulta os agendamentos e mostra aqueles associados aos animais do cliente logado. */
function listarAgendamentos() {
    // Recupera o código do cliente armazenado durante o login.
    const codigoCliente = Number(sessionStorage.getItem("codigoCliente"));

    // Solicita à API Java os agendamentos cadastrados.
    fetch("http://localhost:7070/agendamentos")
    .then(response => {
        // Verifica se a consulta foi realizada com sucesso.
        if (!response.ok) {
            throw new Error("Erro ao buscar agendamentos.");
        }

        // Converte a resposta JSON em uma lista de agendamentos.
        return response.json();
    })
    .then(agendamentos => {
        // Exibe os agendamentos no console para auxiliar nos testes.
        console.log(agendamentos);

        // Localiza a área onde os agendamentos serão apresentados.
        const lista = document.getElementById("listaAgendamentos");

        // Limpa a lista antes de preenchê-la novamente.
        lista.innerHTML = "";

        // Percorre todos os agendamentos recebidos da API.
        agendamentos.forEach(agendamento => {
            // Encontra o animal associado ao agendamento pelo código.
            const animal = animaisDisponiveis.find(a => a.codigo === agendamento.codigoAnimal);

            // Mostra somente os agendamentos de animais pertencentes ao cliente logado e que não estejam cancelados.
            if (animal && animal.codigoCliente === codigoCliente
        && agendamento.status !== "Cancelado") {
                // Cria um parágrafo para representar o agendamento na página.
                const item = document.createElement("p");

                // Mostra o nome do animal, a data e o horário agendados.
                item.textContent = animal.nome + " - " + agendamento.data + " - " + agendamento.hora;

                // Cria o botão de cancelamento para agendamentos ativos.
                if (agendamento.status === "Agendado") {
                    const botaoCancelar = document.createElement("button");

                    botaoCancelar.textContent = "✕";
                    botaoCancelar.className = "botao-cancelar";
                    botaoCancelar.title = "Cancelar agendamento";
                    botaoCancelar.setAttribute("aria-label", "Cancelar agendamento");

                        // Abre a janela de confirmação quando o cliente clica no botão.
                        botaoCancelar.addEventListener("click", function() {
                            abrirModalCancelamento(agendamento);
                        });

                    item.appendChild(botaoCancelar);
                }

                // Adiciona o agendamento à lista exibida no HTML.
                lista.appendChild(item);
            }
        });
    });
}

// Carrega os animais e serviços quando a página é aberta.
carregarAnimais();
carregarServicos();

// Atualiza o preço sempre que o cliente troca o animal ou o serviço selecionado.
document.getElementById("animal").addEventListener("change", atualizarPreco);
document.getElementById("servico").addEventListener("change", atualizarPreco);

// Atualiza o preço quando o cliente seleciona ou desmarca Leva e Traz.
document.getElementById("levaETraz").addEventListener("change", atualizarPreco);

/* Executa o cadastro de um agendamento quando o formulário é enviado. */
document.getElementById("formAgendamento").addEventListener("submit", function(event) {

    // Impede que o formulário recarregue a página automaticamente.
    event.preventDefault();
    console.log("Form submitted. Default prevented:", event.defaultPrevented);

    // Localiza a área onde será exibida a mensagem do agendamento.
    const mensagem = document.getElementById("mensagemAgendamento");

    // Limpa a mensagem anterior.
    mensagem.textContent = "";

    // Obtém os dados selecionados pelo cliente no formulário.
    const codigoAnimal = Number(document.getElementById("animal").value);
    const codigoServico = Number(document.getElementById("servico").value);

    // Obtém a data selecionada no formato yyyy-MM-dd.
    const dataOriginal = document.getElementById("data").value;

    // Separa o ano, o mês e o dia.
    const [ano, mes, dia] = dataOriginal.split("-");

    // Converte a data para o formato dd/MM/yyyy esperado pelo Java.
    const data = `${dia}/${mes}/${ano}`;
    const hora = document.getElementById("hora").value;

    // Verifica se o cliente selecionou Leva e Traz.
    const levaETraz = document.getElementById("levaETraz").checked;

    // Organiza os dados necessários para cadastrar o agendamento.
    const agendamento = {
        codigoAnimal: codigoAnimal,
        codigoServico: codigoServico,
        data: data,
        hora: hora,
        levaETraz: levaETraz
    };

    // Envia o agendamento para a API Java.
    fetch("http://localhost:7070/agendamentos", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        // Converte o objeto agendamento para JSON antes do envio.
        body: JSON.stringify(agendamento)
    })
    .then(async response => {
        if (!response.ok) {
            const erro = await response.text();
            throw new Error(erro);
        }

        return response.json();
    })
    .then(agendamentoCriado => {
        // Mostra o agendamento criado no console para auxiliar nos testes.
        console.log("Agendamento criado:", agendamentoCriado);

        // Informa ao cliente que o agendamento foi realizado.
        mensagem.textContent = "Agendamento realizado com sucesso!";
        mensagem.style.color = "green";

        // Atualiza a lista para incluir o novo agendamento.
        listarAgendamentos();
    })
    .catch(erro => {
        console.error(erro);

        mensagem.textContent = erro.message;
        mensagem.style.color = "red";

        console.log("Mensagem exibida no HTML:", mensagem.textContent);
    });
});
