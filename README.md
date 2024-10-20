# BuscaPet - API

API do trabalho da disciplina Laboratório de Engenharia de Software.

## Descrição do Projeto

O software “BuscaPet” consiste em uma ferramenta para encontrar animais perdidos, além de ser também uma plataforma que possibilita a divulgação de animais para adoção.

## Pré-requisitos

- Java Version = 17.
- Banco de Dados = MySQL.
- IDE (Foi utilizado o IntelliJ na versão Ultimate).

## Instalação

**1. Clone o repositório**

<code> git clone https://github.com/BuscaPet-LABESOF2024/buscapet-api.git </code>

**2. Abra no seu editor de código** <br><br>
Durante o desenvolvimento, nós utilizamos o IntelliJ, por isso o recomendamos.

**3. Adicionando o banco de dados**<br><br>
Com o projeto aberto, abra o arquivo **application.properties** e adicione os dados do seu banco de dados. <br><br>
<code> 
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD} </code> <br>

- Altere ${DB_URL} para a URL do seu banco de dados. <br>
- Altere ${DB_USERNAME} para o nome de usuário do seu banco de dados. <br>
- Altere ${DB_PASSWORD} para a senha do seu banco de dados.

**4. Rode a aplicação** <br>
- Para isso, abra o arquivo BuscaPetApiApplication.java. <br>
- Clique no botão Run e execute o projeto. (Shift + 10 é o atalho para executar o projeto.)
- Ao ser executado o projeto, a aplicação roda todas as migrações presentes relacionadas ao banco de dados.
- Com a aplicação rodando, ela utilizada a porta 8080.

