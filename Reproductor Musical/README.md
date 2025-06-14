# 🎵 Reproductor de Música

Un sistema completo de gestión de playlists desarrollado en Java que permite crear, organizar y administrar colecciones de canciones utilizando estructuras de datos lineales (listas enlazadas).

## 📋 Características

- **Gestión de Playlists**: Crear y administrar múltiples playlists
- **Administración de Canciones**: Agregar, buscar y eliminar canciones
- **Búsqueda Avanzada**: Buscar canciones por título en todas las playlists
- **Estadísticas**: Ver información detallada sobre duración y cantidad de canciones
- **Interfaz de Usuario**: Menú interactivo por consola con navegación intuitiva

## 🏗️ Arquitectura del Sistema

### Clases Principales

#### `Cancion.java`
Representa una canción individual con sus propiedades básicas:
- Título (convertido automáticamente a mayúsculas)
- Artista (convertido automáticamente a mayúsculas)
- Duración en segundos
- Métodos para mostrar información y formatear duración

#### `Playlist.java`
Encapsula una playlist con:
- Nombre único (en mayúsculas)
- Lista enlazada de canciones
- Métodos para agregar, buscar y eliminar canciones
- Funcionalidades de visualización y estadísticas

#### `GestorPlaylists.java`
Administrador principal del sistema que maneja:
- Lista enlazada de playlists
- Operaciones CRUD sobre playlists
- Búsqueda global de canciones
- Estadísticas generales del sistema

#### `ListaCanciones.java`
Implementación de lista enlazada para almacenar canciones:
- Inserción al final para mantener orden
- Búsqueda por título
- Eliminación con actualización de referencias
- Cálculo de duración total

### Nodos de Enlace

#### `NodoCancion.java`
Nodo para la estructura de lista enlazada de canciones.

#### `NodoPlaylist.java`
Nodo para la estructura de lista enlazada de playlists.

#### `ReproductorMusica.java`
Clase principal con interfaz de usuario que proporciona:
- Menú interactivo por consola
- Validación de entrada de datos
- Navegación entre diferentes funcionalidades
- Manejo de errores

## 🚀 Funcionalidades

### 1. Crear Playlist
- Crear nuevas playlists con nombres únicos
- Validación de nombres duplicados
- Conversión automática a mayúsculas

### 2. Agregar Canciones
- Agregar canciones a playlists específicas
- Validación de datos de entrada
- Navegación paso a paso con opción de retroceso

### 3. Mostrar Playlists
- Visualizar contenido completo de playlists
- Mostrar información detallada de cada canción
- Estadísticas de duración y cantidad

### 4. Buscar Canciones
- Búsqueda por título en todas las playlists
- Búsqueda case-insensitive
- Visualización de resultados detallados

### 5. Eliminar Canciones
- Eliminar canciones específicas de playlists
- Confirmación de eliminación
- Actualización automática de estadísticas

### 6. Estadísticas Generales
- Total de playlists y canciones
- Duración total del sistema
- Promedio de canciones por playlist

## 💻 Uso del Sistema

### Compilación
```bash
javac *.java
```

### Ejecución
```bash
java ReproductorMusica
```

### Navegación
- Use números para seleccionar opciones del menú
- Ingrese 'M' para regresar al menú principal
- Use '<' para retroceder en formularios paso a paso
- Presione Enter para continuar después de visualizar información

## 📊 Ejemplo de Uso

1. **Crear Playlist**: Ingrese el nombre de la nueva playlist
2. **Agregar Canción**: 
   - Seleccione la playlist
   - Ingrese título, artista y duración
3. **Buscar**: Ingrese el título de la canción a buscar
4. **Visualizar**: Seleccione la playlist para ver su contenido completo

## 🔧 Características Técnicas

### Estructuras de Datos
- **Listas Enlazadas Simples**: Para almacenar canciones y playlists
- **Inserción al Final**: Mantiene el orden de inserción
- **Búsqueda Lineal**: Recorrido secuencial para búsquedas

### Validaciones
- Nombres únicos para playlists
- Campos obligatorios para canciones
- Validación de tipos de datos numéricos
- Manejo de entradas vacías

### Formato de Datos
- Conversión automática a mayúsculas para consistencia
- Formato de duración MM:SS o HH:MM:SS
- Interfaz visual con caracteres ASCII

## 🎯 Casos de Uso

### Escenarios Típicos
- **DJ/Músico**: Organizar sets musicales por evento
- **Usuario Personal**: Crear playlists por género o estado de ánimo
- **Análisis Musical**: Gestionar colecciones para análisis de duración

### Ventajas del Sistema
- **Simplicidad**: Interfaz intuitiva por consola
- **Eficiencia**: Estructuras de datos optimizadas para operaciones frecuentes
- **Escalabilidad**: Fácil extensión para nuevas funcionalidades
- **Portabilidad**: Código Java puro sin dependencias externas

## 📝 Notas de Implementación

- Todas las cadenas se convierten a mayúsculas para consistencia
- El sistema mantiene referencias de inicio y fin en las listas para optimizar inserciones
- La interfaz incluye limpieza de pantalla para mejor experiencia visual
- Manejo robusto de errores con mensajes informativos