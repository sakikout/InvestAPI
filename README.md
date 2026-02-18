# 📊 InvestAPI: API de Gerenciamento de Carteira de Investimentos

A proposta desse projeto é o desenvolvimento de uma API RESTful com Spring Boot que permita aos usuários cadastrarem ativos que possuem, atualizarem informações, acompanharem saldos e obterem um resumo de suas carteiras de investimentos. 

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) ![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white) 


## ⚙️ Inicialização

Para inicializar a aplicação, caminhe até a pasta `docker` no terminal a partir da raíz do repositório (`cd docker`), e rode o comando `docker compose -f docker-compose-dev.yml up --build`. Certifique-se que o Docker Desktop está em funcionamento. Assim que a aplicação estiver em funcionamento, é possível testar as rotas no arquivo em `resquests.http` na pasta `/invest`.

## 📎Funcionalidades

### 1. Serviço de Investimentos

A entidade de investimentos é usada para representar uma ação cadastrada por um usuário.

* Cadastrar um novo ativo na carteira
    - Endpoint: `POST /investments`

Payload:
```json
{
  "type": "ACAO",
  "symbol": "VALE3",
  "quantity": 50,
  "purchasePrice": 65.50,
  "purchaseDate": "2025-08-15"
}
```

* Listar todos os ativos da carteira
    - Endpoint: `GET /investments`
* Obter um ativo específico
    - Endpoint: `GET /investments/{id}`
* Filtrar ativos por tipo
    - Endpoint: `GET /investments?type=ACAO`
* Atualizar um ativo
    - Endpoint: `PUT /investments/{id}`
* Remover um ativo da carteira
    - Endpoint: `DELETE /investments/{id}`
* Resumo da carteira
    - Endpoint: `GET /investments/summary`

### 2. Serviço de Usuários
A entidade de usuário é usada para representar o usuário que acessa o sistema. Além disso, ela é usada para atribuir um portfólio específico à um usuário.

* Criar um usuário
    - Endpoint: `POST /users`

Payload:
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "password": "123456",
  "role": "USER"
}

```

* Listar todos os usuários
    - Endpoint: `GET /users/all`
* Obter um usuário por id
    - Endpoint: `GET /users/{id}`
* Atualizar um usuário
    - Endpoint: `PUT /users/{id}`
* Deletar um usuário
    - Endpoint: `DELETE /users/{id}`

### 3. Serviço de Portfólios

A entidade de portfólio é usada para armazenar todos os investimentos cadastrados por um usuário, além de guardar cálculos referentes à eles.

* Criar um portfólio
    - Endpoint: `POST /porfolios`

Payload:
```json
{
  "userId": "{{userId}}",
  "totalInvested": 600.00,
  "totalByType": {
    "ACAO": 600.00
  },
  "assetCount": 1,
  "investments": [
    {
      "type": "ACAO",
      "symbol": "VALE3",
      "quantity": 100,
      "purchasePrice": 60.00,
      "purchaseDate": "2025-08-15"
    }
  ]
}

```

* Listar todos os portfólios
    - Endpoint: `GET /portfolios/all`
* Obter um portfólio por id
    - Endpoint: `GET /portfolios/{id}`
* Obter um portfólio pelo id do usuário
    - Endpoint: `GET /porfolios/user/{userId}`
* Atualizar um portfólio
    - Endpoint: `PUT /porfolios/{id}`
* Deletar um portfólio
    - Endpoint: `DELETE /porfolios/{id}`
