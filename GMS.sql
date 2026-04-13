-- ===============================================
-- BASE DE DATOS UNIFICADA - GMS
-- Sistema de Gestión de Gimnasio
-- Versión: 1.0.0
-- Fecha: 13 de abril 2026
-- Descripción: Script completo con estructura de BD,
-- tablas y datos iniciales (usuario admin)
-- ===============================================

-- 1. Crear Base de Datos
-- ===============================================
DROP DATABASE IF EXISTS gms;
CREATE DATABASE gms 
  CHARACTER SET utf8mb4 
  COLLATE utf8mb4_unicode_ci;

USE gms;

-- ===============================================
-- 2. TABLA: usuarios_sistema
-- Descripción: Usuarios del sistema
-- ===============================================
CREATE TABLE usuarios_sistema (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre_usuario VARCHAR(120) NOT NULL UNIQUE COMMENT 'Nombre de usuario único',
  correo VARCHAR(180) NOT NULL UNIQUE COMMENT 'Email del usuario',
  clave LONGTEXT NOT NULL COMMENT 'Contraseña hasheada (BCrypt)',
  rol VARCHAR(30) NOT NULL DEFAULT 'MIEMBRO' COMMENT 'Rol: ADMIN, RECEPCIONISTA, MIEMBRO',
  activo TINYINT(1) NOT NULL DEFAULT 1 COMMENT 'Usuario activo o inactivo',
  creado_en DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de creación',
  actualizado_en DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Fecha de última actualización',
  creado_por VARCHAR(100) NOT NULL DEFAULT 'system' COMMENT 'Usuario que creó el registro',
  
  INDEX idx_nombre_usuario (nombre_usuario),
  INDEX idx_correo (correo),
  INDEX idx_rol (rol),
  INDEX idx_activo (activo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla de usuarios del sistema';

-- ===============================================
-- 3. TABLA: planes
-- Descripción: Planes de membresía
-- ===============================================
CREATE TABLE planes (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL UNIQUE COMMENT 'Nombre del plan',
  descripcion LONGTEXT NOT NULL COMMENT 'Descripción detallada del plan',
  precio_mensual DECIMAL(10, 2) NOT NULL COMMENT 'Precio mensual en formato decimal',
  duracion_dias INT NOT NULL COMMENT 'Duración del plan en días',
  activo TINYINT(1) NOT NULL DEFAULT 1 COMMENT 'Plan activo o inactivo',
  creado_en DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  actualizado_en DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  creado_por VARCHAR(100) NOT NULL DEFAULT 'system',
  
  INDEX idx_nombre (nombre),
  INDEX idx_activo (activo),
  INDEX idx_precio (precio_mensual)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla de planes de membresía';

-- ===============================================
-- 4. TABLA: instructores
-- Descripción: Instructores del gimnasio
-- ===============================================
CREATE TABLE instructores (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  usuario_id BIGINT NOT NULL UNIQUE COMMENT 'FK a usuarios_sistema',
  especialidad VARCHAR(100) NOT NULL COMMENT 'Especialidad del instructor',
  certificaciones LONGTEXT COMMENT 'Certificaciones profesionales',
  activo TINYINT(1) NOT NULL DEFAULT 1 COMMENT 'Instructor activo o inactivo',
  creado_en DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  actualizado_en DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  creado_por VARCHAR(100) NOT NULL DEFAULT 'system',
  
  INDEX idx_usuario_id (usuario_id),
  INDEX idx_especialidad (especialidad),
  INDEX idx_activo (activo),
  CONSTRAINT fk_instructores_usuario 
    FOREIGN KEY (usuario_id) 
    REFERENCES usuarios_sistema(id) 
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla de instructores del gimnasio';

-- ===============================================
-- 5. TABLA: miembros
-- Descripción: Miembros del gimnasio
-- ===============================================
CREATE TABLE miembros (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  usuario_id BIGINT NOT NULL UNIQUE COMMENT 'FK a usuarios_sistema',
  numero_membresia VARCHAR(50) NOT NULL UNIQUE COMMENT 'Número de membresía único',
  telefono_contacto VARCHAR(20) NOT NULL COMMENT 'Teléfono de contacto',
  fecha_nacimiento DATE COMMENT 'Fecha de nacimiento (nullable)',
  sexo VARCHAR(10) NOT NULL COMMENT 'Sexo: M, F, OTRO',
  telefono_emergencia VARCHAR(20) COMMENT 'Teléfono de emergencia (nullable)',
  creado_en DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  actualizado_en DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  creado_por VARCHAR(100) NOT NULL DEFAULT 'system',
  
  INDEX idx_usuario_id (usuario_id),
  INDEX idx_numero_membresia (numero_membresia),
  INDEX idx_sexo (sexo),
  CONSTRAINT fk_miembros_usuario 
    FOREIGN KEY (usuario_id) 
    REFERENCES usuarios_sistema(id) 
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla de miembros del gimnasio';

-- ===============================================
-- 6. TABLA: membresias
-- Descripción: Membresías activas de miembros
-- ===============================================
CREATE TABLE membresias (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  miembro_id BIGINT NOT NULL COMMENT 'FK a miembros',
  plan_id BIGINT NOT NULL COMMENT 'FK a planes',
  fecha_inicio DATE NOT NULL COMMENT 'Fecha de inicio de la membresía',
  fecha_vencimiento DATE NOT NULL COMMENT 'Fecha de vencimiento de la membresía',
  estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVA' COMMENT 'Estado: ACTIVA, VENCIDA, CANCELADA',
  creado_en DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  actualizado_en DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  creado_por VARCHAR(100) NOT NULL DEFAULT 'system',
  
  UNIQUE KEY uk_miembro_fecha (miembro_id, fecha_inicio),
  INDEX idx_miembro_id (miembro_id),
  INDEX idx_plan_id (plan_id),
  INDEX idx_estado (estado),
  INDEX idx_fecha_vencimiento (fecha_vencimiento),
  CONSTRAINT fk_membresias_miembro 
    FOREIGN KEY (miembro_id) 
    REFERENCES miembros(id) 
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_membresias_plan 
    FOREIGN KEY (plan_id) 
    REFERENCES planes(id) 
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla de membresías activas';

-- ===============================================
-- 7. TABLA: clases
-- Descripción: Clases ofrecidas en el gimnasio
-- ===============================================
CREATE TABLE clases (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL COMMENT 'Nombre de la clase',
  instructor_id BIGINT NOT NULL COMMENT 'FK a instructores',
  dia_semana VARCHAR(15) NOT NULL COMMENT 'Día de semana: LUNES, MARTES, etc.',
  hora_inicio TIME NOT NULL COMMENT 'Hora de inicio de la clase',
  duracion_minutos INT NOT NULL COMMENT 'Duración en minutos',
  capacidad INT NOT NULL COMMENT 'Capacidad máxima de miembros',
  estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVA' COMMENT 'Estado: ACTIVA, CANCELADA',
  creado_en DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  actualizado_en DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  creado_por VARCHAR(100) NOT NULL DEFAULT 'system',
  
  INDEX idx_instructor_id (instructor_id),
  INDEX idx_nombre (nombre),
  INDEX idx_dia_semana (dia_semana),
  INDEX idx_estado (estado),
  CONSTRAINT fk_clases_instructor 
    FOREIGN KEY (instructor_id) 
    REFERENCES instructores(id) 
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla de clases del gimnasio';

-- ===============================================
-- 8. TABLA: asistencias
-- Descripción: Registro de asistencias a clases
-- ===============================================
CREATE TABLE asistencias (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  clase_id BIGINT NOT NULL COMMENT 'FK a clases',
  miembro_id BIGINT NOT NULL COMMENT 'FK a miembros',
  fecha DATE NOT NULL COMMENT 'Fecha de la clase',
  asistio TINYINT(1) NOT NULL DEFAULT 0 COMMENT 'Si asistió o no',
  hora_llegada TIME COMMENT 'Hora de llegada (nullable)',
  creado_en DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  actualizado_en DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  creado_por VARCHAR(100) NOT NULL DEFAULT 'system',
  
  UNIQUE KEY uk_clase_miembro_fecha (clase_id, miembro_id, fecha),
  INDEX idx_clase_id (clase_id),
  INDEX idx_miembro_id (miembro_id),
  INDEX idx_fecha (fecha),
  INDEX idx_clase_fecha (clase_id, fecha),
  CONSTRAINT fk_asistencias_clase 
    FOREIGN KEY (clase_id) 
    REFERENCES clases(id) 
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_asistencias_miembro 
    FOREIGN KEY (miembro_id) 
    REFERENCES miembros(id) 
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla de asistencias a clases';

-- ===============================================
-- 9. TABLA: tokens_refresco
-- Descripción: Tokens de refresh para autenticación
-- ===============================================
CREATE TABLE tokens_refresco (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  token VARCHAR(500) NOT NULL UNIQUE COMMENT 'Token JWT de refresh',
  usuario_id BIGINT NOT NULL COMMENT 'FK a usuarios_sistema',
  expira_en DATETIME NOT NULL COMMENT 'Fecha y hora de expiración del token',
  revocado TINYINT(1) NOT NULL DEFAULT 0 COMMENT 'Si el token ha sido revocado',
  creado_en DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  actualizado_en DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  creado_por VARCHAR(100) NOT NULL DEFAULT 'system',
  
  INDEX idx_usuario_id (usuario_id),
  INDEX idx_expira_en (expira_en),
  INDEX idx_revocado (revocado),
  CONSTRAINT fk_tokens_usuario 
    FOREIGN KEY (usuario_id) 
    REFERENCES usuarios_sistema(id) 
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla de tokens de refresh JWT';

-- ===============================================
-- 10. DATOS INICIALES
-- ===============================================

-- Insertar usuario administrador
-- Contraseña: admin123 (hash: $2a$10$slYQmyNdGzin7olVMwO1Be5qKV6EHjjZgG0jcYzEZF3zPRq5aJhCK)
INSERT INTO usuarios_sistema 
(nombre_usuario, correo, clave, rol, activo, creado_por) 
VALUES 
('admin', 'admin@gms.com', '$2a$10$slYQmyNdGzin7olVMwO1Be5qKV6EHjjZgG0jcYzEZF3zPRq5aJhCK', 'ADMIN', 1, 'system');

-- ===============================================
-- Información de la base de datos
-- ===============================================
-- Notas:
-- 1. La contraseña del admin es: admin123
-- 2. Hash BCrypt: $2a$10$slYQmyNdGzin7olVMwO1Be5qKV6EHjjZgG0jcYzEZF3zPRq5aJhCK
-- 3. Todos los timestamps se crean automáticamente
-- 4. Charset UTF8MB4 soporta caracteres especiales y emojis
-- 5. Los índices están optimizados para queries frecuentes
-- 6. Las FK tienen RESTRICT para evitar borrados accidentales
-- 7. Tabla de auditoría: creado_en, actualizado_en, creado_por
-- ===============================================

-- Fin de script GMS
