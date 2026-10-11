const formulario = document.getElementById("formLogin");

formulario.addEventListener("submit", function(event) {
    event.preventDefault();

    const email = document.getElementById("email").value;
    const senha = document.getElementById("senha").value;

    const login = {
        email: email,
        senha: senha
    };

    fetch("http://localhost:7070/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(login)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("E-mail ou senha incorretos.");
        }

        return response.json();
    })

    .then(dados => {
        sessionStorage.setItem("codigoCliente", dados.codigo);
        window.location.href = "cliente.html";
    })

    .catch(erro => {
        console.error(erro);

        const mensagem = document.getElementById("mensagemLogin");

        mensagem.style.color = "red";
        mensagem.textContent = "E-mail ou senha incorretos.";
    });

});