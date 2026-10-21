<details>
<summary>🇧🇷 PT-BR</summary>

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

```
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

```
StudioDeBelezaAnimalJava/
├── dados
│   ├── administradores.csv
│   ├── agendamentos.csv
│   ├── animais.csv
│   ├── clientes.csv
│   ├── servicos.csv
│   └── servicos_backup.csv
├── frontend
│   └── Landing
│       ├── Css
│       │   ├── global
│       │   │   └── style.css
│       │   └── pages
│       │       ├── agendamentos
│       │       │   └── agdstyle.css
│       │       ├── animais
│       │       │   └── animaistyle.css
│       │       ├── cadastro
│       │       │   └── Cadstyle.css
│       │       ├── cliente
│       │       │   └── clientestyle.css
│       │       └── login
│       │           └── Loginstyle.css
│       ├── Js
│       ├── agendamentos.html
│       ├── agendamentos.js
│       ├── animais.html
│       ├── animais.js
│       ├── cadastro.html
│       ├── cadastro.js
│       ├── cliente.html
│       ├── index.html
│       ├── login.html
│       └── login.js
├── pom.xml
├── src
│   └── main
│       └── java
│           ├── Main.java
│           ├── api
│           │   └── api.java
│           ├── dao
│           │   ├── AdministradorCSV.java
│           │   ├── AgendamentoCSV.java
│           │   ├── AnimalCSV.java
│           │   ├── ClienteCSV.java
│           │   └── ServicoCSV.java
│           ├── model
│           │   ├── Administrador.java
│           │   ├── Agendamento.java
│           │   ├── Animal.java
│           │   ├── Cliente.java
│           │   └── Servico.java
│           ├── service
│           │   ├── AdministradorService.java
│           │   ├── AgendamentoService.java
│           │   ├── AnimalService.java
│           │   ├── ClienteService.java
│           │   └── ServicoService.java
│           └── view
│               └── TelaPrincipal.java
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
</details>

## 🐾 Animal Beauty Studio

A management and appointment system for a pet bathing and grooming business, developed in **Java** as an academic project for the Software Development and Analysis program.

---

## About the project

Animal Beauty Studio is a system created to make it easier to manage a pet bathing and grooming business.

The system allows you to register clients, pets, services, and appointments. It also includes a web interface for customers and a Java Swing desktop interface for administration.

## How it works

The project is organized into layers:

- `model` — represents the system’s objects, such as Client, Pet, Service, Appointment, and Administrator.
- `dao` — handles reading and writing data to CSV files.
- `service` — contains business operations and rules.
- `view` — contains the administrative interface developed with Java Swing.
- `api` — exposes the system’s functionality through an HTTP API using Javalin.
- `frontend` — contains the web pages developed with HTML, CSS, and JavaScript.

The frontend communicates with the API to register and query information. Currently, data is stored in CSV files.

---

## Features

**Customer area (web)**

- Client registration
- Client login
- Pet registration linked to the client
- Viewing registered pets
- Browsing available services
- Booking appointments
- Viewing the client’s appointments
- Displaying service prices according to the pet’s size

**Administrative area (desktop)**

- Administrator registration and management
- Client and pet management
- Service registration and management
- Service pricing by size (S, M, and L)
- Appointment management

**Backend**

- HTTP API developed with Javalin
- Frontend-backend communication
- Layered architecture
- Data persistence in CSV files

---

## Architecture

```text
Frontend (HTML, CSS, and JavaScript)
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
        CSV Files


Java Swing (Administration)
              |
              v
           SERVICE
              |
              v
             DAO
              |
              v
        CSV Files
```
## Technologies used

- **Java 21**: Backend development
- **Java Swing**: Desktop administration interface
- **Javalin**: HTTP API
- **HTML**: Page structure
- **CSS**: Styling
- **JavaScript**: API interaction
- **Maven**: Dependency management
- **CSV**: Current data storage (in future change to mySQL)
- **Git and GitHub**: Version control and collaboration

## Project Structure
```
StudioDeBelezaAnimalJava/
├── dados
│   ├── administradores.csv
│   ├── agendamentos.csv
│   ├── animais.csv
│   ├── clientes.csv
│   ├── servicos.csv
│   └── servicos\_backup.csv
├── frontend
│   └── Landing
│       ├── Css
│       │   ├── global
│       │   │   └── style.css
│       │   └── pages
│       │       ├── agendamentos
│       │       │   └── agdstyle.css
│       │       ├── animais
│       │       │   └── animaistyle.css
│       │       ├── cadastro
│       │       │   └── Cadstyle.css
│       │       ├── cliente
│       │       │   └── clientestyle.css
│       │       └── login
│       │           └── Loginstyle.css
│       ├── Js
│       ├── agendamentos.html
│       ├── agendamentos.js
│       ├── animais.html
│       ├── animais.js
│       ├── cadastro.html
│       ├── cadastro.js
│       ├── cliente.html
│       ├── index.html
│       ├── login.html
│       └── login.js
├── pom.xml
├── src
│   └── main
│       └── java
│           ├── Main.java
│           ├── api
│           │   └── api.java
│           ├── dao
│           │   ├── AdministradorCSV.java
│           │   ├── AgendamentoCSV.java
│           │   ├── AnimalCSV.java
│           │   ├── ClienteCSV.java
│           │   └── ServicoCSV.java
│           ├── model
│           │   ├── Administrador.java
│           │   ├── Agendamento.java
│           │   ├── Animal.java
│           │   ├── Cliente.java
│           │   └── Servico.java
│           ├── service
│           │   ├── AdministradorService.java
│           │   ├── AgendamentoService.java
│           │   ├── AnimalService.java
│           │   ├── ClienteService.java
│           │   └── ServicoService.java
│           └── view
│               └── TelaPrincipal.java
└── README.md
```
## How to run
### 1. Administrative interface
Run:
```
src/main/java/Main.java
```
The program will open the graphical interface developed with Java Swing.
### 2. API
Run:
```
src/main/java/api/api.java
```

The API will start at:
http://localhost:7070

Some of the available routes:
```
/clientes
/animais
/servicos
/agendamentos
/administradores
```
### 3. Web interface

With the API running:

    Open the project in Visual Studio Code.
    Install the Live Server extension if you do not already have it.
    Open the frontend/index.html file.
    Click Open with Live Server.

The home page will open in the browser and allow access to the customer features.

## Status

🚧 In development

The project already includes a desktop administration interface, customer web pages, an HTTP API, and CSV-based storage.

Upcoming work includes database migration, scheduling improvements, and integration testing.

## Autors
<table>
  <tr>
      <td align="center">
            <a href="https://github.com/1lucasmaglio" title="Sobre">
                  <img src="https://avatars.githubusercontent.com/u/266368513?v=4" width="100px;"   alt="Foto do Lucas Maglio no GitHub"/><br>
                  <sub>
                        <b>Lucas Maglio</b>
                  </sub>
            </a>
     </td>
     <td align="center">
            <a href="https://github.com/YannEscobar" title="Sobre">
        <img src="https://avatars.githubusercontent.com/u/240530528?v=4" width="100px;" alt="Foto do Yann no GitHub"/><br>
        <sub>
          <b>Yann Escobar</b>
        </sub>
            </a>
      </td> 
      <td align="center">
            <a href="" title="Sobre">
                  <img src="https://avatars.githubusercontent.com/u/288315951?v=4" width="100px" alt="Foto do Thiago Almeida no GitHub"><br>
                  <sub>
                        <b>Thiago Almeida</b>
                  </sub>
            </a>
      </td>
      <td align="center">
            <a href="" title="Sobre">
                  <img src="https://avatars.githubusercontent.com/u/288312813?v=4" width="100px" alt="Foto do Thiago Almeida no GitHub"><br>
                  <sub>
                        <b>Thiago Basilio</b>
                  </sub>
            </a>
      </td>
  </tr>
</table>
