# 🐾 Sistema de Cadastro e Manipulação de Pets

Este é um sistema para cadastro, busca, atualização e exclusão de pets, desenvolvido com **Spring Boot**, **PostgreSQL** e **Docker**.  
O projeto é uma reinterpretação de um desafio original que utilizava armazenamento em arquivos `.txt`, porém nesta versão os dados são persistidos em um banco de dados relacional.

---

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3**
- **PostgreSQL**
- **Docker & Docker Compose**
- **Lombok**
- **Spring Data JPA**
- **Maven**

---

## 🗂️ Funcionalidades

- Cadastro completo de pets
- Busca por múltiplos critérios (nome, sexo, idade, peso, raça, endereço)
- Atualização de todos os dados, exceto tipo e sexo
- Exclusão de registros
- Listagem geral ou filtrada de pets

---

## 🐘 Banco de Dados

Os dados dos pets são armazenados em um banco **PostgreSQL**.  
A conexão é configurada no `application.properties` e o container é gerenciado via **Docker Compose**.

---

## 🐳 Como executar com Docker

### Pré-requisitos:
- Docker e Docker Compose instalados

### Passos:

```bash
# Clone o projeto
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio

# Suba os containers
docker-compose up --build
