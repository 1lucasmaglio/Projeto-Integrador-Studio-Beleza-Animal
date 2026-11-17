# 🐾 Studio de Beleza Animal

Sistema de gerenciamento e agendamento para um estabelecimento de banho e tosa, desenvolvido em **Java** como projeto acadêmico do curso de Análise e Desenvolvimento de Sistemas.

---

## Sobre o projeto

O Studio de Beleza Animal é um sistema criado para facilitar o gerenciamento de um estabelecimento de banho e tosa.

O sistema permite cadastrar clientes, animais, serviços e realizar agendamentos. Também conta com uma interface web para os clientes e uma interface desktop em Java Swing para a administração.

## Como funciona?

O projeto foi organizado em camadas:

- `model` — representa os objetos do sistema, como Cliente, Animal, Serviço, Agendamento e Administrador.
- `dao` — realiza a leitura e escrita dos dados nos arquivos CSV.
- `service` — contém as operações e regras de negócio.
- `view` — contém a interface administrativa desenvolvida com Java Swing.
- `api` — disponibiliza as funcionalidades do sistema por meio de uma API HTTP utilizando Javalin.
- `frontend` — contém as páginas web desenvolvidas com HTML, CSS e JavaScript.

O frontend se comunica com a API para cadastrar e consultar informações. Atualmente, os dados são armazenados em arquivos CSV.

---

## Funcionalidades

**Área do cliente (web)**

- Cadastro de clientes
- Login de clientes
- Cadastro de animais vinculados ao cliente
- Visualização dos animais cadastrados
- Consulta dos serviços disponíveis
- Realização de agendamentos
- Visualização dos agendamentos do cliente
- Exibição do preço do serviço conforme o porte do animal

**Área administrativa (desktop)**

- Cadastro e gerenciamento de administradores
- Gerenciamento de clientes e animais
- Cadastro e gerenciamento de serviços
- Definição de preços dos serviços por porte (P, M e G)
- Gerenciamento de agendamentos

**Backend**

- API HTTP desenvolvida com Javalin
- Comunicação entre frontend e backend
- Organização em camadas
- Persistência de dados em arquivos CSV

---

## Arquitetura

```text
Frontend (HTML, CSS e JavaScript)
              |
              v
        API (Javalin)
              |
              v
           SERVICE
              |
              v
             DAO
              |
              v
        Arquivos CSV


Java Swing (Administração)
              |
              v
           SERVICE
              |
              v
             DAO
              |
              v
        Arquivos CSV
```

---

## Tecnologias utilizadas

- **Java 21** — desenvolvimento do backend
- **Java Swing** — interface administrativa desktop
- **Javalin** — API HTTP
- **HTML** — estrutura das páginas web
- **CSS** — estilização das páginas
- **JavaScript** — interação com a API
- **Maven** — gerenciamento de dependências
- **CSV** — armazenamento atual dos dados
- **Git e GitHub** — versionamento e colaboração

---

## Estrutura do projeto

```text
StudioDeBelezaAnimalJava/
├── dados/
│   ├── administradores.csv
│   ├── agendamentos.csv
│   ├── animais.csv
│   ├── clientes.csv
│   └── servicos.csv
│
├── frontend/
│   ├── index.html
│   ├── login.html
│   ├── login.js
│   ├── cadastro.html
│   ├── cadastro.js
│   ├── cliente.html
│   ├── animais.html
│   ├── animais.js
│   ├── agendamentos.html
│   ├── agendamentos.js
│   └── style.css
│
├── src/main/java/
│   ├── api/
│   ├── dao/
│   ├── model/
│   ├── service/
│   ├── view/
│   └── Main.java
│
├── pom.xml
└── README.md
```

---

## Como executar

### 1. Interface administrativa

Execute o arquivo:

```text
src/main/java/Main.java
```

O programa abrirá a interface gráfica desenvolvida com Java Swing.

### 2. API

Execute o arquivo:

```text
src/main/java/api/api.java
```

A API será iniciada em:

```text
http://localhost:7070
```

Algumas das rotas utilizadas:

```text
/clientes
/animais
/servicos
/agendamentos
/administradores
```

### 3. Interface web

Com a API em execução:

1. Abra o projeto no Visual Studio Code.
2. Instale a extensão Live Server, caso ainda não tenha.
3. Abra o arquivo `frontend/index.html`.
4. Clique em **Open with Live Server**.

A página inicial será aberta no navegador e permitirá acessar as funcionalidades do cliente.

---

## Próximas etapas

- Migrar o armazenamento de arquivos CSV para um banco de dados.
- Adicionar a opção de Leva e Traz aos agendamentos.
- Impedir agendamentos duplicados no mesmo dia e horário.
- Bloquear agendamentos nos dias em que o estabelecimento não funciona.
- Melhorar a interface web e realizar testes gerais.

## Melhorias futuras

- Definir a duração dos serviços conforme o porte do animal.
- Evitar conflitos de horários considerando a duração dos serviços.
- Adicionar endereço e CEP ao cadastro dos clientes.
- Calcular o valor do Leva e Traz conforme a distância.
- Adicionar opções de pagamento, como dinheiro e Pix.
- Integrar pagamentos Pix e acompanhar os pagamentos dos agendamentos.

---

## Status

🚧 **Em desenvolvimento**

O projeto já conta com interface administrativa desktop, páginas web para clientes, API HTTP e armazenamento em CSV.

As próximas etapas incluem a migração para banco de dados, melhorias nos agendamentos e testes de integração.

---

## Integrantes

- Lucas Maglio Chiabai
- Thiago Alexandre Marques Basílio
- Thiago Santos de Almeida
- Yann Sahmuel Escobar de Campos
