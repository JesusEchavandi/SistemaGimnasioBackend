# Frontend Angular - Sistema Gestión Gimnasio

Frontend web construido con **Angular 18** para consumir la REST API del backend.

---

## 📋 Requisitos Previos

- **Node.js 20+**
- **npm 10+**
- **Angular CLI 18**
- Backend ejecutándose en `http://localhost:8080`

---

## 🚀 Instalación y Ejecución

### 1. Instalar Dependencias

```bash
cd frontend
npm install
```

### 2. Iniciar Servidor de Desarrollo

```bash
npm start
```

La aplicación estará disponible en `http://localhost:4200`

### 3. Construir para Producción

```bash
npm run build:prod
```

Los archivos compilados estarán en `dist/gms-frontend`

---

## 🏗️ Estructura del Proyecto

```
frontend/
├── src/
│   ├── app/
│   │   ├── componentes/          # Componentes principales
│   │   │   ├── autenticacion/    # Login y Registro
│   │   │   ├── dashboard/        # Dashboard principal
│   │   │   ├── miembros/         # Gestión de miembros
│   │   │   └── membresias/       # Gestión de membresías
│   │   ├── servicios/            # Servicios HTTP
│   │   │   ├── api.servicio.ts   # Cliente API REST
│   │   │   └── autenticacion.servicio.ts  # Gestión auth
│   │   ├── interceptores/        # Interceptores HTTP
│   │   │   └── jwt.interceptor.ts # Agregar token JWT
│   │   ├── rutas/                # Configuración routing
│   │   ├── app.module.ts         # Módulo raíz
│   │   ├── app.component.ts      # Componente raíz
│   │   └── app-routing.module.ts
│   ├── styles.css                # Estilos globales
│   ├── index.html                # HTML principal
│   └── main.ts                   # Punto de entrada
├── package.json
├── angular.json
├── tsconfig.json
└── README.md                     # Este archivo
```

---

## 🎯 Funcionalidades Implementadas

### ✅ Autenticación
- Login con usuario y contraseña
- Registro de nuevos usuarios
- Token JWT almacenado en localStorage
- Refresh automático de tokens
- Cierre de sesión

### ✅ Dashboard
- Vista principal con estadísticas
- Tarjetas de información (Miembros, Membresías, Instructores, Clases)
- Acceso rápido a funcionalidades principales
- Menú lateral de navegación

### ✅ Estión de Miembros
- Listar todos los miembros
- Crear nuevo miembro
- Ver detalles del miembro
- Editar miembro (preparado)
- Eliminar miembro (preparado)

### ✅ Gestión de Membresías
- Listar membresías activas/vencidas
- Crear nueva membresía
- Renovar membresía
- Ver próximas a vencer
- Estado visual (ACTIVA/VENCIDA)

### ✅ Componentes Adicionales (Plantillas)
- Planes de membresía
- Instructores
- Clases
- Reportes

---

## 🔌 Integración Backend

El frontend se conecta al backend en:

```
Backend URL: http://localhost:8080/api/v1
```

### Peticiones Autenticadas

Todos los requests incluyen header:
```
Authorization: Bearer {access_token}
```

Esto se hace automáticamente via `jwt.interceptor.ts`

---

## 🎨 Estilos y Diseño

- **Estilos Globales**: `src/styles.css`
- **Variables CSS**:
  - `--color-primario`: #2196F3 (Azul)
  - `--color-secundario`: #FF5722 (Naranja)
  - `--color-exito`: #4CAF50 (Verde)
  - `--color-error`: #f44336 (Rojo)

### Componentes Estilizados

- Botones con hovering
- Tarjetas con sombra
- Formularios responsivos
- Tablas con zebra-striping
- Alertas (exito, error, info, advertencia)
- Badges

---

## 📱 Responsividad

El diseño es **mobile-first** y se adapta a:
- 📱 Teléfonos (< 600px)
- 📱 Tablets (600px - 768px)
- 💻 Desktop (> 768px)

---

## 🔒 Seguridad

### Autenticación
- Tokens JWT en localStorage
- Interceptor HTTP para headers
- Guard de autenticación en rutas privadas

### Solicitudes a API
- CORS configurado en backend
- SSL/TLS en producción
- Tokens con expiración (1 hora)

---

## 🛠️ Comandos Disponibles

| Comando | Descripción |
|---------|-------------|
| `npm start` | Inicia servidor desarrollo (puerto 4200) |
| `npm run build` | Construye para desarrollo |
| `npm run build:prod` | Construye para producción |
| `npm test` | Ejecuta tests unitarios |
| `npm run lint` | Valida código con linter |

---

## 📚 Servicios Disponibles

### ApiServicio

```typescript
// Autenticación
login(usuario: string, password: string)
registrarse(datos: any)
refrescarToken(refreshToken: string)

// Miembros
listarMiembros()
crearMiembro(datos: any)
actualizarMiembro(id: number, datos: any)
eliminarMiembro(id: number)

// Membresías
listarMembresias()
crearMembresia(datos: any)
renovarMembresia(id: number)
membresiasProximasVencer()

// Más servicios...
```

### AutenticacionServicio

```typescript
guardarTokens(accessToken: string, refreshToken: string)
obtenerToken(): string | null
estaAutenticado(): boolean
guardarUsuario(usuario: any)
cerrarSesion()
```

---

## 🚀 Próximas Mejoras

- [ ] Componentes adicionales (Planes, Instructores, Clases)
- [ ] Paginación en tablas
- [ ] Búsqueda y filtros
- [ ] Edición inline de datos
- [ ] Confirmación de eliminar
- [ ] Exportar a PDF/Excel
- [ ] Gráficos y charts
- [ ] Tema oscuro (dark mode)
- [ ] Internacionalización (i18n)
- [ ] Unit tests funcionales
- [ ] Pruebas E2E

---

## 🐛 Troubleshooting

### Error de CORS
```
Access-Control-Allow-Origin missing
```
**Solución**: Verificar `ConfiguracionSeguridad.java` en backend tenga:
```java
@CrossOrigin(origins = "http://localhost:4200")
```

### Token401 (No autorizado)
```
status: 401, message: "Unauthorized"
```
**Solución**: 
1. Verificar token en localStorage
2. Hacer login nuevamente
3. Revisar expiración token (1 hora)

### No se cargan datos
```
GET http://localhost:8080/api/v1/... 404
```
**Solución**:
1. Verificar backend está ejecutándose
2. Revisar URL base en `api.servicio.ts`
3. Consultar logs del backend

---

## 📖 Documentación Adicional

- [Angular Docs](https://angular.io/docs)
- [HttpClient Guide](https://angular.io/guide/http)
- [Routing](https://angular.io/guide/routing-overview)
- [Forms](https://angular.io/guide/forms)

---

## 👥 Autor

Proyecto desarrollado para **Sistema Gestión Gimnasio**

**Última actualización**: 13 abril 2026
