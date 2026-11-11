
/* Busca o formulário de login pelo ID definido no HTML. */
const formulario = document.getElementById("formLogin");

/* Executa esta função quando o cliente tenta entrar na conta. */
formulario.addEventListener("submit", function(event) {
    // Impede que o formulário recarregue a página automaticamente.
    event.preventDefault();

    // Obtém o e-mail e a senha digitados pelo cliente.
    const email = document.getElementById("email").value;
    const senha = document.getElementById("senha").value;

    // Organiza os dados do login em um objeto para enviar ao backend.
    const login = {
        email: email,
        senha: senha
    };

    // Envia os dados para a API Java verificar o login.
    fetch("http://localhost:7070/Client/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        // Converte o objeto login para o formato JSON.
        body: JSON.stringify(login)
    })
    .then(response => {
        // Verifica se o servidor recusou o login.
        if (!response.ok) {
            throw new Error("E-mail ou senha incorretos.");
        }

        // Converte a resposta do servidor de JSON para um objeto JavaScript.
        return response.json();
    })

    .then(dados => {
        // Guarda o código do cliente durante a sessão do navegador.
        sessionStorage.setItem("codigoCliente", dados.codigo);

        // Redireciona o cliente para sua área após o login.
        window.location.href = "cliente.html";
    })

    .catch(erro => {
        // Registra o erro no console para facilitar a identificação de problemas.
        console.error(erro);

        // Busca o elemento HTML responsável por mostrar a mensagem de erro.
        const mensagem = document.getElementById("mensagemLogin");

        // Exibe a mensagem de erro em vermelho para o cliente.
        mensagem.style.color = "red";
        mensagem.textContent = "E-mail ou senha incorretos.";
    });

});
