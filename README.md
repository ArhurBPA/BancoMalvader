# Visão Geral
O projeto Banco Malvader é uma aplicação de gerenciamento bancário que permite a clientes e funcionários realizar operações bancárias como abertura e encerramento de contas, consultas de dados, geração de relatórios e transações financeiras. A aplicação é desenvolvida em Java utilizando a biblioteca Swing para a interface gráfica e JDBC para a interação com o banco de dados.

# Tecnologias Utilizadas
- Java: Linguagem de programação principal.
- Swing: Biblioteca para construção da interface gráfica.
- JDBC: API para conexão com o banco de dados.
- MySQL: Sistema de gerenciamento de banco de dados.
- OpenCSV: Biblioteca para exportação de dados em formato CSV.

# Diagramas
![Diagrama de Sequencia](/diagramas/sequencia.jpeg "Diagrama de Sequencia")

![DiagramaClasseMalavader](https://github.com/user-attachments/assets/6297a2db-a1d2-4d15-ba0d-8c430f5d2856)

  
# Estrutura do Projeto
A estrutura do projeto é organizada da seguinte forma:
```
src/
│
├── controller/          # Controladores da aplicação
│   ├── BancoController.java
│   ├── FuncionarioController.java
│   └── UsuarioController.java
│
├── dao/                  # Data Access Objects (DAOs) para interação com o banco de dados
│   ├── BancoDAO.java
│   ├── ClienteDAO.java
│   ├── FuncionarioDAO.java
│   └── ConnectionFactory.java
│
├── model/               # Classes de modelo representando entidades do sistema
│   ├── Cliente.java
│   ├── Conta.java
│   ├── Transacao.java
│   ├── Usuario.java
│   └── Endereco.java
│
├── util/                # Utilitários e classes auxiliares
│   ├── DBUtils.java
│   ├── CSVExporter.java
│   └── Relatorio.java
│
├── view/                 # Classes de visualização (interface gráfica)
│   ├── MainView.java
│   ├── ClienteView.java
│   ├── FuncionarioView.java
│   └── CadastroView.java
│
└── util/                # Classe principal para inicialização
    └── BancoMalvader.java
```

# Funcionalidades
### Login de Usuário: Clientes e funcionários podem se autenticar na aplicação.
### Gerenciamento de Contas:
- Abertura de contas (Corrente e Poupança).
- Encerramento de contas.
- Consulta de dados de contas.
### Transações Financeiras:
- Realização de depósitos e saques.
- Geração de extratos de transações.
- Cadastro de Funcionários: Funcionários podem ser cadastrados com cargos específicos.
### Geração de Relatórios: Relatórios de transações e dados do usuário podem ser gerados e exportados em formato CSV.
