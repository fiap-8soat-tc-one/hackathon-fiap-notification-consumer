# AWS Lambda com Notification Consumer 🚀

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fiap-8soat-tc-one_hackathon-fiap-notification-consumer&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=fiap-8soat-tc-one_hackathon-fiap-notification-consumer)
[![Build and Publish image to ECR](https://github.com/fiap-8soat-tc-one/hackathon-fiap-notification-consumer/actions/workflows/build.yml/badge.svg)](https://github.com/fiap-8soat-tc-one/hackathon-fiap-notification-consumer/actions/workflows/build.yml)

## 📘 Visão Geral
Este repositório contém uma aplicação **Spring Boot**, empacotada com **Docker**, que atua como consumer de mensagens do Amazon SQS, processando notificações e enviando e-mails através do SendGrid.

---

## 🔐 Por que usar o SendGrid?

O **SendGrid** é um serviço de envio de e-mails que pode ser usado para campanhas de marketing, e-mails transacionais e outros. É uma boa opção para empresas de todos os portes, desde startups até multinacionais, entre as principais vantagens estão:
- ✅ **Suporte**: O SendGrid está disponível em todo o mundo
- ✅ **Escalabilidade**: Serve para empresas de todos os portes, desde startups até multinacionais 
- ✅ **Análise em tempo real**: O SendGrid oferece análise personalizável e em tempo real 
- ✅ **API**: O SendGrid oferece uma API que permite enviar e-mails em grande escala 
- ✅ **Testes A/B**: O SendGrid permite realizar testes A/B para melhorar o relacionamento com os clientes 

---

## ☕ Por que usar uma App Spring Boot ?

Análisamos a possibilidade de utilizar **Amazon SNS** com **AWS Lambda**, mas isso demandaria mais recursos e tornaria a solução mais complexa em termos de infraestrutura, por isso optamos por fazer uma aplicaçào Spring Boot com todas as dependências carregadas via Maven.

## 🧪 Estrutura do Projeto

```bash
src/
├── main/
│   ├── java/
│   │   └── com.seuprojeto.notifier/
│   │       ├── config/       # Configurações AWS e SendGrid
│   │       ├── listener/     # Listener do SQS
│   │       ├── service/      # Serviço de envio de e-mail
│   │       └── model/        # Modelos de dados
│   └── resources/
│       └── application.yml

---

## 🔄 Fluxo de Funcionamento

1. Mensagem é recebida via SQS após processamento do(s) vídeo(s)
2. Notification Consumer recebe a mensagem envia um email via SendGrid

---

## 📎 Exemplo de Request Payload JSON
```json
{
  "to": "usuario@exemplo.com",
  "subject": "Nova notificação",
  "body": "Olá! Esta é uma notificação enviada via SendGrid."
}
```

### ▶️ Rodando a aplicação localmente
```bash
./mvnw spring-boot:run
```
---

## ✅ Requisitos
- Java 17+
- Maven
- Conta na AWS com acesso à SQS
- Conta SendGrid com API Key válida

---

## ✉️ Contato
Para dúvidas ou sugestões, entre em contato com o time técnico responsável pelo Hackaton FIAP.

