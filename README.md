# 🥋 Registro de Ponto - Judô

Sistema completo de Registro de Ponto com interface moderna (JavaFX), backend em Java com PostgreSQL hospedado na nuvem (Railway), arquitetura limpa e pronta para produção.

---

## ✅ Funcionalidades

- Login com autenticação segura
- Cadastro e edição de usuários
- Registro de entrada/saída com cálculo automático da duração
- Histórico completo em tabela
- Layout moderno com imagem de fundo
- Gerenciamento de usuários (CRUD)

---

## 📦 Requisitos

- Java 17 ou superior
- Maven 3.6+
- Internet ativa (banco está na nuvem)

---

## ☁️ Banco de Dados na Nuvem (Railway)

O sistema usa PostgreSQL remoto via [Railway](https://railway.app).

### 🔐 Dados de Conexão

```properties
db.url=jdbc:postgresql://maglev.proxy.rlwy.net:38004/railway
db.user=postgres
db.password=SUA_SENHA_AQUI
db.driver=org.postgresql.Driver
