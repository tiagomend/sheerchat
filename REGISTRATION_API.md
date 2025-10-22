# API de Registro de Usuário

## Endpoint de Registro

### POST `/api/auth/register`

Cria uma nova conta de usuário no sistema.

#### Request Body

```json
{
  "username": "string",
  "email": "string",
  "password": "string"
}
```

#### Response Success (201 Created)

```json
{
  "message": "Conta criada com sucesso",
  "userId": 1,
  "username": "testuser",
  "emailConfirmationSent": false
}
```

#### Response Error (400 Bad Request)

Quando o nome de usuário já existe:
```json
{
  "message": "Username already exists",
  "userId": null,
  "username": null,
  "emailConfirmationSent": false
}
```

Quando o e-mail já existe:
```json
{
  "message": "Email already exists",
  "userId": null,
  "username": null,
  "emailConfirmationSent": false
}
```

## Exemplo de Uso

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "novousuario",
    "email": "usuario@exemplo.com",
    "password": "SenhaSegura123!"
  }'
```

## Configuração

### Confirmação de E-mail (Opcional)

Por padrão, a confirmação de e-mail está desabilitada. Para habilitá-la, adicione a seguinte propriedade ao `application.properties`:

```properties
app.email.confirmation.enabled=true
```

Quando habilitada, será necessário implementar o serviço de envio de e-mail.

## Validações

### Campos Obrigatórios
- `username`: mínimo 3 caracteres, máximo 30 caracteres
- `email`: formato válido com "@" e domínio válido
- `password`: mínimo 8 caracteres, máximo 100 caracteres

### Requisitos de Senha
A senha deve conter:
- Pelo menos uma letra (maiúscula ou minúscula)
- Pelo menos um número
- Pelo menos um símbolo (caractere especial)

Exemplos de senhas válidas: `Password123!`, `Senha@456`, `Test#789`

## Funcionalidades

- ✅ Validação de unicidade de nome de usuário
- ✅ Validação de unicidade de e-mail
- ✅ Validação de formato de e-mail
- ✅ Validação de força de senha (letras, números e símbolos)
- ✅ Validação de campos obrigatórios
- ✅ Armazenamento seguro de senhas (hash BCrypt)
- ✅ Mensagem de confirmação: "Conta criada com sucesso"
- ✅ Suporte para confirmação de e-mail (opcional)
- ✅ Usuário pode autenticar-se após o registro
