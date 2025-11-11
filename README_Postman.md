# Guía de Pruebas con Postman - Pediatric Dosage Calculator AI

Esta guía explica cómo probar la funcionalidad de Inteligencia Artificial de Pediatric Dosage Calculator usando Postman.

##  Requisitos Previos

- **Postman instalado**: Descarga desde [postman.com](https://www.postman.com/downloads/)
- **Backend corriendo**: El servidor Spring Boot debe estar ejecutando en `http://localhost:8080`
- **Colección de Postman**: Crea una nueva colección llamada "Pediatric Dosage Calculator"

##  Configuración Inicial

### 1. Crear Colección en Postman

1. Abre Postman
2. Haz clic en "New" > "Collection"
3. Nómbrala "Pediatric Dosage Calculator"
4. Crea una variable de entorno para el token JWT

### 2. Configurar Variables de Entorno

1. En la colección, ve a "Variables"
2. Agrega estas variables:
   - `base_url`: `http://localhost:8080`
   - `jwt_token`: (dejalo vacío por ahora)

##  Paso 1: Autenticación (Login)

### Crear Request de Login

1. **Nuevo Request**:
   - Method: `POST`
   - URL: `{{base_url}}/api/auth/login`

2. **Headers**:
   ```
   Content-Type: application/json
   ```

3. **Body** (raw JSON):
   ```json
   {
     "username": "admin",
     "password": "admin123"
   }
   ```

4. **Enviar Request**:
   - Haz clic en "Send"
   - Deberías recibir una respuesta como:
   ```json
   {
     "token": "eyJhbGciOiJIUzI1NiJ9...",
     "type": "Bearer",
     "username": "admin",
     "authorities": ["ROLE_ADMIN"]
   }
   ```

5. **Guardar Token**:
   - Copia el valor del `token`
   - Ve a las variables de la colección
   - Pega el token en la variable `jwt_token`

##  Paso 2: Probar la IA

### Crear Request de IA

1. **Nuevo Request**:
   - Method: `POST`
   - URL: `{{base_url}}/api/ai/generate`

2. **Headers**:
   ```
   Content-Type: application/json
   Authorization: Bearer {{jwt_token}}
   ```

3. **Body** (raw JSON):
   ```json
   {
     "prompt": "Explica qué es el paracetamol y sus usos en pediatría"
   }
   ```

4. **Enviar Request**:
   - Haz clic en "Send"
   - Deberías recibir una respuesta como:
   ```json
   {
     "response": "El paracetamol es un medicamento analgésico y antipirético utilizado comúnmente para reducir la fiebre y aliviar el dolor en niños..."
   }
   ```

##  Ejemplos de Pruebas

### Ejemplo 1: Consulta sobre Medicamentos

**Body**:
```json
{
  "prompt": "Qué debo saber sobre la administración de antibióticos en niños"
}
```

### Ejemplo 2: Pregunta sobre Dosificación

**Body**:
```json
{
  "prompt": "Cuáles son las consideraciones para dosificar medicamentos en bebés menores de 6 meses"
}
```

### Ejemplo 3: Información General

**Body**:
```json
{
  "prompt": "Explica qué es la fiebre y cuándo debo preocuparme en un niño"
}
```

##  Tests Automáticos en Postman

### Configurar Tests para Login

En el request de login, agrega esto en la pestaña "Tests":

```javascript
// Guardar token automáticamente
if (pm.response.code === 200) {
    const response = pm.response.json();
    pm.collectionVariables.set("jwt_token", response.token);
    console.log("Token guardado:", response.token);
}
```

### Configurar Tests para IA

En el request de IA, agrega esto en la pestaña "Tests":

```javascript
// Verificar respuesta exitosa
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Response has response field", function () {
    const jsonData = pm.response.json();
    pm.expect(jsonData).to.have.property('response');
});

pm.test("Response is not empty", function () {
    const jsonData = pm.response.json();
    pm.expect(jsonData.response).to.not.be.empty;
});
```

## 🐛 Solución de Problemas

### Error 401 Unauthorized
- **Causa**: Token JWT inválido o expirado
- **Solución**: Vuelve a hacer login y actualiza la variable `jwt_token`

### Error 400 Bad Request
- **Causa**: Prompt vacío o formato incorrecto
- **Solución**: Asegúrate de que el body tenga un `prompt` válido

### Error 500 Internal Server Error
- **Causa**: Problema con la API de Google Gemini
- **Solución**: Revisa los logs del backend, verifica la API key

### Connection Refused
- **Causa**: Backend no está corriendo
- **Solución**: Ejecuta `mvn spring-boot:run` o `java -jar target/pediatric-dosage-calculator-0.0.1-SNAPSHOT.jar`

##  Colección Completa de Requests

### Requests Sugeridos para Crear:

1. **Login** - `POST {{base_url}}/api/auth/login`
2. **AI Generate** - `POST {{base_url}}/api/ai/generate`
3. **Get Medicines** - `GET {{base_url}}/api/medicines` (necesita auth)
4. **Calculate Dose** - `POST {{base_url}}/api/calculate` (necesita auth)

### Headers Comunes para Requests Autenticados:
```
Content-Type: application/json
Authorization: Bearer {{jwt_token}}
```

##  Screenshots de Ejemplo

### 1. Request de Login
```
POST http://localhost:8080/api/auth/login
Headers: Content-Type: application/json
Body: {"username":"admin","password":"admin123"}
```

### 2. Request de IA
```
POST http://localhost:8080/api/ai/generate
Headers:
  Content-Type: application/json
  Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Body: {"prompt":"Explica qué es el ibuprofeno"}
```

##  Runner de Postman

Para probar múltiples requests automáticamente:

1. Ve a "Runner" en Postman
2. Selecciona la colección "Pediatric Dosage Calculator"
3. Ejecuta los requests en orden (login primero, luego IA)

##  Recursos Adicionales

- [Documentación de Postman](https://learning.postman.com/docs/getting-started/introduction/)
- [README de IA](../README_AI.md)
- [README de Frontend](../README_AI_Frontend.md)
- [README Principal](../README.md)

## ⚠️ Notas Importantes

- La IA es para fines informativos, no sustituye consejo médico profesional
- Mantén la API key de Google segura y no la compartas
- Los tokens JWT expiran, renueva cuando sea necesario
- Prueba en un entorno de desarrollo primero

---

¡Ahora puedes probar fácilmente todos los endpoints con Postman! Si tienes problemas, revisa los logs del backend o comparte los errores específicos.</content>
<parameter name="filePath">README_Postman.md