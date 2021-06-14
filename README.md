# Rest API

Projeto destinado à conclusão da disciplina de Sistemas Distribuídos visando o aprendizado prático da arquitetura REST

## Setup desenvolvimento

- Para subir a aplicação com as configurações de desenvolvimento é necessário:
    - PostgreSQL 11+
    - Java 16+
    - Gradle 7+ (Opcional)

## Rodar Aplicação

- Criar o banco `rest-api` no servidor do Postgres local (`localhost`)
- É possível rodar a aplicação a partir da sua classe principal [RestAPIApplication](src/main/java/com/sistemasdistribuidos/restapi/RestAPIApplication.java) ou através da runConfiguration do IntellijIDEA (cada IDE possui sua integração com o Spring Boot)
- Dependendo das configurações de autenticação do servidor, é necessário modificar no [application.yml](src/main/resources/application.yml) ou passar como parâmetro na runConfiguration os parâmetros de autenticação `spring.datasource.username` e `spring.datasource.password` (no IntellijIDEA fica em Configuration -> Environment -> Program Arguments)
- A Aplicação roda por padrão na porta 8080 com a URL completa: http://localhost:8080/, e o front-end do Swagger é acessível no endpoint `/swagger-ui` (http://localhost:8080/swagger-ui)
