# 🏋️ Sistema de Gestión de Gimnasio (GMS)

**Sistema integral de gestión para gimnasios** con autenticación JWT, gestión de membresías, clases, instructores y reportes analíticos.

[![Python](https://img.shields.io/badge/Java-21-ED8B00?style=flat&logo=java)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.13-6DB33F?style=flat&logo=springboot)](https://spring.io/projects/spring-boot)
[![Angular](https://img.shields.io/badge/Angular-18.2.21-DD0031?style=flat&logo=angular)](https://angular.io/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=flat&logo=mysql)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

---

## 📋 Tabla de Contenidos

- [Características](#características)
- [Stack Tecnológico](#stack-tecnológico)
- [Requisitos Previos](#requisitos-previos)
- [Instalación Rápida](#instalación-rápida)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Endpoints API](#endpoints-api)
- [Documentación](#documentación)
- [Contribuciones](#contribuciones)

---

## ✨ Características

### 🔐 Autenticación
- ✅ Registro de usuarios
- ✅ Login con JWT
- ✅ Refresh tokens (7 días)
- ✅ Roles: ADMIN, RECEPCIONISTA, MIEMBRO

### 💰 Gestión de Planes
- ✅ CRUD completo de planes
- ✅ Precios y duraciones configurables
- ✅ Activar/desactivar planes

### 👥 Miembros
- ✅ Registro de miembros
- ✅ Número de membresía único
- ✅ Búsqueda avanzada
- ✅ Gestión de datos personales

### 📅 Membresías
- ✅ Asignación de planes a miembros
- ✅ Renovación automática
- ✅ Estados (Activa, Vencida, Cancelada)
- ✅ Alertas de próximas a vencer

### 👨‍🏫 Instructores
- ✅ Gestión de instructores
- ✅ Especialidades y certificaciones
- ✅ Activar/desactivar

### 🏋️ Clases
- ✅ Horarios y capacidades configurables
- ✅ Asignación de instructores
- ✅ Filtrado por período

### 📋 Asistencias
- ✅ Registro de asistencias
- ✅ Control de tasa de asistencia
- ✅ Reportes por miembro y clase

### 📊 Reportes
- ✅ Membresías activas/vencidas
- ✅ Análisis de asistencias
- ✅ Ingresos por membresías
- ✅ Estadísticas por período

---

## 🛠️ Stack Tecnológico

### Backend
- **Framework:** Spring Boot 3.5.13
- **Java:** 21 LTS
- **Seguridad:** JWT (JJWT 0.12.6)
- **ORM:** JPA/Hibernate
- **Migraciones:** Flyway
- **Build:** Maven

### Frontend
- **Framework:** Angular 18.2.21
- **Lenguaje:** TypeScript 5.6
- **HTTP:** RxJS 7.8
- **Routing:** Angular Router
- **Guards:** Protección de rutas con JWT

### Base de Datos
- **Motor:** MySQL 8.0
- **Charset:** UTF8MB4
- **Migraciones:** Versionadas con Flyway
- **Tablas:** 9 (con auditoría completa)

---

## 📦 Requisitos Previos

| Componente | Versión | Descarga |
|-----------|---------|----------|
| Java JDK | 21+ | [java.com](https://www.java.com/) |
| Maven | 3.6+ | [maven.apache.org](https://maven.apache.org/) |
| Node.js | 18+ | [nodejs.org](https://nodejs.org/) |
| npm | 9+ | (viene con Node.js) |
| MySQL | 8.0+ | [mysql.com](https://www.mysql.com/) |
| Git | - | [git-scm.com](https://git-scm.com/) |

---

## 🚀 Instalación Rápida

### 1️⃣ Clonar Repositorio

```bash
git clone https://github.com/JesusEchavandi/SistemaGimnasioBackend.git
cd SistemaGimnasioBackend
```

### 2️⃣ Crear Base de Datos

```bash
# Opción A: Directamente con MySQL
mysql -u root < GMS.sql

# Opción B: Desde MySQL Workbench
# File → Open SQL Script → Selecciona GMS.sql → Execute
```

**Verificación:**
```bash
mysql -u root -e "USE gms; SHOW TABLES;"
```

### 3️⃣ Ejecutar Backend

```bash
# Terminal 1
mvn clean spring-boot:run

# Backend estará en: http://localhost:8080
# Swagger: http://localhost:8080/swagger-ui.html
```

### 4️⃣ Ejecutar Frontend

```bash
# Terminal 2
cd frontend
npm install --legacy-peer-deps
npm start

# Frontend estará en: http://localhost:4400
```

### 5️⃣ Acceder al Sistema

```
URL: http://localhost:4400
Usuario: admin
Contraseña: admin123
```

---

## 📂 Estructura del Proyecto

```
SistemaGimnasioBackend/
├── src/main/java/com/gimnasio/gms/
│   ├── GmsApplication.java
│   ├── config/                          # Configuración (Security, OpenAPI, Auditoría)
│   ├── comun/                          # Código compartido (DTOs, excepciones, auditoría)
│   ├── seguridad/                      # Autenticación JWT
│   │   ├── controlador/                # AutenticacionControlador
│   │   ├── servicio/                   # JwtServicio, AutenticacionServicio
│   │   ├── dto/                        # LoginRequest, TokenResponse
│   │   └── entidad/                    # UsuarioSistema, TokenRefresco
│   ├── membresias/                     # Gestión de membresías
│   ├── clases/                         # Gestión de clases e instructores
│   ├── instructores/                   # Gestión de instructores
│   ├── reportes/                       # Reportes y analytics
│   └── dashboard/                      # Dashboard del sistema
│
├── frontend/
│   ├── src/app/
│   │   ├── componentes/                # 8 componentes principales
│   │   ├── servicios/                  # ApiServicio, AutenticacionServicio
│   │   ├── interceptores/              # JwtInterceptor
│   │   ├── guardias/                   # AutenticacionGuardia
│   │   └── rutas/                      # Configuración de rutas
│   ├── package.json
│   └── angular.json
│
├── GMS.sql                             # Base de datos completa
├── pom.xml                             # Dependencias Maven
├── GUIA_POSTMAN_README.md              # Documentación Postman
├── README_GMS_SQL.md                   # Instrucciones BD
└── README.md                           # Este archivo
```

---

## 🔌 Endpoints API

### 🔐 Autenticación (3)

```
POST   /api/v1/auth/registro             Registrar nuevo usuario
POST   /api/v1/auth/login                Iniciar sesión
POST   /api/v1/auth/refresh              Refrescar token
```

### 💰 Planes (7)

```
GET    /api/v1/planes                    Listar todos
GET    /api/v1/planes/{id}               Obtener por ID
POST   /api/v1/planes                    Crear
PUT    /api/v1/planes/{id}               Actualizar
DELETE /api/v1/planes/{id}               Eliminar
PATCH  /api/v1/planes/{id}/desactivar    Desactivar
PATCH  /api/v1/planes/{id}/activar       Activar
```

### 👥 Miembros (8)

```
GET    /api/v1/miembros                  Listar
GET    /api/v1/miembros/{id}             Obtener por ID
POST   /api/v1/miembros                  Crear
PUT    /api/v1/miembros/{id}             Actualizar
DELETE /api/v1/miembros/{id}             Eliminar
GET    /api/v1/miembros/usuario/{id}     Por usuario
GET    /api/v1/miembros/numero/{numero}  Por número membresía
GET    /api/v1/miembros/activos          Activos
```

### 📅 Membresías (8)

```
GET    /api/v1/membresias                Listar
GET    /api/v1/membresias/{id}           Obtener por ID
POST   /api/v1/membresias                Crear
PATCH  /api/v1/membresias/{id}/renovar   Renovar
GET    /api/v1/membresias/pendientes/proximas-a-vencer
PATCH  /api/v1/membresias/{id}/estado    Cambiar estado
GET    /api/v1/membresias/miembro/{id}   Por miembro
GET    /api/v1/membresias/activas        Activas
```

### 👨‍🏫 Instructores (6)

```
GET    /api/v1/instructores              Listar
GET    /api/v1/instructores/{id}         Obtener por ID
POST   /api/v1/instructores              Crear
PUT    /api/v1/instructores/{id}         Actualizar
PATCH  /api/v1/instructores/{id}/desactivar
GET    /api/v1/instructores/activos      Activos
```

### 🏋️ Clases (7)

```
GET    /api/v1/clases                    Listar
GET    /api/v1/clases/{id}               Obtener por ID
POST   /api/v1/clases                    Crear
PUT    /api/v1/clases/{id}               Actualizar
DELETE /api/v1/clases/{id}               Eliminar
GET    /api/v1/clases/filtrar            Filtrar por fechas
GET    /api/v1/clases/instructor/{id}    Por instructor
```

### 📋 Asistencias (5)

```
POST   /api/v1/asistencias               Registrar
GET    /api/v1/asistencias/{id}          Obtener por ID
GET    /api/v1/asistencias/filtrar       Filtrar
GET    /api/v1/asistencias/{id}/tasa     Tasa asistencia
PUT    /api/v1/asistencias/{id}          Editar
```

### 📊 Reportes (8)

```
GET    /api/v1/reportes/membresias/activas
GET    /api/v1/reportes/membresias/proximas-a-vencer
GET    /api/v1/reportes/membresias/vencidas
GET    /api/v1/reportes/membresias/por-estado
GET    /api/v1/reportes/asistencias/por-miembro
GET    /api/v1/reportes/asistencias/por-clase
GET    /api/v1/reportes/asistencias/tasa-promedio
GET    /api/v1/reportes/membresias/ingresos
```

**Total: 40+ endpoints RESTful**

---

## 📚 Documentación

### Documentación Oficial

| Documento | Descripción |
|-----------|-------------|
| [GUIA_POSTMAN_README.md](GUIA_POSTMAN_README.md) | Guía completa para Postman con 40+ ejemplos |
| [README_GMS_SQL.md](README_GMS_SQL.md) | Instrucciones para usar GMS.sql en otra PC |
| [RESUMEN_BASE_DATOS.md](RESUMEN_BASE_DATOS.md) | Resumen de estructuras y cambios |
| [Swagger UI](http://localhost:8080/swagger-ui.html) | Documentación interactiva de APIs |

### Postman Collection

Importa la colección para probar todos los endpoints:
1. Abre Postman
2. Click **Import** → Selecciona `GMS-API-Collection.postman_collection.json`
3. ¡Listo para probar!

### Ejemplo de Request

```bash
# Login
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'

# Respuesta
{
  "success": true,
  "data": {
    "id": 1,
    "accessToken": "eyJhbGc...",
    "refreshToken": "eyJhbGc..."
  }
}
```

---

## 🔐 Autenticación

### Credenciales Iniciales

```
Usuario:     admin
Contraseña:  admin123
Email:       admin@gms.com
Rol:         ADMIN
```

### Flujo JWT

1. **Login** → Recibe `accessToken` y `refreshToken`
2. **Usar Token** → En header `Authorization: Bearer {accessToken}`
3. **Token Expira** → Usar `refreshToken` para obtener uno nuevo
4. **Seguridad** → Token expira en 60 minutos

---

## 🔧 Configuración

### Backend (application-dev.yml)

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/gms
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update

jwt:
  secret: TuSecretoSuperSeguroAqui123!@#$%^&*
  expiration: 3600000        # 60 minutos
  refreshExpiration: 604800000  # 7 días
```

### Frontend (environment.ts)

```typescript
export const environment = {
  apiUrl: 'http://localhost:8080/api/v1',
  jwtTimeout: 3600000  // 60 minutos
};
```

---

## 📈 Características Técnicas

### 🏗️ Arquitectura

```
Controller → Service → Repository → Entity
    ↓         ↓         ↓           ↓
  DTO      Logic      Query       DB
```

### 🔒 Seguridad

- ✅ Autenticación JWT
- ✅ CORS configurado para desarrollo
- ✅ Validación de inputs con Jakarta
- ✅ Manejo centralizado de excepciones
- ✅ Hashing seguro de contraseñas (BCrypt)

### 📊 Base de Datos

- ✅ 9 tablas con relaciones
- ✅ Foreign Keys con RESTRICT
- ✅ Índices optimizados
- ✅ Auditoría en todas las tablas
- ✅ Charset UTF8MB4

### 🧪 Testing

```bash
# Compilar
mvn clean compile

# Tests unitarios
mvn test

# Build completo
mvn clean package
```

---

## 🔄 Desarrollo Continuo

### Compilación Rápida

```bash
# Compilar sin tests
mvn clean compile

# Compilar con tests
mvn clean compile test
```

### Hot Reload

```bash
# Spring Boot detecta cambios automáticamente en desarrollo
# Solo recarga clases modificadas
```

### Lint y Formato

```bash
# Frontend - Lint
cd frontend && npm run lint

# Frontend - Format
npm run format
```

---

## 📋 Requisitos Funcionales

- [x] Autenticación CRUD con JWT
- [x] Gestión de planes
- [x] Gestión de miembros
- [x] Gestión de membresías
- [x] Gestión de instructores
- [x] Gestión de clases
- [x] Registro de asistencias
- [x] Reportes completos
- [x] API RESTful 40+
- [x] Frontend Angular responsive
- [x] Base de datos normalizada

---

## 🐛 Troubleshooting

### Backend no inicia

```bash
# Verificar MySQL corriendo
mysql -u root -e "SELECT 1;"

# Verificar puerto 8080 disponible
netstat -ano | findstr :8080

# Limpiar y recompilar
mvn clean compile spring-boot:run
```

### Frontend no compila

```bash
# Limpiar node_modules
rm -rf node_modules
npm install --legacy-peer-deps

# Reiniciar servidor
npm start
```

### BD no se conecta

```bash
# Verificar BD existe
mysql -u root -e "SHOW DATABASES LIKE 'gms';"

# Recrear BD
mysql -u root < GMS.sql
```

---

## 💡 Tips de Desarrollo

### 1. Usar Postman Collection

Importa la collection para probar endpoints rápidamente sin escribir código.

### 2. Swagger API

Accede a `http://localhost:8080/swagger-ui.html` para ver endpoints documentados.

### 3. Base de Datos

Toda la estructura está en `GMS.sql` - ejecuta una sola vez.

### 4. Credenciales

Admin viene preconfigurado: `admin` / `admin123`

---

## 🤝 Contribuciones

¡Las contribuciones son bienvenidas! 

1. Fork el repositorio
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

---

## 📄 Licencia

Este proyecto está bajo la licencia MIT. Ver archivo [LICENSE](LICENSE) para más detalles.

---

## 👨‍💻 Autor

**Jesús Echavandi**
- GitHub: [@JesusEchavandi](https://github.com/JesusEchavandi)
- Proyecto: Sistema de Gestión de Gimnasio (GMS)

---

## 📞 Soporte

¿Preguntas o problemas?

1. Revisa la [documentación](GUIA_POSTMAN_README.md)
2. Abre un [Issue](https://github.com/JesusEchavandi/SistemaGimnasioBackend/issues)
3. Consulta [README_GMS_SQL.md](README_GMS_SQL.md) para BD

---

## 🎯 Roadmap

- [ ] Autenticación OAuth2
- [ ] Panel de administrador avanzado
- [ ] Exportar reportes a PDF/Excel
- [ ] Aplicación móvil nativa
- [ ] Notificaciones por email
- [ ] Sistema de pagos integrado
- [ ] Multi-idioma (es/en)

---

## ✅ Status

**Versión:** 1.0.0  
**Fecha:** 13 de abril 2026  
**Estado:** ✅ **Producción Ready**

- [x] Backend compilado y testeado
- [x] Frontend compilado y funcional
- [x] Base de datos completa
- [x] Documentación 100%
- [x] API 40+ endpoints
- [x] GitHub public

---

**¡Gracias por usar GMS!** 🏋️‍♂️

Hecho con ❤️ en Spring Boot + Angular
