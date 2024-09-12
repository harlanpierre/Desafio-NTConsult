# Desafio Técnico NT Consult

Este projeto é um desafio técnico da empresa NT Consult, consistindo em dois microserviços: `service-hotel` e `service-notification`.

## Visão Geral

O `service-hotel` oferece três APIs principais:
1. Pesquisa de hotéis disponíveis
2. Comparação de opções entre hotéis
3. Reserva de hotel

Após uma reserva ser feita, o sistema envia os dados para o `service-notification` através de uma fila no RabbitMQ, que então envia um email de confirmação para o cliente e em seguida publica uma mensagem na fila do rabbitmq para o `service-hotel` confirmando o envio do e-mail.

## Instruções de Instalação

1. Clone o repositório:
   ```
   git clone https://github.com/harlanpierre/Desafio-NTConsult.git
   ```

2. Na raiz do projeto, execute:
   ```
   docker-compose up -d
   ```

   Isso irá iniciar instâncias do PostgreSQL, RabbitMQ, SonarQube, construir e iniciar os serviços hotel e notification, nas portas 8080 e 8081, respectivamente.

## Testando as APIs

### 1. API de Busca de Hotéis

Utilize o seguinte curl no Insomnia ou Postman:

```
curl --request GET \
--url 'http://localhost:8080/hotel/search?destination=cabo&checkInDate=2024-09-28&checkOutDate=2024-09-29&numRooms=1&numGuests=5&pageSize=1&page=0'
```

Nota: Apenas `checkInDate` e `checkOutDate` são parâmetros obrigatórios.

### 2. API de Comparação de Opções entre Hotéis

Utilize o seguinte curl:

```
curl --request GET \
--url 'http://localhost:8080/hotel/comparison?ids=2&priceNight=150.00&destination=cabo&amenities=wifi&page=0&pageSize=10'
```

### 3. API de Reserva de Hotéis

Utilize o seguinte curl:

```
curl --request POST \
--url http://localhost:8080/reservation \
--header 'Content-Type: application/json' \
--data '{
"hotelId": 1,
"dateCheckin": "2024-09-28",
"dateCheckout": "2024-09-29",
"nameClient": "Harlan Pierre",
"contact": "harlan.pierre@email.com",
"paymentMethod": "Pix"
}'
```
## OpenAPI

O `service-hotel` fornece uma interface web OpenAPI e um arquivo `json` para serem usados também. Basta acessar:

* Interface Web: [http://localhost:8080/swagger-ui](http://localhost:8080/swagger-ui.html)
* JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## Credenciais

### PostgreSQL
- User: ntuser
- Password: ntpassword
- Port: 5432
- Database: ntdatabase

### RabbitMQ
- Host: localhost
- Port: 5672
- User: ntusermq
- Password: ntpasswordmq

## Testes de Cobertura com SonarQube

1. Acesse http://localhost:9000 no navegador
2. Faça login com usuário e senha: admin/admin
3. Crie um projeto 'Local' e gere um token
4. Acesse o terminal na raiz do projeto `service-hotel`, e execute:

```
mvn clean verify sonar:sonar \
-Dsonar.projectKey=nt-service-hotel \
-Dsonar.host.url=http://localhost:9000 \
-Dsonar.login=YOUR_GENERATED_TOKEN
```

Substitua `YOUR_GENERATED_TOKEN` pelo token gerado no passo 3.

## Arquitetura e Decisões de Design

- **Banco de Dados**: PostgreSQL foi escolhido por ser relacional e facilmente escalável. Índices foram criados nas principais colunas para melhorar o desempenho das consultas.

- **Arquitetura**: Foi utilizada a arquitetura hexagonal para isolar a regra de negócio das tecnologias utilizadas, facilitando manutenção e possíveis mudanças futuras.

## Melhorias Futuras

- Aprimorar a estrutura do `service-notification`
- Criar mais serviços, como: criação de hotéis, cancelamento de reserva, cadastro de clientes etc...
- Implementar exceções mais personalizadas.