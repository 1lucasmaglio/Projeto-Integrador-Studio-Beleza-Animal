        // Seleciona os elementos do menu.
        const botaoPerfil = document.getElementById("botaoPerfil");
        const menuPerfil = document.getElementById("menuPerfil");
        const perfilContainer = document.getElementById("perfilContainer");

        const linkEntrar = document.getElementById("linkEntrar");
        const linkCadastro = document.getElementById("linkCadastro");
        const botaoSair = document.getElementById("botaoSair");

        const botaoAgendar = document.getElementById("botaoAgendar");
        const botaoComecar = document.getElementById("botaoComecar");
        const textoChamadaFinal = document.getElementById("textoChamadaFinal");

        // Recupera o código salvo pelo login.js.
        const codigoCliente = sessionStorage.getItem("codigoCliente");

        // Ajusta a página conforme a sessão do cliente.
        if (codigoCliente) {
            linkEntrar.hidden = true;
            linkCadastro.hidden = true;
            perfilContainer.hidden = false;

            // Cliente logado pode acessar diretamente os agendamentos.
            botaoAgendar.href = "agendamentos.html";

            // Atualiza a chamada final para o cliente logado.
            textoChamadaFinal.textContent =
                "Escolha um serviço e agende um horário para seu pet.";

            botaoComecar.href = "agendamentos.html";
            botaoComecar.textContent = "Agendar Agora";
        } else {
            linkEntrar.hidden = false;
            linkCadastro.hidden = false;
            perfilContainer.hidden = true;
        }

        // Abre ou fecha o menu ao clicar no botão.
        botaoPerfil.addEventListener("click", function() {
            menuPerfil.hidden = !menuPerfil.hidden;

            botaoPerfil.setAttribute(
                "aria-expanded",
                String(!menuPerfil.hidden)
            );
        });

        // Fecha o menu ao clicar fora dele.
        document.addEventListener("click", function(evento) {
            if (!evento.target.closest(".perfil-container")) {
                menuPerfil.hidden = true;
                botaoPerfil.setAttribute("aria-expanded", "false");
            }
        });

        // Fecha o menu ao pressionar Escape.
        document.addEventListener("keydown", function(evento) {
            if (evento.key === "Escape") {
                menuPerfil.hidden = true;
                botaoPerfil.setAttribute("aria-expanded", "false");
            }
        });

        // Encerra a sessão do cliente.
        botaoSair.addEventListener("click", function() {
            sessionStorage.removeItem("codigoCliente");
            window.location.href = "index.html";
        });
