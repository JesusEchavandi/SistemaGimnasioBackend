# 🗄️ Base de Datos Unificada - GMS.sql

## 📋 Descripción

**`GMS.sql`** es un archivo SQL único y completo que contiene:

✅ Creación de la base de datos `gms`  
✅ Creación de 8 tablas con estructura completa  
✅ Relaciones (Foreign Keys) entre tablas  
✅ Índices optimizados para performance  
✅ Usuario administrador inicial (admin / admin123)  
✅ Charset UTF8MB4 para soportar caracteres especiales  

---

## 🚀 Cómo Usar en Otra Computadora

### Requisitos Previos

✓ MySQL 5.5+ instalado (recomendado 8.0+)  
✓ Acceso a la línea de comandos MySQL  
✓ Usuario root de MySQL con permisos de administrador  

---

### Opción 1: Desde Línea de Comandos (Recomendado)

**Windows (PowerShell o CMD):**
```powershell
# Navega a la carpeta del proyecto (donde está GMS.sql)
cd D:\Ruta\Donde\Este\GMS.sql

# Ejecutar script SQL
mysql -u root < GMS.sql

# O con contraseña (si lo requiere)
mysql -u root -p < GMS.sql
```

**Linux/Mac:**
```bash
cd /ruta/donde/este/GMS.sql
mysql -u root < GMS.sql

# O con contraseña
mysql -u root -p < GMS.sql
```

---

### Opción 2: Desde MySQL Workbench

1. **Abrir MySQL Workbench**
2. **File** → **Open SQL Script**
3. Seleccionar **`GMS.sql`**
4. Hacer click en el botón ⚡ **Execute** (o **Ctrl+Shift+Enter**)
5. Confirmación: `Query OK, X rows affected`

---

### Opción 3: Desde Script PowerShell

```powershell
$sqlPath = "D:\Ruta\GMS.sql"
$mySqlPath = "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"

& $mySqlPath -u root < $sqlPath
echo "✅ Base de datos GMS importada exitosamente"
```

---

## ✅ Verificar Instalación

Después de ejecutar GMS.sql, verifica que todo está correcto:

### Verificar BD existe
```sql
SHOW DATABASES LIKE 'gms';
```

### Verificar tablas creadas
```sql
USE gms;
SHOW TABLES;
```

**Resultado esperado:** 9 tablas
- usuarios_sistema
- planes
- instructores
- miembros
- membresias
- clases
- asistencias
- tokens_refresco

### Verificar usuario admin
```sql
USE gms;
SELECT id, nombre_usuario, correo, rol, activo FROM usuarios_sistema WHERE nombre_usuario='admin';
```

**Resultado esperado:**
```
| id | nombre_usuario | correo         | rol   | activo |
|----|----------------|----------------|-------|--------|
| 1  | admin          | admin@gms.com  | ADMIN | 1      |
```

---

## 🔐 Credenciales Iniciales

| Campo | Valor |
|-------|-------|
| **Usuario** | `admin` |
| **Contraseña** | `admin123` |
| **Email** | `admin@gms.com` |
| **Rol** | `ADMIN` |

---

## 📊 Estructura de Tablas

### 1. usuarios_sistema
```sql
-- Campos principales
id (PK), nombre_usuario (UNIQUE), correo (UNIQUE), clave (BCrypt),
rol (ADMIN/RECEPCIONISTA/MIEMBRO), activo, timestamps, creado_por
```

### 2. planes
```sql
id (PK), nombre (UNIQUE), descripcion, precio_mensual, duracion_dias,
activo, timestamps, creado_por
```

### 3. instructores
```sql
id (PK), usuario_id (FK UNIQUE), especialidad, certificaciones,
activo, timestamps, creado_por
```

### 4. miembros
```sql
id (PK), usuario_id (FK UNIQUE), numero_membresia (UNIQUE),
telefono_contacto, fecha_nacimiento, sexo, telefono_emergencia,
timestamps, creado_por
```

### 5. membresias
```sql
id (PK), miembro_id (FK), plan_id (FK), fecha_inicio, fecha_vencimiento,
estado (ACTIVA/VENCIDA/CANCELADA), timestamps, creado_por
Constraint UNIQUE: (miembro_id, fecha_inicio)
```

### 6. clases
```sql
id (PK), nombre, instructor_id (FK), dia_semana,
hora_inicio, duracion_minutos, capacidad, estado,
timestamps, creado_por
```

### 7. asistencias
```sql
id (PK), clase_id (FK), miembro_id (FK), fecha, asistio,
hora_llegada, timestamps, creado_por
Constraint UNIQUE: (clase_id, miembro_id, fecha)
```

### 8. tokens_refresco
```sql
id (PK), token (UNIQUE), usuario_id (FK), expira_en,
revocado, timestamps, creado_por
```

---

## 🔧 Características de la BD

### ✅ Auditoría Completa
Todas las tablas incluyen:
- `creado_en` - Timestamp de creación (auto)
- `actualizado_en` - Timestamp de modificación (auto)
- `creado_por` - Usuario que creó el registro

### ✅ Índices Optimizados
Índices en:
- Foreign Keys (para joins rápidos)
- Campos únicos
- Campos de búsqueda frecuente (nombre_usuario, estado, fecha, etc.)

### ✅ Constraints de Integridad
- `UNIQUE` en campos que no pueden duplicarse
- `FOREIGN KEY` con `RESTRICT` para evitar eliminaciones accidentales
- `NOT NULL` en campos obligatorios

### ✅ Charset UTF8MB4
Soporte completo para:
- Caracteres latinos con acentos (á, é, í, ó, ú)
- Caracteres especiales (ñ, ü, ç)
- Emojis (si se necesita en el futuro)

---

## 🔄 Migración desde Otra BD

Si tienes datos previos en otra base de datos y necesitas migrar a GMS:

1. **Respalda la BD anterior:**
   ```bash
   mysqldump -u root -p gms_vieja > gms_backup.sql
   ```

2. **Importa la nueva estructura GMS:**
   ```bash
   mysql -u root < GMS.sql
   ```

3. **Migra datos con cuidado:**
   - Ejecuta queries de INSERT/SELECT desde tu DB antigua
   - Valida las FK y constraints
   - Verifica integridad referencial

---

## 🔑 Cambiar Contraseña Admin

Si necesitas cambiar la contraseña del admin después de importar:

```sql
USE gms;

-- Opción 1: Actualizar contraseña (necesitas hash BCrypt)
UPDATE usuarios_sistema SET clave='[NUEVO_HASH_BCRYPT]' WHERE nombre_usuario='admin';

-- Opción 2: Usar aplicación (recomendado)
-- Login en la aplicación y cambiar desde el UI
```

Para generar hash BCrypt online: https://bcrypt.online/

---

## 🐛 Troubleshooting

### "Error: Access denied for user 'root'"
```powershell
# Intenta con contraseña
mysql -u root -p < GMS.sql
# O especifica host
mysql -h localhost -u root -p < GMS.sql
```

### "Error: Database 'gms' already exists"
```sql
-- GMS.sql ya incluye DROP DATABASE IF EXISTS gms
-- Ejecuta el script nuevamente y se sobrescribirá
```

### "Error: Table 'gms.usuarios_sistema' doesn't exist"
```powershell
# Verifica que el script se ejecutó completamente
# Intenta nuevamente:
mysql -u root < GMS.sql
```

---

## 📝 Notas Importantes

1. **GMS.sql es idempotente:**
   - Puedes ejecutarlo múltiples veces
   - Automáticamente elimina BD anterior y crea nueva
   - No duplica datos

2. **Contenido del usuario admin:**
   - La contraseña `admin123` es BCrypt hasheada
   - Es seguro en tránsito (nunca se envía en plano)
   - Frontend y backend usan JWT para sesiones

3. **Producción:**
   - Cambia la contraseña del admin  
   - Asegúrate de tener backups regulares
   - Configura replicación de BD si necesitas HA

---

## ✅ Checklist Post-Instalación

- [ ] BD `gms` creada
- [ ] 8 tablas creadas con estructura correcta
- [ ] Usuario admin presente y funcional
- [ ] Índices creados
- [ ] Foreign keys validadas
- [ ] Charset UTF8MB4 configurado
- [ ] Script probado desde otra computadora

---

## 📞 Soporte

Si encuentras problemas:

1. Verifica que MySQL está corriendo: `mysqlcheck -u root`
2. Valida sintaxis SQL: revisa mensajes de error
3. Intenta con `mysql -u root -v < GMS.sql` para ver queries ejecutadas

---

**Versión:** 1.0.0  
**Última actualización:** 13 de abril 2026  
**Estado:** ✅ Producción Ready
