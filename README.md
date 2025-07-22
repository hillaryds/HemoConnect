# HemoConnect 🏥🩸

Sistema de Gestão de Doação de Sangue desenvolvido em Java que conecta doadores, hospitais e administradores em uma plataforma unificada para otimizar o processo de doação de sangue.

## 📖 Introdução

O **HemoConnect** é um sistema completo de gerenciamento de banco de sangue que digitaliza e automatiza todo o processo de doação, desde o cadastro de doadores até o registro final das doações. O sistema foi desenvolvido seguindo os princípios da Programação Orientada a Objetos e utiliza PostgreSQL como banco de dados.

## 🔧 Pré-requisitos

### Software Necessário

- **Java Development Kit (JDK) 8 ou superior**;
- **PostgreSQL 12 ou superior**;
- **Driver JDBC PostgreSQL** (incluído: `postgresql-42.7.7.jar`).

### Configuração do Ambiente

1. **Instale o PostgreSQL** em sua máquina
2. **Configure as credenciais** no arquivo `src/database/DatabaseConnection.java`:
   ```java
   private static final String DB_URL = "jdbc:postgresql://localhost:5432/HemoConnect";
   private static final String DB_USER = "postgres";
   private static final String DB_PASSWORD = "1234";
   ```
3. **Execute o script de criação** do banco de dados: `sql/schema.sql`

## 🏗️ Arquitetura do Sistema

O projeto segue o padrão **MVC (Model-View-Controller)** com **DAO (Data Access Object)**:

```
HemoConnect/
├── src/
│   ├── main/
│   │   └── MainSystem.java           # Sistema principal
│   ├── administrador/
│   │   ├── Administrador.java        # Model
│   │   ├── AdministradorController.java
│   │   ├── AdministradorDAO.java
│   │   ├── AdministradorView.java
│   │   └── AdministradorMain.java
│   ├── hospital/
│   │   ├── Hospital.java             # Model
│   │   ├── HospitalController.java
│   │   ├── HospitalDAO.java
│   │   ├── HospitalView.java
│   │   └── HospitalMain.java
│   ├── doador/
│   │   ├── Doador.java               # Model
│   │   ├── DoadorController.java
│   │   ├── DoadorDAO.java
│   │   ├── DoadorView.java
│   │   └── DoadorMain.java
│   ├── triagem/
│   │   ├── Triagem.java              # Model
│   │   ├── TriagemController.java
│   │   ├── TriagemDAO.java
│   │   ├── TriagemView.java
│   │   └── TriagemMain.java
│   ├── doacao/
│   │   ├── Doacao.java               # Model
│   │   ├── DoacaoController.java
│   │   ├── DoacaoDAO.java
│   │   ├── DoacaoView.java
│   │   └── DoacaoMain.java
│   └── database/
│       └── DatabaseConnection.java   # Conexão com BD
├── lib/
│   └── postgresql-42.7.7.jar        # Driver JDBC
├── sql/
│   └── schema.sql                    # Script de criação do BD
└── bin/                              # Arquivos compilados
```

## ⚙️ Funcionalidades por Módulo

| Módulo | Funcionalidades | Descrição |
|--------|----------------|-----------|
| **🏥 Hospital** | • Listar hospitais<br>• Criar hospital<br>• Buscar por cidade<br>• Buscar por nome<br>• Remover hospital | Gerenciamento completo de hospitais com validação de integridade referencial |
| **👨‍💼 Administrador** | • Login/Logout<br>• Listar administradores<br>• Criar administrador<br>• Remover administrador | Sistema de autenticação e controle de acesso ao sistema |
| **👥 Doador** | • Listar doadores<br>• Criar doador<br>• Buscar por CPF<br>• Listar por tipo sanguíneo<br>• Listar por cidade<br>• Listar por hospital<br>• Remover doador | Gestão completa de doadores com validações específicas |
| **🩺 Triagem** | • Listar triagens<br>• Criar triagem<br>• Buscar por data<br>• Listar aprovadas/reprovadas<br>• Estatísticas por data<br>• Remover triagem | Avaliação automática de aptidão baseada em critérios médicos |
| **🩸 Doação** | • Listar doações<br>• Registrar doação<br>• Buscar por data<br>• Buscar por doador<br>• Estatísticas gerais<br>• Relatórios por período | Controle completo das doações realizadas com relatórios |

## 🚀 Como Executar

### 1. Preparação do Banco de Dados

```sql
-- 1. Criar o banco de dados
CREATE DATABASE HemoConnect;

-- 2. Executar o script de criação das tabelas
\i sql/schema.sql
```

### 2. Compilação do Projeto

```bash
# Navegar até o diretório do projeto
cd HemoConnect

# Criar diretório bin se não existir
mkdir bin -Force

# Compilar todas as classes Java
javac -cp "lib\postgresql-42.7.7.jar" -d bin src\main\*.java src\administrador\*.java src\hospital\*.java src\doador\*.java src\triagem\*.java src\doacao\*.java src\database\*.java
```

### 3. Execução do Sistema

```bash
java -cp "bin;lib/postgresql-42.7.7.jar" main.MainSystem
```

### 4. Login Inicial

- **Login:** `admin`
- **Senha:** `admin123`

## 📄 Informações Acadêmicas

- **Instituição**: UFERSA (Universidade Federal Rural do Semi-Árido)
- **Disciplina**: Programação Orientada a Objetos (POO)
- **Semestre**: 2025.1
- **Linguagem**: Java
- **Banco de Dados**: PostgreSQL
- **Padrões**: MVC, DAO, Singleton

## 👥 Participantes

<table align="center">
  <tr>    
    <td align="center">
      <a href="https://github.com/hillaryds">
        <img src="https://avatars.githubusercontent.com/u/143619299?v=4" 
        width="120px;" alt="Foto de Hillary Diniz"/><br>
        <sub>
          <b>Hillary Diniz</b>
         </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/letsticia">
        <img src="https://avatars.githubusercontent.com/u/126128839?v=4" 
        width="120px;" alt="Foto de Letícia Gonçalves no GitHub"/><br>
        <sub>
          <b>Letícia Gonçalves</b>
         </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/alexrbss">
        <img src="https://avatars.githubusercontent.com/u/143243497?v=4" 
        width="120px;" alt="Foto de Rubens Alexandre"/><br>
        <sub>
          <b>Rubens Alexandre</b>
         </sub>
      </a>
    </td>
  </tr>
</table>

