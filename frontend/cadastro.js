
/* Busca o formulário de cadastro pelo ID definido no HTML. */
const formulario = document.getElementById("formCadastro");

/* Executa esta função quando o cliente envia o formulário de cadastro. */
formulario.addEventListener("submit", function(event) {

    // Impede que o formulário recarregue a página automaticamente.
    event.preventDefault();

    // Obtém os dados preenchidos pelo cliente nos campos do formulário.
    const nome = document.getElementById("nome").value;
    const telefone = document.getElementById("telefone").value;
    const email = document.getElementById("email").value;
    const senha = document.getElementById("senha").value;

    // Organiza os dados em um objeto JavaScript chamado cliente.
    const cliente = {
        nome: nome,
        telefone: telefone,
        email: email,
        senha: senha
    };  

    // Mostra alguns dados no console para auxiliar durante os testes.
    console.log(nome, telefone, email);

    // Envia os dados do cliente para a API Java realizar o cadastro.
    fetch("http://localhost:7070/clientes", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        // Converte o objeto cliente para JSON antes de enviá-lo.
        body: JSON.stringify(cliente)
    })
    .then(response => {
        // Verifica se o servidor retornou um erro ao tentar cadastrar.
        if (!response.ok) {
            throw new Error("Erro ao cadastrar cliente.");
        }
    })

    .then(() => {
        // Se o cadastro foi realizado com sucesso, abre a página de login.
        window.location.href = "login.html";
    })

    .catch(erro => {
        // Mostra o erro no console para facilitar a identificação do problema.
        console.error(erro);

        // Informa ao cliente que não foi possível concluir o cadastro.
        alert("Não foi possível cadastrar o cliente.");
    });

});
