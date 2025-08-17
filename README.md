# ForumHub API

## Índice

* [Descrição do Projeto](https://www.google.com/search?q=%23descri%C3%A7%C3%A3o-do-projeto)
* [Funcionalidades](https://www.google.com/search?q=%23funcionalidades)
* [Tecnologias Utilizadas](https://www.google.com/search?q=%23tecnologias-utilizadas)
* [Arquitetura](https://www.google.com/search?q=%23arquitetura)
* [Pré-requisitos](https://www.google.com/search?q=%23pr%C3%A9-requisitos)
* [Como Executar o Projeto](https://www.google.com/search?q=%23como-executar-o-projeto)
* [Documentação da API](https://www.google.com/search?q=%23documenta%C3%A7%C3%A3o-da-api)
* [Endpoints da API](https://www.google.com/search?q=%23endpoints-da-api)
* [Autor](https://www.google.com/search?q=%23autor)
* [Licença](https://www.google.com/search?q=%23licen%C3%A7a)

## Descrição do Projeto

O ForumHub é uma API REST desenvolvida como parte do Challenge de Back-end da Alura. A aplicação simula um fórum de discussão, permitindo que usuários criem, leiam, atualizem e deletem tópicos e respostas. O sistema conta com um mecanismo de autenticação e autorização seguro via Tokens JWT para proteger as rotas.

## Funcionalidades

- ✅ **Autenticação:** Sistema de login com geração de token JWT.
- ✅ **CRUD de Usuários:**
    - Cadastro de novos usuários.
    - Listagem e detalhamento de usuários.
    - Atualização de informações de usuários.
    - Exclusão lógica de usuários (soft delete).
- ✅ **CRUD de Tópicos:**
    - Criação de novos tópicos associados a um autor.
    - Listagem de todos os tópicos ativos com paginação.
    - Detalhamento de um tópico específico.
    - Atualização de um tópico (apenas pelo autor).
    - Exclusão lógica de um tópico.
- ✅ **Funcionalidades de Respostas:**
    - Criação de respostas para um tópico existente.
    - Listagem de todas as respostas de um tópico.
    - Atualização e exclusão lógica de respostas (apenas pelo autor).
    - Marcar uma resposta como a solução do tópico (apenas pelo autor do tópico).
- ✅ **Validações:** Regras de negócio implementadas na camada de serviço para garantir a integridade dos dados (ex: não permitir tópicos duplicados, validação de permissões, etc.).

## Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3**
- **Spring Web:** Para a criação de endpoints REST.
- **Spring Security:** Para a implementação de autenticação e autorização stateless com JWT.
- **Spring Data JPA:** Para a persistência de dados.
- **Hibernate:** Como implementação da JPA.
- **PostgreSQL:** Banco de dados relacional.
- **Flyway:** Para o versionamento e controle de migrações do banco de dados.
- **Maven:** Para o gerenciamento de dependências e build do projeto.
- **Lombok:** Para a redução de código boilerplate.
- **java-jwt:** Biblioteca para a geração e validação de tokens JWT.
- **Springdoc-openapi (Swagger):** Para a documentação interativa da API.

## Arquitetura

A aplicação foi desenvolvida seguindo os princípios da **arquitetura em camadas** (Layered Architecture), separando as responsabilidades em:

- **Controllers:** Camada de apresentação, responsável por receber as requisições HTTP e retornar as respostas.
- **Services:** Camada de negócio, onde toda a lógica, validações e regras da aplicação são implementadas.
- **Repositories:** Camada de acesso a dados, responsável pela comunicação com o banco de dados.
- **Models/Entities:** Representação das tabelas do banco de dados como objetos Java.
- **DTOs (Data Transfer Objects):** Objetos para transferir dados entre as camadas e o cliente, garantindo que o modelo de domínio não seja exposto diretamente.

## Pré-requisitos

Antes de começar, você precisará ter instalado em sua máquina:

- JDK 17 ou superior
- Maven 3.8 ou superior
- PostgreSQL 13 ou superior
- Uma IDE de sua preferência (ex: IntelliJ, VSCode)

## Como Executar o Projeto

1.  **Clone o repositório:**

    ```bash
    git clone https://github.com/Andrevictor20/Challenge-ForumHub.git
    cd Challenge-ForumHub
    ```

2.  **Configure o Banco de Dados:**

    - Crie um banco de dados no PostgreSQL (ex: `forumhub_db`).

3.  **Configure as Variáveis de Ambiente:**

    - No arquivo `src/main/resources/application.properties`, configure as variáveis para acesso ao seu banco de dados e o segredo do token JWT.

    <!-- end list -->

    ```properties
    # Configuração do Banco de Dados PostgreSQL
    spring.datasource.url=jdbc:postgresql://localhost/forumhub_db
    spring.datasource.username=seu_usuario_postgres
    spring.datasource.password=sua_senha_postgres

    # Segredo para a geração do token JWT
    api.security.token.secret=seu_segredo_super_secreto_aqui
    ```

4.  **Construa o projeto (Build):**
    Em vez de executar diretamente, gere o build da aplicação. Isso compilará o código e empacotará tudo em um arquivo `.jar`.


5.  **Execute a aplicação:**
    Após o build ser concluído com sucesso, execute o arquivo `.jar` gerado.

    ```bash
    java -jar target/Challenge-ForumHub-0.0.1-SNAPSHOT.jar
    ```

6.  A API estará disponível em `http://localhost:5040`.

## Documentação da API

A documentação de todos os endpoints foi gerada com Springdoc-openapi e está disponível em uma interface interativa do Swagger.

Após iniciar a aplicação, acesse:
➡️ **[http://localhost:5040/swagger-ui.html](https://www.google.com/search?q=http://localhost:5040/swagger-ui.html)**

Lá você poderá ver todos os endpoints, seus parâmetros, DTOs e respostas, além de poder testar a API diretamente pelo navegador.

## Endpoints da API

A API está organizada em torno dos seguintes recursos:

#### Autenticação

| Verbo | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/login` | Efetua o login de um usuário e retorna um token JWT. |

#### Tópicos

| Verbo | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/topicos` | Cria um novo tópico (requer autenticação). |
| `GET` | `/topicos` | Lista todos os tópicos ativos de forma paginada. |
| `GET` | `/topicos/{id}` | Detalha um tópico específico pelo ID. |
| `PUT` | `/topicos/{id}` | Atualiza um tópico (apenas o autor). |
| `DELETE` | `/topicos/{id}` | Exclui (logicamente) um tópico (apenas o autor). |
| `GET` | `/topicos/{id}/respostas` | Lista todas as respostas de um tópico específico. |

#### Usuários

| Verbo | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/usuarios` | Cadastra um novo usuário. |
| `GET` | `/usuarios` | Lista todos os usuários ativos de forma paginada. |
| `GET` | `/usuarios/{id}` | Detalha um usuário específico pelo ID. |
| `PUT` | `/usuarios/{id}` | Atualiza um usuário (requer permissão). |
| `DELETE` | `/usuarios/{id}` | Exclui (logicamente) um usuário (requer permissão). |

#### Respostas

| Verbo | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/respostas` | Cria uma nova resposta para um tópico (requer autenticação). |
| `PUT` | `/respostas/{id}` | Atualiza uma resposta (apenas o autor). |
| `DELETE` | `/respostas/{id}` | Exclui (logicamente) uma resposta (apenas o autor). |
| `POST` | `/respostas/{id}/solucao` | Marca uma resposta como a solução do tópico (apenas o autor do tópico). |

