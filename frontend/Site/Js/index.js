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
botaoPerfil.addEventListener("click", function () {
  
  evento.stopPropagation();
  
  menuPerfil.hidden = !menuPerfil.hidden;

  botaoPerfil.setAttribute("aria-expanded", String(!menuPerfil.hidden));
});

// Fecha o menu ao clicar fora dele.
document.addEventListener("click", function (evento) {
  if (!evento.target.closest(".perfil-container")) {
    menuPerfil.hidden = true;
    botaoPerfil.setAttribute("aria-expanded", "false");
  }
});

// Fecha o menu ao pressionar Escape.
document.addEventListener("keydown", function (evento) {
  if (evento.key === "Escape") {
    menuPerfil.hidden = true;
    botaoPerfil.setAttribute("aria-expanded", "false");
  }
});

// Encerra a sessão do cliente.
botaoSair.addEventListener("click", function () {
  sessionStorage.removeItem("codigoCliente");
  window.location.href = "index.html";
});
// ==========================

// Efeito de rotação dos cartões de serviço.

// ==========================
document.querySelectorAll(".cartao-servico").forEach(function (card) {
  // Seleciona todos os elementos com a classe "cartao-servico" e aplica a função 'card' para eles...
  card.addEventListener("pointermove", function (e) {
    // Adiciona um escutador de evento, que é o ponteiro do mouse, e quando ele se move faz...
    if (e.pointerType !== "mouse") return; // ignora o touchscreen, só faz efeito com mouse

    const r = card.getBoundingClientRect();
    // cria uma constante r que recebe as dimensões do card, ou seja, a posição e tamanho do card na tela

    const x = (e.clientX - r.left) / r.width - 0.5;
    // Depois disso cria a constante x ela vai recber a posição do ponteiro do mouse
    // em relação ao card; depois ela faz uma divisão pelo tamanho do card e subtrai 0.5 para centralizar
    // o efeito, ou seja, quando o mouse estiver no centro do card, x será 0.

    // Mesma coisa para o eixo y, mas invertido, porque o eixo y é invertido na tela.
    const y = (e.clientY - r.top) / r.height - 0.5;

    card.style.setProperty("--ry", x * 10 + "deg"); // até 10° para os lados
    card.style.setProperty("--rx", -y * 10 + "deg");
  });

  card.addEventListener("pointerleave", function () {
    card.style.removeProperty("--rx");
    card.style.removeProperty("--ry");
  });
});
