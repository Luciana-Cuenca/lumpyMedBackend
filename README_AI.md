# Guía de Uso de la IA en Pediatric Dosage Calculator

Esta guía explica cómo utilizar la funcionalidad de Inteligencia Artificial integrada en la aplicación Pediatric Dosage Calculator.

##  Requisitos Previos

1. **Servidor corriendo**: Asegúrate de que la aplicación Spring Boot esté ejecutando en `http://localhost:8080`
2. **Token JWT**: Necesitas autenticarte primero para obtener un token de acceso

##  Autenticación

### 1. Login para obtener token JWT

**Endpoint**: `POST /api/auth/login`

**Headers**:
```
Content-Type: application/json
```

**Body**:
```json
{
  "username": "admin",
  "password": "admin123"
}
```

**Respuesta exitosa**:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer",
  "username": "admin",
  "authorities": ["ROLE_ADMIN"]
}
```

**Nota**: El usuario admin se crea automáticamente al iniciar la aplicación por primera vez.

##  Uso de la IA

### Endpoint de IA

**URL**: `POST /api/ai/generate`

**Headers**:
```
Content-Type: application/json
Authorization: Bearer {tu_token_jwt}
```

**Body**:
```json
{
  "prompt": "Tu pregunta o consulta médica aquí"
}
```

### Ejemplos de Uso

#### 1. Consulta sobre medicamentos

```json
{
  "prompt": "Explica qué es el paracetamol y sus usos en pediatría"
}
```

#### 2. Pregunta sobre dosificación

```json
{
  "prompt": "Cuáles son las consideraciones para dosificar antibióticos en niños menores de 2 años"
}
```

#### 3. Información general médica

```json
{
  "prompt": "Qué debo saber sobre la fiebre en bebés"
}
```

### Respuesta de la IA

**Respuesta exitosa (200 OK)**:
```json
{
  "response": "El paracetamol es un medicamento analgésico y antipirético utilizado comúnmente para reducir la fiebre y aliviar el dolor en niños. En pediatría, se administra generalmente cada 4-6 horas según sea necesario, pero nunca excediendo la dosis máxima diaria recomendada por peso corporal..."
}
```

**Errores posibles**:

- **401 Unauthorized**: Token JWT inválido o expirado
- **400 Bad Request**: Prompt vacío o inválido
- **500 Internal Server Error**: Error en la API de Google Gemini

##  Pruebas con cURL

### 1. Obtener token
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

### 2. Usar la IA
```bash
curl -X POST http://localhost:8080/api/ai/generate \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TU_TOKEN_AQUI" \
  -d '{"prompt":"Explica qué es el ibuprofeno"}'
```

##  Solución de Problemas

### Token expirado
Los tokens JWT expiran después de cierto tiempo. Si obtienes un error 401, vuelve a hacer login.

### Error de conexión
- Verifica que el servidor esté corriendo en el puerto 8080
- Revisa los logs de la aplicación para errores de la API de Google

### Configuración de la API Key
La API key de Google Gemini está configurada en `application.properties`. Si hay problemas, verifica que la key sea válida.

##  Consideraciones de Seguridad

- La API key de Google está protegida en el backend y no se expone al frontend
- Todas las consultas a la IA requieren autenticación JWT
- Los datos sensibles no se envían a la API externa

##  Limitaciones

- Las respuestas de la IA son informativas y no sustituyen el consejo médico profesional
- La aplicación está diseñada para uso educativo y de apoyo, no para diagnóstico médico
- Siempre consulta con un profesional de la salud para decisiones médicas importantes

---

Para más información sobre otros endpoints de la API, consulta el [README principal](../README.md).</content>
<parameter name="filePath">README_AI.md