
/* Busca o formulário de cadastro de animais pelo ID definido no HTML. */
const formulario = document.getElementById("formAnimal");

/* Executa esta função quando o cliente envia o formulário de cadastro de animal. */
formulario.addEventListener("submit", function(event) {
    // Impede que o formulário recarregue a página automaticamente.
    event.preventDefault();

    // Obtém as informações do animal preenchidas no formulário.
    const nome = document.getElementById("nome").value;
    const especie = document.getElementById("especie").value;
    const raca = document.getElementById("raca").value;
    const porte = document.getElementById("porte").value;

    // Recupera o código do cliente que realizou o login e converte para número.
    const codigoCliente = Number(sessionStorage.getItem("codigoCliente"));

    // Organiza os dados do animal e associa o cadastro ao cliente logado.
    const animal = {
        nome: nome,
        especie: especie,
        raca: raca,
        porte: porte,   
        codigoCliente: codigoCliente
    };

    // Exibe os dados do animal no console para auxiliar nos testes.
    console.log(animal);

    // Envia os dados do animal para a API Java realizar o cadastro.
    fetch("http://localhost:7070/animais", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        // Converte o objeto animal para JSON antes de enviá-lo.
        body: JSON.stringify(animal)
    })
    .then(response => {
        // Verifica se ocorreu algum erro durante o cadastro.
        if (!response.ok) {
            throw new Error("Erro ao cadastrar animal.");
        }

        // Converte a resposta JSON do servidor para um objeto JavaScript.
        return response.json();
    })

    .then(dados => {
        // Limpa os campos do formulário após o cadastro.
        formulario.reset();

        // Exibe a resposta do servidor no console.
        console.log(dados);

        // Atualiza a lista para mostrar o animal recém-cadastrado.
        listarAnimais();
    })

    .catch(erro => {
        // Registra o erro no console e informa o problema ao cliente.
        console.error(erro);
        alert("Não foi possível cadastrar o animal.");
    });

});

/* Busca os animais cadastrados e mostra apenas os que pertencem ao cliente logado. */
    function listarAnimais() {
        // Recupera o código do cliente armazenado durante o login.
        const codigoCliente = Number(sessionStorage.getItem("codigoCliente"));

        // Solicita à API Java a lista de animais cadastrados.
        fetch("http://localhost:7070/animais")
        .then(response => {
            // Verifica se a consulta foi realizada com sucesso.
            if (!response.ok) {
                throw new Error("Erro ao buscar animais.");
            }

            // Converte a resposta JSON em uma lista de objetos JavaScript.
            return response.json();
        })
        .then(animais => {
            // Localiza no HTML a área onde os animais serão exibidos.
            const lista = document.getElementById("listaAnimais");

            // Limpa a lista antes de preenchê-la novamente, evitando duplicações.
            lista.innerHTML = "";

            // Percorre todos os animais recebidos da API.
            animais.forEach(animal => {
                // Exibe somente os animais associados ao código do cliente logado.
                if (animal.codigoCliente === codigoCliente) {
                    // Cria um novo elemento de parágrafo no HTML.
                    const paragrafo = document.createElement("p");

                    // Define o texto do parágrafo com o nome e a espécie do animal.
                    paragrafo.textContent = animal.nome + " - " + animal.especie;

                    // Adiciona o parágrafo à lista de animais na página.
                    lista.appendChild(paragrafo);
                }
            });
        })

        .catch(erro => {
            // Registra no console possíveis erros durante a consulta.
            console.error(erro);
        });
    }

    // Carrega a lista de animais assim que a página é aberta.
    listarAnimais();
