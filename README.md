# Permutador
API Demonstrativa com arquitetura hexagonal que contém um endpoint para realizar permutação de strings

## Execução

Para executar o projeto, basta utilizar o comando

```docker-compose up --build```

## Requisições

Exemplo de requisição para o Permutador:

```
curl --location 'http://localhost:9090/api/permutacao' \
--header 'Content-Type: application/json' \
--data '{
    "stringPermutavel": "abcdefghijklm"
}'
```

**Obs.:** O resultado das permutações é impresso nos logs do container.
