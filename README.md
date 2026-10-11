# 🐾 Studio de Beleza Animal

Sistema de agendamento para um estabelecimento de banho e tosa, desenvolvido em **Java** como projeto acadêmico de Análise e Desenvolvimento de Sistemas.

---

## Sobre o projeto

**O que é?**

Um sistema para auxiliar no gerenciamento de um estabelecimento de banho e tosa. A aplicação permite cadastrar clientes, seus animais, os serviços oferecidos e realizar agendamentos.

**Como funciona?**

O projeto foi separado em camadas com responsabilidades diferentes:

- `model` — representa os objetos do sistema, como Cliente, Animal, Serviço, Agendamento e Administrador.
- `dao` — realiza a leitura e escrita dos dados nos arquivos CSV.
- `service` — concentra as operações e regras do sistema.
- `view` — contém a interface gráfica feita com Java Swing.
- `api` — disponibiliza os dados através de uma API HTTP utilizando Javalin.

Os dados são armazenados em arquivos **CSV**, permitindo que continuem disponíveis mesmo depois que o programa é encerrado.

---

## Funcionalidades

- Cadastro e gerenciamento de administradores
- Cadastro de clientes
- Cadastro de animais vinculados aos clientes
- Cadastro de serviços
- Criação e gerenciamento de agendamentos
- Controle de conflito de horários
- Persistência dos dados em CSV
- Interface gráfica desktop
- API para consulta dos dados

---

## Arquitetura

```text
                ┌─────────────────┐
                │      VIEW       │
                │  Java Swing     │
                └────────┬────────┘
                         │
                         ▼
                ┌─────────────────┐
                │     SERVICE     │
                │ Regras do       │
                │ sistema         │
                └────────┬────────┘
                         │
                         ▼
                ┌─────────────────┐
                │       DAO       │
                │ Persistência    │
                │ em CSV          │
                └────────┬────────┘
                         │
                         ▼
                ┌─────────────────┐
                │     dados/      │
                │ Arquivos CSV    │
                └─────────────────┘

API (Javalin) ──────► SERVICE
```

---

## Tecnologias utilizadas

- **Java**
- **Java Swing** — interface gráfica
- **Javalin** — API HTTP
- **Maven** — gerenciamento de dependências
- **CSV** — persistência dos dados

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

### Interface gráfica

Execute:

```text
src/main/java/Main.java
```

O `Main` inicia a `TelaPrincipal`, que contém a interface gráfica do sistema.

### API

Execute:

```text
src/main/java/api/api.java
```

A API será iniciada em:

```text
http://localhost:7070
```

Principais rotas:

```text
/clientes
/animais
/servicos
/agendamentos
/administradores
```

---

## Status

🚧 **Em desenvolvimento**

A versão atual possui a estrutura principal do sistema, interface desktop, persistência em CSV e API. O projeto continuará sendo atualizado conforme novos requisitos forem implementados.

---

## Integrantes

- Lucas Maglio Chiabai
- Thiago Alexandre Marques Basílio
- Thiago Santos de Almeida
- Yann Sahmuel Escobar de Campos
