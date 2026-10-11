const formulario = document.getElementById("formCadastro");

formulario.addEventListener("submit", function(event) {

    event.preventDefault();

    const nome = document.getElementById("nome").value;
    const telefone = document.getElementById("telefone").value;
    const email = document.getElementById("email").value;
    const senha = document.getElementById("senha").value;

    const cliente = {
        nome: nome,
        telefone: telefone,
        email: email,
        senha: senha
    };

    console.log(nome, telefone, email);

    fetch("http://localhost:7070/clientes", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(cliente)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Erro ao cadastrar cliente.");
        }
    })

    .then(() => {
        window.location.href = "login.html";
    })

    .catch(erro => {
        console.error(erro);
        alert("Não foi possível cadastrar o cliente.");
    });

});