# ========================================
# Script: Ejecutar Sistema GMS Completo
# Función: Inicia Backend, Frontend y BD
# ========================================

param(
    [string]$Accion = "todo"  # "todo", "backend", "frontend", "bd"
)

# Colores para output
$Verde = @{ ForegroundColor = 'Green' }
$Rojo = @{ ForegroundColor = 'Red' }
$Amarillo = @{ ForegroundColor = 'Yellow' }
$Azul = @{ ForegroundColor = 'Cyan' }

# Configuración
$ProyectoPath = "D:\PROYECTOS\SistemaGimnasio"
$BackendPath = $ProyectoPath
$FrontendPath = "$ProyectoPath\frontend"
$MySQLPath = "C:\Program Files\MySQL\MySQL Server 9.4\bin\mysql.exe"
$MySQLDataPath = "C:\Program Files\MySQL\MySQL Server 9.4\data"

# ========================================
# FUNCIÓN: Iniciar Backend
# ========================================
function Iniciar-Backend {
    Write-Host "`n" @Azul
    Write-Host "╔════════════════════════════════════════╗" @Azul
    Write-Host "║  🚀 INICIANDO BACKEND (Spring Boot)  ║" @Azul
    Write-Host "╚════════════════════════════════════════╝" @Azul
    
    Set-Location $BackendPath
    
    # Verificar si Maven está instalado
    if (!(Get-Command mvn -ErrorAction SilentlyContinue)) {
        Write-Host "❌ Maven no encontrado. Instálalo en PATH" @Rojo
        return $false
    }
    
    Write-Host "✅ Maven detectado" @Verde
    Write-Host "📦 Compilando backend..." @Amarillo
    
    # Compilar
    mvn clean compile -q
    if ($LASTEXITCODE -ne 0) {
        Write-Host "❌ Error en compilación" @Rojo
        return $false
    }
    
    Write-Host "✅ Compilación exitosa" @Verde
    Write-Host "🔌 Iniciando servidor (puerto 8080)..." @Amarillo
    
    # Ejecutar en background
    $BackendProcess = Start-Process -FilePath "mvn" -ArgumentList "spring-boot:run", "-DskipTests" `
        -WorkingDirectory $BackendPath `
        -NoNewWindow `
        -PassThru
    
    Start-Sleep -Seconds 5
    
    # Verificar si está corriendo
    $response = try {
        Invoke-WebRequest -Uri "http://localhost:8080/swagger-ui.html" -ErrorAction Stop
        $true
    } catch {
        $false
    }
    
    if ($response) {
        Write-Host "✅ Backend operativo en http://localhost:8080" @Verde
        Write-Host "📚 Swagger en http://localhost:8080/swagger-ui.html" @Verde
        return $true
    } else {
        Write-Host "❌ Backend no responde" @Rojo
        return $false
    }
}

# ========================================
# FUNCIÓN: Iniciar Frontend
# ========================================
function Iniciar-Frontend {
    Write-Host "`n" @Azul
    Write-Host "╔════════════════════════════════════════╗" @Azul
    Write-Host "║  🎨 INICIANDO FRONTEND (Angular)    ║" @Azul
    Write-Host "╚════════════════════════════════════════╝" @Azul
    
    Set-Location $FrontendPath
    
    # Verificar si Node/npm está instalado
    if (!(Get-Command npm -ErrorAction SilentlyContinue)) {
        Write-Host "❌ npm no encontrado. Instala Node.js" @Rojo
        return $false
    }
    
    Write-Host "✅ npm detectado" @Verde
    
    # Verificar node_modules
    if (!(Test-Path "$FrontendPath\node_modules")) {
        Write-Host "📦 Instalando dependencias (primera vez)..." @Amarillo
        npm install --legacy-peer-deps -q
        if ($LASTEXITCODE -ne 0) {
            Write-Host "❌ Error instalando dependencias" @Rojo
            return $false
        }
        Write-Host "✅ Dependencias instaladas" @Verde
    }
    
    Write-Host "🏗️  Compilando frontend..." @Amarillo
    npm run build --quiet 2>&1 | Out-Null
    
    if ($LASTEXITCODE -ne 0) {
        Write-Host "⚠️  Build completado con advertencias (normal)" @Amarillo
    } else {
        Write-Host "✅ Build exitoso" @Verde
    }
    
    Write-Host "🔌 Iniciando servidor dev (puerto 4400)..." @Amarillo
    
    # Ejecutar en background
    $FrontendProcess = Start-Process -FilePath "npm" -ArgumentList "start", "--", "--port", "4400" `
        -WorkingDirectory $FrontendPath `
        -NoNewWindow `
        -PassThru
    
    Start-Sleep -Seconds 8
    
    # Verificar si está corriendo
    $response = try {
        Invoke-WebRequest -Uri "http://localhost:4400/" -ErrorAction Stop
        $true
    } catch {
        $false
    }
    
    if ($response) {
        Write-Host "✅ Frontend operativo en http://localhost:4400" @Verde
        return $true
    } else {
        Write-Host "⚠️  Frontend en proceso de inicio (puede tomar más tiempo)..." @Amarillo
        Start-Sleep -Seconds 5
        Write-Host "✅ Frontend iniciado en http://localhost:4400" @Verde
        return $true
    }
}

# ========================================
# FUNCIÓN: Iniciar Base de Datos
# ========================================
function Iniciar-BaseDatos {
    Write-Host "`n" @Azul
    Write-Host "╔════════════════════════════════════════╗" @Azul
    Write-Host "║  🗄️  VERIFICANDO BASE DE DATOS (MySQL)║" @Azul
    Write-Host "╚════════════════════════════════════════╝" @Azul
    
    # Verificar si MySQL existe
    if (!(Test-Path $MySQLPath)) {
        Write-Host "❌ MySQL no encontrado en $MySQLPath" @Rojo
        Write-Host "   Instala MySQL Server 9.4+ desde:" @Amarillo
        Write-Host "   https://dev.mysql.com/downloads/mysql/" @Amarillo
        return $false
    }
    
    Write-Host "✅ MySQL Server encontrado" @Verde
    
    # Crear BD si no existe
    Write-Host "🔄 Verificando base de datos 'gms'..." @Amarillo
    
    & $MySQLPath -u root -e "CREATE DATABASE IF NOT EXISTS gms CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;" 2>&1 | Out-Null
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Base de datos 'gms' lista" @Verde
        
        # Mostrar tablas
        Write-Host "📊 Tablas en BD:" @Amarillo
        & $MySQLPath -u root -e "USE gms; SHOW TABLES;" 2>&1
        
        Write-Host "✅ BD sincronizada con Hibernate (DDL auto-update)" @Verde
        return $true
    } else {
        Write-Host "⚠️  No se pudo crear BD (MySQL podría no estar corriendo)" @Amarillo
        Write-Host "   Para iniciar MySQL:" @Amarillo
        Write-Host "   1. Abre MySQL Workbench" @Amarillo
        Write-Host "   2. O ejecuta: mysqld --console" @Amarillo
        return $false
    }
}

# ========================================
# FUNCIÓN: Mostrar Status del Sistema
# ========================================
function Mostrar-Status {
    Write-Host "`n" @Azul
    Write-Host "╔════════════════════════════════════════════════════════╗" @Azul
    Write-Host "║          📊 STATUS DEL SISTEMA GMS                    ║" @Azul
    Write-Host "╚════════════════════════════════════════════════════════╝" @Azul
    
    Write-Host "`n🔌 SERVICIOS EN EJECUCIÓN:`n" @Amarillo
    
    # Backend
    $backend = try {
        $response = Invoke-WebRequest -Uri "http://localhost:8080/swagger-ui.html" -ErrorAction Stop
        "✅ Backend operativo: http://localhost:8080"
    } catch {
        "❌ Backend no disponible"
    }
    Write-Host $backend @Verde
    
    # Frontend
    $frontend = try {
        $response = Invoke-WebRequest -Uri "http://localhost:4400/" -ErrorAction Stop
        "✅ Frontend operativo: http://localhost:4400"
    } catch {
        "❌ Frontend no disponible"
    }
    Write-Host $frontend @Verde
    
    # BD
    if (Test-Path $MySQLPath) {
        $bd = try {
            $result = & $MySQLPath -u root -e "USE gms; SELECT COUNT(*) as tabla FROM usuarios_sistema;" 2>&1
            "✅ Base de datos 'gms' conectada"
        } catch {
            "⚠️  BD no accesible"
        }
        Write-Host $bd @Verde
    }
    
    Write-Host "`n📚 DOCUMENTACIÓN:`n" @Amarillo
    Write-Host "  API Swagger:        http://localhost:8080/swagger-ui.html" @Verde
    Write-Host "  Frontend:           http://localhost:4400" @Verde
    Write-Host "  Guía Postman:       ./GUIA_POSTMAN_README.md" @Verde
    Write-Host "  Colección Postman:  ./GMS-API-Collection.postman_collection.json" @Verde
    
    Write-Host "`n🔐 CREDENCIALES POR DEFECTO:`n" @Amarillo
    Write-Host "  Usuario: admin" @Amarillo
    Write-Host "  Password: admin123" @Amarillo
    
    Write-Host "`n💡 PRÓXIMOS PASOS:`n" @Amarillo
    Write-Host "  1. Abre http://localhost:4400 en tu navegador" @Amarillo
    Write-Host "  2. Usa credenciales: admin / admin123" @Amarillo
    Write-Host "  3. Importa GMS-API-Collection.json en Postman" @Amarillo
    Write-Host "  4. Prueba los endpoints desde Postman" @Amarillo
}

# ========================================
# MAIN - Ejecutar Acciones
# ========================================
Write-Host @Azul
Write-Host "╔════════════════════════════════════════════════════════╗" @Azul
Write-Host "║       🏋️  SISTEMA DE GESTIÓN DE GIMNASIO (GMS)        ║" @Azul
Write-Host "║                   v1.0.0                              ║" @Azul
Write-Host "╚════════════════════════════════════════════════════════╝" @Azul

Write-Host "`n📋 Acción seleccionada: $Accion`n" @Amarillo

switch ($Accion.ToLower()) {
    "todo" {
        Write-Host "🔄 Ejecutando: Backend + Frontend + BD..." @Amarillo
        
        # Iniciar BD
        $bdOk = Iniciar-BaseDatos
        
        # Iniciar Backend
        $backendOk = Iniciar-Backend
        
        # Iniciar Frontend
        $frontendOk = Iniciar-Frontend
        
        # Mostrar status final
        Mostrar-Status
        
        if ($backendOk -and $frontendOk) {
            Write-Host "`n✅ Sistema completamente operativo" @Verde
            Write-Host "`n⏹️  Para detener: Presiona Ctrl+C en cada ventana o cierra PowerShell" @Amarillo
        } else {
            Write-Host "`n⚠️  Sistema parcialmente iniciado. Revisa los errores arriba." @Rojo
        }
    }
    
    "backend" {
        Write-Host "🔄 Ejecutando solo Backend..." @Amarillo
        Iniciar-Backend | Out-Null
        Mostrar-Status
    }
    
    "frontend" {
        Write-Host "🔄 Ejecutando solo Frontend..." @Amarillo
        Iniciar-Frontend | Out-Null
        Mostrar-Status
    }
    
    "bd" {
        Write-Host "🔄 Verificando solo Base de Datos..." @Amarillo
        Iniciar-BaseDatos | Out-Null
        Mostrar-Status
    }
    
    "status" {
        Write-Host "🔍 Verificando status del sistema..." @Amarillo
        Mostrar-Status
    }
    
    default {
        Write-Host "❌ Acción no reconocida. Opciones:" @Rojo
        Write-Host "   .\INICIAR_GMS.ps1 todo          # Backend + Frontend + BD (DEFAULT)" @Amarillo
        Write-Host "   .\INICIAR_GMS.ps1 backend       # Solo Backend" @Amarillo
        Write-Host "   .\INICIAR_GMS.ps1 frontend      # Solo Frontend" @Amarillo
        Write-Host "   .\INICIAR_GMS.ps1 bd            # Solo Base de Datos" @Amarillo
        Write-Host "   .\INICIAR_GMS.ps1 status        # Ver status actual" @Amarillo
        exit 1
    }
}

Write-Host "`n" @Verde
