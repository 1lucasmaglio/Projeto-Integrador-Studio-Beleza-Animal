let animaisDisponiveis = [];
let servicosDisponiveis = [];

function carregarAnimais() {
    const codigoCliente = Number(sessionStorage.getItem("codigoCliente"));

    fetch("http://localhost:7070/animais")
    .then(response => {
        if (!response.ok) {
            throw new Error("Erro ao buscar animais.");
        }

        return response.json();
    })
    .then(animais => {
        animaisDisponiveis = animais;

        const selecao = document.getElementById("animal");

        animais.forEach(animal => {
            if (animal.codigoCliente === codigoCliente) {
                const opcao = document.createElement("option");

                opcao.value = animal.codigo;
                opcao.textContent = animal.nome;

                selecao.appendChild(opcao);
            }
        });
        
        listarAgendamentos();
    }) 
    .catch(erro => {
        console.error(erro);
    });
}

function carregarServicos() {

    fetch("http://localhost:7070/servicos")
    .then(response => {
        if (!response.ok) {
            throw new Error("Erro ao buscar serviços.");
        }

        return response.json();
    })
    .then(servicos => {
        servicosDisponiveis = servicos;

        const selecao = document.getElementById("servico");

        servicos.forEach(servico => {
            const opcao = document.createElement("option");

            opcao.value = servico.codigo;
            opcao.textContent = servico.nome;

            selecao.appendChild(opcao);
        });
    })
    .catch(erro => {
        console.error(erro);
    });
}


function atualizarPreco() {
    const codigoAnimal = Number(document.getElementById("animal").value);
    const codigoServico = Number(document.getElementById("servico").value);

    const animal = animaisDisponiveis.find(a => a.codigo === codigoAnimal);
    const servico = servicosDisponiveis.find(s => s.codigo === codigoServico);

    if (!animal || !servico) {
    return;
    }

    let valor = 0;

    if (animal.porte === "P") {
        valor = servico.valorP;
    } else if (animal.porte === "M") {
        valor = servico.valorM;
    } else {
        valor = servico.valorG;
    }

    const preco = document.getElementById("precoServico");
    preco.textContent = "Preço: R$ " + valor.toFixed(2).replace(".", ",");
}

function listarAgendamentos() {
    const codigoCliente = Number(sessionStorage.getItem("codigoCliente"));

    fetch("http://localhost:7070/agendamentos")
    .then(response => {
        if (!response.ok) {
            throw new Error("Erro ao buscar agendamentos.");
        }

        return response.json();
        })

        .then(agendamentos => {
            console.log(agendamentos);
        
            const lista = document.getElementById("listaAgendamentos");
            lista.innerHTML = "";

        agendamentos.forEach(agendamento => {
            const animal = animaisDisponiveis.find(a => a.codigo === agendamento.codigoAnimal);

            if (animal && animal.codigoCliente === codigoCliente) {
                const item = document.createElement("p");
                item.textContent = animal.nome + " - " + agendamento.data + " - " + agendamento.hora;
                lista.appendChild(item);
            }
        });
    });
}

carregarAnimais();
carregarServicos();

document.getElementById("animal").addEventListener("change", atualizarPreco);
document.getElementById("servico").addEventListener("change", atualizarPreco);

document.getElementById("formAgendamento").addEventListener("submit", function(event) {
    event.preventDefault();

    const codigoAnimal = Number(document.getElementById("animal").value);
    const codigoServico = Number(document.getElementById("servico").value);
    const data = document.getElementById("data").value;
    const hora = document.getElementById("hora").value;

    const agendamento = {
    codigoAnimal: codigoAnimal,
    codigoServico: codigoServico,
    data: data,
    hora: hora
    };

    fetch("http://localhost:7070/agendamentos", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(agendamento)
    })

    .then(response => {
        if (!response.ok) {
            throw new Error("Erro ao agendar serviço.");
        }

        return response.json();
    })

    .then(agendamentoCriado => {
        console.log("Agendamento criado:", agendamentoCriado);
        alert("Agendamento realizado com sucesso!");
        listarAgendamentos();
    })

    .catch(erro => {
        console.error(erro);
        alert("Não foi possível realizar o agendamento.");
    });

});