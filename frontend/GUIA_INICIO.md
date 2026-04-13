# Guía de Inicio - Frontend Angular

## 🎯 Objetivo
Frontend web para consumir REST API del Sistema Gestión Gimnasio (Backend).

---

## 📥 Instalación Rápida

### 1. Instalar Node.js
- Descargar: https://nodejs.org/ (v20 o superior)
- Verificar instalación:
```bash
node --version
npm --version
```

### 2. Clonar/Descargar Proyecto
```bash
cd d:\PROYECTOS\SistemaGimnasio\frontend
```

### 3. Instalar Dependencias
```bash
npm install
```
(tarda 3-5 minutos en primera instalación)

### 4. Iniciar Servidor
```bash
npm start
```

Abrirá automáticamente `http://localhost:4200` en el navegador.

---

## ✅ Verificar que Todo Funciona

1. Página de **Login** debe cargar
2. Usuario: `admin` / Password: `password123`
3. Click **Acceder** → Ir a Dashboard
4. Ver estadísticas y menú lateral
5. Navegar a **Miembros** → Cargar tabla

**✅ Si todo funciona, ¡el frontend está listo!**

---

## 🛑 Prerequisitos: Backend Ejecutándose

El frontend necesita el backend ejecutándose:

```bash
cd d:\PROYECTOS\SistemaGimnasio
mvn clean spring-boot:run
```

Backend debe estar en: `http://localhost:8080`

---

## 📁 Estructura Principal

```
frontend/
├── src/
│   ├── app/
│   │   ├── componentes/     # Pantallas (Login, Dashboard, Miembros, etc)
│   │   ├── servicios/       # Conexión con Backend
│   │   ├── interceptores/   # Token JWT automático
│   │   └── rutas/           # Navegación
│   ├── styles.css           # Estilos globales
│   └── main.ts              # Punto entrada
├── package.json             # Dependencias
├── angular.json             # Configuración Angular
└── README.md                # Documentación
```

---

## 🚀 Comandos Útiles

```bash
# Iniciar desarrollo
npm start

# Construir para producción
npm run build:prod

# Ejecutar tests
npm test

# Validar código
npm run lint

# Actualizar dependencias
npm update
```

---

## 🎨 Pantallas Disponibles

| Pantalla | Descripción | Ruta |
|----------|------------|------|
| Login | Autenticación usuario | `/login` |
| Dashboard | Principal con estadísticas | `/dashboard` |
| Miembros | CRUD de miembros | `/miembros` |
| Membresías | Gestión de membresías | `/membresias` |

---

## 🔌 Conectado con Backend

El frontend automaticamente:
- Envía **Token JWT** en cada petición
- Redirige a Login si sesión expira
- Valida respuestas del backend

---

## 🐛 Errores Comunes

### ❌ Error: "Cannot find module '@angular/core'"
**Solución**: `npm install`

### ❌ Error: "Port 4200 already in use"
**Solución**: `ng serve --port 4300`

### ❌ Error: "Backend not reachable (404)"
**Solución**: Verificar que Backend esté ejecutándose en puerto 8080

---

## 📊 Próximas Adiciones

- Componentes para Planes, Instructores, Clases
- Paginación y búsqueda
- Gráficos estadísticos
- Exportar a PDF
- Dark mode

---

**¡Listo para desarrollar! 🚀**
