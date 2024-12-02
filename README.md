# Microserviços com Java Spring Boot e OpenFeign

## Descrição do Projeto

Este projeto consiste no desenvolvimento de dois microsserviços, implementados em Java 17 utilizando Spring Boot e OpenFeign, que se comunicam entre si e com a API externa JSONPlaceholder (https://jsonplaceholder.typicode.com). O objetivo é oferecer uma solução integrada para manipulação de posts e comentários através de uma arquitetura baseada em microsserviços




---

## Funcionalidades

### **Gerenciamento de Posts**

1. **Criar Post**
   - **Endpoint:** `POST /api/posts/ms-a`
   - **Descrição:** Cria um novo post.

2. **Consultar Todos os Posts**
   - **Endpoint:** `GET /api/posts/ms-a`
   - **Descrição:** Lista todos os posts disponíveis.

3. **Consultar Post por ID**
   - **Endpoint:** `GET /api/posts/ms-a/{id}`
   - **Descrição:** Recupera informações de um post específico pelo seu ID.

4. **Atualizar Post por ID**
   - **Endpoint:** `PUT /api/posts/ms-a/{id}`
   - **Descrição:** Atualiza as informações de um post existente.

5. **Excluir Post por ID**
   - **Endpoint:** `DELETE /api/posts/ms-a/{id}`
   - **Descrição:** Exclui um post pelo seu ID.

---

### **Gerenciamento de Comentários**

1. **Criar Comentário**
   - **Endpoint:** `POST /api/posts/{postId}/comments`
   - **Descrição:** Cria um novo comentário para um post específico.

2. **Consultar Todos os Comentários de um Post**
   - **Endpoint:** `GET /api/posts/{postId}/comments`
   - **Descrição:** Recupera todos os comentários de um post.

3. **Consultar Comentário por ID**
   - **Endpoint:** `GET /api/posts/{postId}/{id}`
   - **Descrição:** Recupera informações de um comentário específico.

4. **Atualizar Comentário**
   - **Endpoint:** `PUT /api/posts/{postId}/{id}`
   - **Descrição:** Atualiza um comentário existente.

5. **Excluir Comentário**
   - **Endpoint:** `DELETE /api/posts/{postId}/{id}`
   - **Descrição:** Exclui um comentário específico.

---

## Configuração do Projeto

### **Requisitos**

1. **Java 17**
2. **Spring Boot**
3. **Maven**
4. **JUnit e Mockito** para testes
5. **Swagger/OpenAPI** para documentação
6. **MongoDB Compass** como banco
7. Microsserviço B deve estar rodando localmente na porta `8081`.
8. Microsserviço A deve estar rodando localmente na porta `8080`.

### **Instruções para Rodar o Projeto**

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/seu-usuario/microsservico-a.git
   cd microsservico-a

2. **Execute o projeto**


André Colossi Branco <br>
Christakis Cyrile Tsikos Banza <br>
Felipe Volz Tadiello <br>
Pedro Antonio Da Silveira Junior <br>
Natanael do Nascimento Freire <br>
