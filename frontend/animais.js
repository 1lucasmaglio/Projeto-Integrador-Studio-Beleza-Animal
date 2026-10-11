const formulario = document.getElementById("formAnimal");

formulario.addEventListener("submit", function(event) {
    event.preventDefault();

    const nome = document.getElementById("nome").value;
    const especie = document.getElementById("especie").value;
    const raca = document.getElementById("raca").value;
    const porte = document.getElementById("porte").value;

    const codigoCliente = Number(sessionStorage.getItem("codigoCliente"));

    const animal = {
        nome: nome,
        especie: especie,
        raca: raca,
        porte: porte,   
        codigoCliente: codigoCliente
    };

    console.log(animal);

    fetch("http://localhost:7070/animais", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(animal)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Erro ao cadastrar animal.");
        }

        return response.json();
    })

    .then(dados => {
        formulario.reset();
        console.log(dados);
        listarAnimais();
    })

    .catch(erro => {
        console.error(erro);
        alert("Não foi possível cadastrar o animal.");
    });

});

    function listarAnimais() {
        const codigoCliente = Number(sessionStorage.getItem("codigoCliente"));

        fetch("http://localhost:7070/animais")
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro ao buscar animais.");
            }

            return response.json();
        })
        .then(animais => {
            const lista = document.getElementById("listaAnimais");

            lista.innerHTML = "";

            animais.forEach(animal => {
                if (animal.codigoCliente === codigoCliente) {
                    const paragrafo = document.createElement("p");

                    paragrafo.textContent = animal.nome + " - " + animal.especie;

                    lista.appendChild(paragrafo);
                }
            });
        })

        .catch(erro => {
            console.error(erro);
        });
    }

    listarAnimais();