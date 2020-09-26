# API - Planetas

## Tecnologias
- Java 8
- Spring Boot
- Lombok
- ModelMapper
- DB - MongoDB
- Testes - Junit 5
- Bean Validation
- Spring Hateoas

### Endpoints

> ## ![#f03c15](https://placehold.it/15/fdff49/000000?text=+) POST Adicionar
> `http://localhost:8080/api/v1/planeta`

> ### Body

```json
{
    "nome": String,
    "clima": String,
    "terreno": String
}
```

----

> ## ![#1589F0](https://placehold.it/15/3dd200/000000?text=+) GET Listar
> `http://localhost:8080/api/v1/planeta`

----

> ## ![#1589F0](https://placehold.it/15/3dd200/000000?text=+) GET Pesquisar por Id
> `http://localhost:8080/api/v1/planeta/:id`

----

> ## ![#1589F0](https://placehold.it/15/3dd200/000000?text=+) GET Pesquisar por nome
> `http://localhost:8080/api/v1/planeta?nome=:nome`

----

> ## ![#1589F0](https://placehold.it/15/1589F0/000000?text=+) PUT Atualizar
> `http://localhost:8080/api/v1/planeta/:id`

> ### Body

```json
{
    "nome": String,
    "clima": String,
    "terreno": String
}
```

----

> ## ![#1589F0](https://placehold.it/15/ff0000/000000?text=+) DELETE Planeta
> `http://localhost:8080/api/v1/planeta/:id`
