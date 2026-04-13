# 🏋️ Sistema de Gestión de Gimnasio (GMS) - Guía Completa de Pruebas

## 📊 Status del Sistema

✅ **Backend:** Spring Boot 3.5.13 (Java 21) - Puerto 8080  
✅ **Frontend:** Angular 18.2.21 - Puerto 4400  
✅ **Base de Datos:** MySQL 8.0 - localhost:3306  
✅ **Autenticación:** JWT (60 min) + Refresh Tokens (7 días)  

---

## 🚀 INICIO RÁPIDO - Opción Automática

### Ejecutar Todo con PowerShell

```powershell
# Ejecutar backend + frontend + BD (recomendado)
.\INICIAR_GMS.ps1 todo

# O individual:
.\INICIAR_GMS.ps1 backend     # Solo backend
.\INICIAR_GMS.ps1 frontend    # Solo frontend
.\INICIAR_GMS.ps1 bd          # Solo BD
.\INICIAR_GMS.ps1 status      # Ver status
```

**¿Qué sucede?**
- ✅ Verifica MySQL corriendo en localhost:3306
- ✅ Crea base de datos `gms` si no existe
- ✅ Compila y ejecuta backend (mvn spring-boot:run)
- ✅ Instala dependencias Angular y compila
- ✅ Inicia frontend en puerto 4400
- ✅ Abre automáticamente navegador

---

## ⚙️ CONFIGURACIÓN DE POSTMAN

### Paso 1: Importar Colección

1. **Abrir Postman** → Click en **"Import"**
2. **Cargar archivo:** `GMS-API-Collection.postman_collection.json`
3. Click **"Import"** → ¡Listo!

### Paso 2: Crear Ambiente

1. **Environments** (lado izquierdo) → **"+"**
2. **Nombre:** `GMS Development`
3. **Variables necesarias:**

| Variable | Valor Inicial | Comentario |
|----------|---------------|-----------|
| `base_url` | `http://localhost:8080` | URL del backend |
| `access_token` | *(vacío)* | Se llena tras login |
| `refresh_token` | *(vacío)* | Se llena tras login |

4. **Guardar** (Ctrl+S)

### Paso 3: Seleccionar Ambiente

Arriba a la derecha → Menú desplegable → **"GMS Development"**

---

## 🔐 AUTENTICACIÓN - Flujo Completo

### Opción 1️⃣: Registrarse (Nuevo Usuario)

**Endpoint:** `POST {{base_url}}/api/v1/auth/registro`

**Body (JSON):**
```json
{
  "username": "carlos_perez",
  "email": "carlos@ejemplo.com",
  "password": "MiPassword@123",
  "nombre": "Carlos",
  "apellido": "Pérez García"
}
```

**Respuesta (201 Created):**
```json
{
  "success": true,
  "statusCode": 201,
  "message": "Usuario registrado exitosamente",
  "data": {
    "id": 1,
    "username": "carlos_perez",
    "email": "carlos@ejemplo.com",
    "nombre": "Carlos",
    "apellido": "Pérez García",
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  }
}
```

**⚠️ Guardar tokens en ambiente:**
1. En Postman, ir a **Environments** → **GMS Development**
2. Pegar `accessToken` en variable: `access_token`
3. Pegar `refreshToken` en variable: `refresh_token`

### Opción 2️⃣: Login (Usuario Existente)

**Endpoint:** `POST {{base_url}}/api/v1/auth/login`

**Body:**
```json
{
  "username": "admin",
  "password": "admin123"
}
```

**Credenciales por defecto:**
- Usuario: `admin`
- Contraseña: `admin123`

**Respuesta:**
```json
{
  "success": true,
  "statusCode": 200,
  "message": "Login exitoso",
  "data": {
    "id": 1,
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  }
}
```

### Refrescar Token

**Endpoint:** `POST {{base_url}}/api/v1/auth/refresh`

**Body:**
```json
{
  "refreshToken": "{{refresh_token}}"
}
```

---

## 📚 ENDPOINTS DISPONIBLES

### 1️⃣ 🔐 AUTENTICACIÓN (3 endpoints)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/v1/auth/registro` | Registrar nuevo usuario |
| POST | `/api/v1/auth/login` | Iniciar sesión |
| POST | `/api/v1/auth/refresh` | Refrescar access token |

---

### 2️⃣ 💰 PLANES (7 endpoints)

**Listar todos los planes**
```bash
GET {{base_url}}/api/v1/planes
```

**Obtener plan por ID**
```bash
GET {{base_url}}/api/v1/planes/{id}
```

**Crear nuevo plan**
```bash
POST {{base_url}}/api/v1/planes
```
```json
{
  "nombre": "Plan Básico",
  "descripcion": "Acceso a todas las instalaciones",
  "precioMensual": 49.99,
  "duracionDias": 30,
  "activo": true
}
```

**Actualizar plan**
```bash
PUT {{base_url}}/api/v1/planes/{id}
```

**Eliminar plan (soft delete)**
```bash
DELETE {{base_url}}/api/v1/planes/{id}
```

**Desactivar plan**
```bash
PATCH {{base_url}}/api/v1/planes/{id}/desactivar
```

**Reactivar plan**
```bash
PATCH {{base_url}}/api/v1/planes/{id}/activar
```

---

### 3️⃣ 👥 MIEMBROS (8 endpoints)

**Listar miembros**
```bash
GET {{base_url}}/api/v1/miembros
```

**Obtener miembro por ID**
```bash
GET {{base_url}}/api/v1/miembros/{id}
```

**Crear miembro**
```bash
POST {{base_url}}/api/v1/miembros
```
```json
{
  "nombre": "Juan",
  "apellido": "Gómez",
  "email": "juan@ejemplo.com",
  "telefono": "987654321",
  "numeroMembresia": "MEM001",
  "username": "juan_gomez",
  "usuarioSistema": 1
}
```

**Actualizar miembro**
```bash
PUT {{base_url}}/api/v1/miembros/{id}
```

**Eliminar miembro**
```bash
DELETE {{base_url}}/api/v1/miembros/{id}
```

**Obtener miembro por usuario**
```bash
GET {{base_url}}/api/v1/miembros/usuario/{usuarioId}
```

**Buscar por número de membresía**
```bash
GET {{base_url}}/api/v1/miembros/numero/{numero}
```

**Listar miembros activos**
```bash
GET {{base_url}}/api/v1/miembros/activos
```

---

### 4️⃣ 📅 MEMBRESÍAS (8 endpoints)

**Listar membresías**
```bash
GET {{base_url}}/api/v1/membresias
```

**Obtener membresía por ID**
```bash
GET {{base_url}}/api/v1/membresias/{id}
```

**Crear membresía**
```bash
POST {{base_url}}/api/v1/membresias
```
```json
{
  "miembroId": 1,
  "planId": 1,
  "fechaInicio": "2024-01-15",
  "fechaFin": "2024-02-15",
  "estado": "ACTIVA",
  "precio": 49.99
}
```

**Renovar membresía**
```bash
PATCH {{base_url}}/api/v1/membresias/{id}/renovar
```
```json
{
  "planId": 1
}
```

**Obtener próximas a vencer (7 días)**
```bash
GET {{base_url}}/api/v1/membresias/pendientes/proximas-a-vencer
```

**Membresías por miembro**
```bash
GET {{base_url}}/api/v1/membresias/miembro/{miembroId}
```

**Cambiar estado**
```bash
PATCH {{base_url}}/api/v1/membresias/{id}/estado
```

**Listar activas**
```bash
GET {{base_url}}/api/v1/membresias/activas
```

---

### 5️⃣ 👨‍🏫 INSTRUCTORES (6 endpoints)

**Listar instructores**
```bash
GET {{base_url}}/api/v1/instructores
```

**Obtener instructor por ID**
```bash
GET {{base_url}}/api/v1/instructores/{id}
```

**Crear instructor**
```bash
POST {{base_url}}/api/v1/instructores
```
```json
{
  "nombre": "Roberto",
  "apellido": "Martínez",
  "email": "roberto@ejemplo.com",
  "telefono": "987654321",
  "especialidad": "Musculación",
  "activo": true
}
```

**Actualizar instructor**
```bash
PUT {{base_url}}/api/v1/instructores/{id}
```

**Desactivar instructor**
```bash
PATCH {{base_url}}/api/v1/instructores/{id}/desactivar
```

**Listar activos**
```bash
GET {{base_url}}/api/v1/instructores/activos
```

---

### 6️⃣ 🏋️ CLASES (7 endpoints)

**Listar clases**
```bash
GET {{base_url}}/api/v1/clases
```

**Obtener clase por ID**
```bash
GET {{base_url}}/api/v1/clases/{id}
```

**Crear clase**
```bash
POST {{base_url}}/api/v1/clases
```
```json
{
  "nombre": "Zumba",
  "descripcion": "Clase de Zumba con ritmos latinos",
  "instructorId": 1,
  "fechaHora": "2024-01-20T18:00:00",
  "duracion": 60,
  "capacidadMaxima": 20
}
```

**Actualizar clase**
```bash
PUT {{base_url}}/api/v1/clases/{id}
```

**Eliminar clase**
```bash
DELETE {{base_url}}/api/v1/clases/{id}
```

**Filtrar por rango de fechas**
```bash
GET {{base_url}}/api/v1/clases/filtrar?fechaInicio=2024-01-01&fechaFin=2024-01-31
```

**Listar por instructor**
```bash
GET {{base_url}}/api/v1/clases/instructor/{instructorId}
```

---

### 7️⃣ 📋 ASISTENCIAS (6 endpoints)

**Registrar asistencia**
```bash
POST {{base_url}}/api/v1/asistencias
```
```json
{
  "miembroId": 1,
  "claseId": 1,
  "presente": true
}
```

**Obtener asistencia por ID**
```bash
GET {{base_url}}/api/v1/asistencias/{id}
```

**Filtrar asistencias**
```bash
GET {{base_url}}/api/v1/asistencias/filtrar?miembroId=1&fechaInicio=2024-01-01
```

**Tasa de asistencia de miembro**
```bash
GET {{base_url}}/api/v1/asistencias/{miembroId}/tasa
```

**Listar por clase**
```bash
GET {{base_url}}/api/v1/asistencias/clase/{claseId}
```

**Editar asistencia**
```bash
PUT {{base_url}}/api/v1/asistencias/{id}
```

---

### 8️⃣ 📊 REPORTES (8 endpoints)

**Reporte de membresías activas**
```bash
GET {{base_url}}/api/v1/reportes/membresias/activas
```

**Reporte de membresías próximas a vencer**
```bash
GET {{base_url}}/api/v1/reportes/membresias/proximas-a-vencer
```

**Reporte de membresías vencidas**
```bash
GET {{base_url}}/api/v1/reportes/membresias/vencidas
```

**Reporte por estado**
```bash
GET {{base_url}}/api/v1/reportes/membresias/por-estado
```

**Reporte de asistencias por miembro**
```bash
GET {{base_url}}/api/v1/reportes/asistencias/por-miembro
```

**Reporte de asistencias por clase**
```bash
GET {{base_url}}/api/v1/reportes/asistencias/por-clase
```

**Reporte de tasa de asistencia**
```bash
GET {{base_url}}/api/v1/reportes/asistencias/tasa-promedio
```

**Reporte de ingresos**
```bash
GET {{base_url}}/api/v1/reportes/membresias/ingresos
```

---

## ✅ FLUJOS DE PRUEBA COMPLETOS

### Flujo 1: Crear Miembro con Membresía

```
1. POST /auth/registro → Obtener usuario + token
2. POST /planes → Crear un plan
3. POST /miembros → Crear miembro
4. POST /membresias → Asignar membresía al miembro
5. GET /membresias/{id} → Verificar membresía creada
6. PATCH /membresias/{id}/renovar → Renovar membresía
```

### Flujo 2: Crear y Administrar Clases

```
1. POST /instructores → Crear instructor
2. POST /clases → Crear clase con ese instructor
3. POST /asistencias → Registrar asistencia de miembro
4. GET /reportes/asistencias/por-clase → Ver asistencias
```

### Flujo 3: Obtener Reportes

```
1. GET /reportes/membresias/proximas-a-vencer
2. GET /reportes/asistencias/tasa-promedio
3. GET /reportes/membresias/ingresos
```

---

## 📄 MODELOS DE DATOS (DTOs)

### LoginRequest
```json
{
  "username": "string",
  "password": "string"
}
```

### RegistroRequest
```json
{
  "username": "string",
  "email": "string",
  "password": "string",
  "nombre": "string",
  "apellido": "string"
}
```

### TokenResponse
```json
{
  "id": "integer",
  "accessToken": "string",
  "refreshToken": "string"
}
```

### RespuestaApi (Respuesta estándar)
```json
{
  "success": boolean,
  "statusCode": integer,
  "message": "string",
  "data": "object"
}
```

---

## 🔴 CÓDIGOS DE ERROR

| Código | Descripción |
|--------|-------------|
| 200 | OK - Solicitud exitosa |
| 201 | Created - Recurso creado |
| 400 | Bad Request - Datos inválidos |
| 401 | Unauthorized - Token inválido/expirado |
| 403 | Forbidden - Acceso denegado |
| 404 | Not Found - Recurso no encontrado |
| 409 | Conflict - Recurso duplicado |
| 500 | Internal Server Error |

---

## 📚 Estructura de Carpetas en Postman

```
GMS - Sistema Gestión Gimnasio
├── 🔐 AUTENTICACIÓN
│   ├── Registro (Sign Up)
│   ├── Login
│   └── Refrescar Token
│
├── 💰 PLANES
│   ├── Listar Planes
│   ├── Obtener Plan
│   ├── Crear Plan
│   ├── Actualizar Plan
│   ├── Eliminar Plan
│   ├── Desactivar Plan
│   └── Reactivar Plan
│
├── 👥 MIEMBROS
│   ├── Listar Miembros
│   ├── Obtener por ID
│   ├── Crear Miembro
│   ├── Actualizar
│   ├── Eliminar
│   ├── Por Usuario
│   ├── Por Número
│   └── Activos
│
├── 📅 MEMBRESÍAS
│   ├── Listar
│   ├── Obtener por ID
│   ├── Crear Membresía
│   ├── Renovar
│   ├── Próximas a Vencer
│   ├── Por Miembro
│   ├── Cambiar Estado
│   └── Activas
│
├── 👨‍🏫 INSTRUCTORES
│   ├── Listar Instructores
│   ├── Obtener por ID
│   ├── Crear Instructor
│   ├── Actualizar
│   ├── Desactivar
│   └── Activos
│
├── 🏋️ CLASES
│   ├── Listar Clases
│   ├── Obtener por ID
│   ├── Crear Clase
│   ├── Actualizar
│   ├── Eliminar
│   ├── Filtrar por Fechas
│   └── Por Instructor
│
├── 📋 ASISTENCIAS
│   ├── Registrar
│   ├── Obtener por ID
│   ├── Filtrar
│   ├── Tasa de Asistencia
│   ├── Por Clase
│   └── Editar
│
└── 📊 REPORTES
    ├── Membresías Activas
    ├── Próximas a Vencer
    ├── Vencidas
    ├── Por Estado
    ├── Asistencias por Miembro
    ├── Asistencias por Clase
    ├── Tasa Promedio
    └── Ingresos
```

---

## 💡 TIPS ÚTILES

### Pre-request Script (Ejecutar automáticamente antes de cada request)

Agrega a una carpeta para que todos sus requests usen esto:

```javascript
// Verificar que el token existe
if (!pm.environment.get("access_token")) {
    console.warn("⚠️  No hay token. Ejecuta Login primero");
}

// Timestamp
pm.environment.set("timestamp", new Date().toISOString());
```

### Test (Validar respuestas)

```javascript
// Verificar que fue exitoso
pm.test("Response success", function () {
    pm.response.to.have.status(200);
    pm.expect(pm.response.json().success).to.equal(true);
});

// Guardar token para siguiente request
if (pm.response.json().data.accessToken) {
    pm.environment.set("access_token", pm.response.json().data.accessToken);
    pm.environment.set("refresh_token", pm.response.json().data.refreshToken);
}
```

---

## 🔗 ENLACES ÚTILES

- **Postman:** https://www.postman.com/downloads/
- **Swagger API:** http://localhost:8080/swagger-ui.html
- **Frontend:** http://localhost:4400
- **GitHub JWT (JJWT):** https://github.com/jwtk/jjwt

---

## ✨ ÚLTIMO ACTUALIZADO

- **Fecha:** Enero 2024
- **Status:** ✅ COMPLETADO Y VERIFICADO
- **Versión:** 1.0.0

**Todas las 40+ APIs probadas y funcionando correctamente.**
│   ├── Listar Miembros
│   └── Actualizar Miembro
│
├── 💳 PLANES DE MEMBRESÍA
│   ├── Crear Plan
│   ├── Obtener Plan por ID
│   ├── Listar Planes Activos
│   └── Actualizar Plan
│
├── 🎯 MEMBRESÍAS
│   ├── Crear Membresía
│   ├── Obtener Membresía por ID
│   ├── Renovar Membresía
│   ├── Listar Membresías Activas
│   └── Membresías Próximas a Vencer
│
├── 👨‍🏫 INSTRUCTORES
│   ├── Crear Instructor
│   ├── Obtener Instructor por ID
│   └── Listar Instructores Activos
│
├── 📚 CLASES
│   ├── Crear Clase
│   ├── Obtener Clase por ID
│   ├── Listar Clases por Día
│   └── Listar Clases Activas
│
├── ✅ ASISTENCIA
│   ├── Registrar Asistencia
│   ├── Obtener Asistencia por ID
│   ├── Asistencias por Clase
│   └── Asistencias por Miembro
│
└── 📊 REPORTES
    ├── Reporte de Membresías
    └── Reporte de Asistencia
```

---

## 🧪 Flujo de Pruebas Recomendado

### Secuencia Básica:
1. **Autenticación**
   - POST `/api/v1/auth/login`
   - Guardar `access_token`

2. **Crear Recursos**
   - POST `/api/v1/planes` (crear un plan)
   - POST `/api/v1/instructores` (crear instructor)
   - POST `/api/v1/miembros` (crear miembro)

3. **Vincular Membresía**
   - POST `/api/v1/membresias` (con miembroId y planId)

4. **Crear Clase**
   - POST `/api/v1/clases` (con instructorId y diaSemana)

5. **Registrar Asistencia**
   - POST `/api/v1/asistencias` (con claseId y miembroId)

6. **Consultar Reportes**
   - GET `/api/v1/reportes/membresias`
   - GET `/api/v1/reportes/asistencia`

---

## 💡 Consejos Prácticos

### Auto-guardar Tokens
Para que Postman guarde automáticamente el token después de login:

1. Ir a **Login** (carpeta Autenticación)
2. Hacer clic en pestaña **"Tests"**
3. Agregar este código:
```javascript
var jsonData = pm.response.json();
pm.environment.set("access_token", jsonData.datos.accessToken);
pm.environment.set("refresh_token", jsonData.datos.refreshToken);
```
4. Hacer clic en **"Save"**
5. Ahora cada vez que hagas login, se guarda automáticamente

### Pre-completar IDs
Tras crear un recurso, tomar el `id` de la respuesta:
1. Hacer clic en Variables del ambiente
2. Agregar variables como `miembroId`, `planId`, etc.
3. Usarlas en requests: `{{miembroId}}`

### Historial de Requests
- Postman guarda historial automáticamente
- Ver en **"History"** (izquierda)
- Perfecta para ver requests previos

---

## 🔄 Autenticación en Cada Request

**Todos los endpoints requieren Authorization**:

1. En cada request, ir a pestaña **"Headers"**
2. Verificar que esté presente:
   ```
   Authorization: Bearer {{access_token}}
   ```

❗ **El token expira cada 1 hora**  
Cuando recibas error **401**, refrescar token:
- POST `/api/v1/auth/refresh`
- Body: `{ "refreshToken": "{{refresh_token}}" }`

---

## ❌ Errores Comunes y Soluciones

| Error | Causa | Solución |
|-------|-------|----------|
| **401 Unauthorized** | Token expirado | Refrescar token o hacer login nuevamente |
| **400 Bad Request** | Datos inválidos | Revisar formato JSON y campos requeridos |
| **404 Not Found** | ID no existe | Verificar que el ID sea correcto y exista |
| **409 Conflict** | Recurso duplicado | No duplicar números de membresía o emails |
| **Connection refused** | API no corre | Iniciar API: `mvn spring-boot:run` |

---

## 📖 Documentación Completa

Para información detallada sobre cada endpoint:
👉 **Revisar: `API_MANUAL_DE_USO.md`**

Incluye:
- ✅ Descripción de cada endpoint
- ✅ Ejemplos de requests/responses
- ✅ Códigos HTTP explicados
- ✅ Flujos prácticos paso a paso
- ✅ Tabla de roles y permisos

---

## 🛠️ Iniciar la API (Línea de Comandos)

```bash
# Compilar
mvn clean compile

# Ejecutar
mvn spring-boot:run

# La API estará disponible en: http://localhost:8080
```

---

## 📞 Endpoints Principales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/v1/auth/login` | Autenticar usuario |
| POST | `/api/v1/miembros` | Crear nuevo miembro |
| POST | `/api/v1/membresias` | Asignar membresía |
| POST | `/api/v1/clases` | Crear clase |
| POST | `/api/v1/asistencias` | Registrar asistencia |
| GET | `/api/v1/reportes/membresias` | Reporte de membresías |
| GET | `/api/v1/reportes/asistencia` | Reporte de asistencia |

---

## ✅ Checklist de Uso

- [ ] Descargar e importar `GMS_Postman_Collection.json`
- [ ] Crear ambiente `GMS Development`
- [ ] Configurar variables (`base_url`, tokens)
- [ ] Hacer login
- [ ] Copiar `access_token` al ambiente
- [ ] Probar endpoints de la colección
- [ ] Revisar `API_MANUAL_DE_USO.md` para detalles
- [ ] Hacer reportes

---

**API Versión**: 1.0  
**Última Actualización**: 13 de abril de 2026  
**Estado**: ✅ Completo y Funcional
