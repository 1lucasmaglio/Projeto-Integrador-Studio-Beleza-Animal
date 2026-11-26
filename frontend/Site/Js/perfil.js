// Seleciona os elementos da página.
const botaoAlterarSenha = document.getElementById("botaoAlterarSenha");
const formSenha = document.getElementById("formSenha");
const formPerfil = document.getElementById("formPerfil");

// Exibe ou esconde o formulário de alteração de senha.
botaoAlterarSenha.addEventListener("click", function () {
  formSenha.hidden = !formSenha.hidden;
});

// Impede o envio enquanto a integração com o backend não está pronta.
formPerfil.addEventListener("submit", function (evento) {
  evento.preventDefault();

  document.getElementById("mensagemPerfil").textContent =
    "A atualização dos dados ainda não está disponível.";
});

// Impede a alteração de senha sem validação no backend.
formSenha.addEventListener("submit", function (evento) {
  evento.preventDefault();

  const novaSenha = document.getElementById("novaSenha").value;
  const confirmarSenha = document.getElementById("confirmarSenha").value;

  if (novaSenha !== confirmarSenha) {
    document.getElementById("mensagemSenha").textContent =
      "As senhas não coincidem.";
    return;
  }

  document.getElementById("mensagemSenha").textContent =
    "A alteração de senha ainda não está disponível.";
});
