# API - Loja Virtual de Skates

### Motivação

Esta API foi desenvolvida como requisito para a matéria "Desenvolvimento de Serviços Web e Testes com Java" no Instituto Infnet. Seu objetivo principal é fornecer uma lista de skates e calcular o frete de entrega de um produto, embora o cálculo do frete seja fictício, baseado em uma regra de negócios para fins de exemplo.

### Informações

A aplicação foi construída com Spring Boot e fica em execução na porta 8080. Para testar a API, é necessário clonar o repositório e executá-lo localmente.

### Exemplo prático

**Skate**

- Solicitar lista de skates
  - GET - http://localhost:8080/skate

- Solicitar lista de skates com parâmetros de quantidade de itens e ordem da lista
  - GET - http://localhost:8080/skate?size=2&sort=name&order=asc

- Solicitar skate por ID
  - GET - http://localhost:8080/skate/1

- Cadastrar skate passando JSON
  - POST - http://localhost:8080/skate
  
```java
    {
      "id": 201,
      "name": "Product X",
      "description": "This is a fantastic product.",
      "imagePath": "/caminho/imagem6.jpg",
      "size": 10.5,
      "model": [
        "lorem",
        "ipsum",
        "dolor"
      ],
      "category": "Electronics",
      "brand": "Brand XYZ",
      "amount": 50,
      "price": 99.99
    }
```

- Deletar skate
  - DELETE - http://localhost:8080/skate/201

- Atualizar skate
  - PUT - http://localhost:8080/skate/1

```java
    {
      "name": "Product Y",
      "description": "This is a fantastic product.",
      "imagePath": "/caminho/imagem6.jpg",
      "size": 7.5,
      "model": [
        "lorem",
        "ipsum",
        "dolor"
      ],
      "category": "Electronics",
      "brand": "Brand XYZ",
      "amount": 50,
      "price": 259.99
    }
```

**Frete**

- Solicitar cotação de frete informando o CEP de origem, CEP de destino e peso do produto
  - GET - http://localhost:8080/cep?cepOrigin=01031900&cepDestination=20040000&weight=1.8

### Observações

- Os endpoints para manipulação de skates permitem operações CRUD (Create, Read, Update, Delete) para gerenciar produtos na loja.
- O endpoint de cotação de frete realiza um cálculo fictício com base em regras de exemplo para simular o valor do frete.
