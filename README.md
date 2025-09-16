# Desafio Movieflix — Casos de Uso & Critérios de Testes

Este `arquivo.md` descreve os **casos de uso**, **endpoints**, **regras de negócio**, **critérios de aceitação dos testes** e instruções práticas para rodar o projeto e os testes automatizados (TDD) do desafio **Movieflix** (Spring Boot + Java).

---

## Visão geral

O sistema deve oferecer:

* Listagem paginada de filmes (título, subtítulo, ano, imagem), ordenada alfabeticamente por título.
* Listagem de gêneros (nomes de todos os gêneros).
* Filtro opcional por gênero na listagem de filmes.
* Visualização de detalhes do filme (título, subtítulo, ano, imagem, sinopse e lista de avaliações com o nome do usuário).
* Criação de avaliação (reviews) apenas por usuários com perfil `MEMBER`.

O projeto será avaliado por testes automatizados que exercitam endpoints REST, regras de autenticação/autorização e tratamento de erros.

---

## Requisitos de segurança / autenticação

* API protege rotas com JWT (ou mecanismo similar). Existem ao menos dois perfis de usuário:

  * `VISITOR` — pode consultar gêneros e filmes, ver detalhes.
  * `MEMBER` — tudo que o VISITOR faz + pode inserir reviews.
* Para qualquer request que exija autenticação, um token inválido deve resultar em **401 Unauthorized**.
* Para ações proibidas ao perfil (ex.: VISITOR tentando `POST /reviews`) deve retornar **403 Forbidden**.

> Observação: os testes esperam comportamentos explícitos (ver seção "Critérios de aceitação").

---

## Endpoints (especificação mínima)

### GET /genres

* Descrição: retorna lista com todos os gêneros (nomes).
* Autorização: `VISITOR` e `MEMBER` (ou seja, token válido de ambos deve retornar 200).
* Erros:

  * token inválido => **401**

### GET /movies

* Descrição: retorna página de filmes com as propriedades mínimas: `title`, `subTitle`, `year`, `imgUrl`.
* Parâmetros opcionais:

  * `genreId` — filtra os filmes pelo gênero.
  * parâmetros de paginação (`page`, `size`, `sort`) — deve vir ordenada alfabeticamente por título por padrão.
* Autorização: `VISITOR` e `MEMBER` (token inválido => 401).
* Retorno: **200** com página de resultados.

### GET /movies/{id}

* Descrição: retorna dados completos do filme: `title`, `subTitle`, `year`, `imgUrl`, `synopsis` e `reviews[]` (cada review contém `text` e o `name` do usuário que escreveu).
* Autorização: `VISITOR` e `MEMBER` (token inválido => 401).
* Erros:

  * id inexistente => **404 Not Found**

### POST /reviews

* Descrição: cria uma avaliação (review) associada a um filme; corpo deve conter `movieId` e `text` (ou model equivalente usado no projeto).
* Autorização: somente `MEMBER`.
* Comportamento esperado nos testes:

  * token inválido => **401**
  * `VISITOR` logado => **403**
  * `MEMBER` e dados válidos => **201 Created** com objeto inserido
  * `MEMBER` e dados inválidos (ex.: `text` em branco, `movieId` inválido) => **422 Unprocessable Entity**

---

## Critérios de aceitação (lista direta usada pelos testes)

* GET /genres deve retornar **401** para token inválido
* GET /genres deve retornar **200** com todos gêneros para `VISITOR` logado
* GET /genres deve retornar **200** com todos gêneros para `MEMBER` logado
* GET /movies/{id} deve retornar **401** para token inválido
* GET /movies/{id} deve retornar **200** com o filme para `VISITOR` logado
* GET /movies/{id} deve retornar **200** com o filme para `MEMBER` logado
* GET /movies/{id} deve retornar **404** para id inexistente
* GET /movies deve retornar **401** para token inválido
* GET /movies deve retornar **200** com página ordenada de filmes para `VISITOR` logado
* GET /movies deve retornar **200** com página ordenada de filmes para `MEMBER` logado
* GET /movies?genreId={id} deve retornar **200** com página ordenada de filmes filtrados por gênero
* POST /reviews deve retornar **401** para token inválido
* POST /reviews deve retornar **403** para `VISITOR` logado
* POST /reviews deve retornar **201** com objeto inserido para `MEMBER` logado e dados válidos
* POST /reviews deve retornar **422** para `MEMBER` logado e dados inválidos

---
