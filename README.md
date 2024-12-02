# Integração entre Microsserviços A e B  

## **Descrição do Projeto**  
Este projeto consiste em dois microsserviços: **Microserviço A** e **Microserviço B**.  
- O **Microserviço A** consome a API do **Microserviço B**.  
- O **Microserviço B**, por sua vez, realiza requisições para um site externo para coletar informações e processá-las.  

---

## **Arquitetura**  
### **Microserviço A**  
- Responsável por fazer requisições HTTP para o **Microserviço B** e processar as respostas.  

### **Microserviço B**  
- Recebe as requisições do **Microserviço A**.  
- Acessa um site externo (como uma API pública ou página da web).  
- Processa os dados recebidos e os envia de volta para o **Microserviço A**.  

---

## **Fluxo de Dados**  
1. **Microserviço A** faz uma requisição para o **Microserviço B**.  
2. **Microserviço B** faz uma requisição HTTP para um site externo (https://jsonplaceholder.typicode.com).  
3. O site externo retorna os dados, que são processados pelo **Microserviço B**.  
4. **Microserviço B** envia os dados processados de volta para o **Microserviço A**.  
5. **Microserviço A** recebe os dados do **Microserviço B** e realiza ações ou retornos conforme necessário.  

---


Integrantes:

André Colossi Branco <br>
Christakis Cyrile Tsikos Banza <br>
Felipe Volz Tadiello <br>
Pedro Antonio Da Silveira Junior <br>
Natanael do Nascimento Freire <br>
