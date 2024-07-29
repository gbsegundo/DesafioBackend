# Desafio Backend
Implementar uma API REST para um sistema simples de contas a pagar.



## Atenticação

As API's estão todas com o sistema de autenticação do tipo JWT.

Antes de chamar cada API é preciso gerar o token chamando a api http://localhost:8013/desafio/authenticate
passando no body o json: {"username": "desafio","password":"password"}.
Deixei fixo o usuario desafio, se for gerar com qualquer outro usuario vai dar a mensagem de usuario inválido.

Essa chamava vai retornar o token, como o exemplo abaixo:

{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkZXNhZmlvIiwiZXhwIjoxNzIyMDczNzExLCJpYXQiOjE3MjIwNTU3MTF9.QvQEwIPQKLfS_PhRE0cj-0LG73X6fiNu0N0Ba0meXcpgUxPHjyXtlUvH7qv9jy6Kr6HXln1k1pxLglmj_CNxbQ"
}

E pra fazer a chamada das outras API's é preciso passar no header o token gerado, como o exemplo abaixo: 

key:  Authorization      Value: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkZXNhZmlvIiwiZXhwIjoxNzIyMDczNjkzLCJpYXQiOjE3MjIwNTU2OTN9.hqQf-ALUK0aUTOgVUHf2_kemch7G6fMElJnRUukKyPwOkGfYe-0edqOqqUi2UgyxQvS2XKiU6jSEiw-0KuHmAA
