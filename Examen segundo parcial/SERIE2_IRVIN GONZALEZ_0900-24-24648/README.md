# Sistema de Control de Empleados - Second Blue S.A.

## Descripción
Este sistema Java permite gestionar la información de empleados de la empresa Second Blue S.A. mediante un menú interactivo que ofrece funcionalidades para agregar, visualizar y almacenar datos de empleados en un archivo binario.

## Características principales
- **Gestión de empleados**: Permite agregar y visualizar información de empleados
- **Persistencia de datos**: Almacena los datos en un archivo binario (`empleados.dat`)
- **Interfaz intuitiva**: Menú interactivo con limpieza automática de consola
- **Validación de datos**: Control de errores en la entrada de información
- **Generación automática de IDs**: Asigna IDs secuenciales automáticamente

## Requisitos del sistema
- Java JDK 8 o superior
- Sistema operativo compatible (Windows, Linux, macOS)

## Instrucciones de uso
1. Compilar el programa: `javac SistemaControlEmpleados.java`
2. Ejecutar el programa: `java SistemaControlEmpleados`
3. Seleccionar opciones del menú:
   - **1. Mostrar todos los empleados**: Visualiza la lista completa
   - **2. Agregar nuevo empleado**: Permite registrar nuevos empleados
   - **3. Guardar cambios**: Persiste los datos en el archivo
   - **4. Salir**: Cierra la aplicación (guarda automáticamente)

## Estructura de datos
Cada empleado contiene:
- ID (generado automáticamente)
- Nombre completo
- Edad
- Salario (en quetzales)

## Funcionalidades detalladas
- **Carga automática**: Al iniciar, carga los datos del archivo existente
- **Formato tabular**: Muestra los empleados en formato de tabla ordenada
- **Persistencia opcional**: Permite elegir cuándo guardar los cambios
- **Manejo de errores**: Controla entradas inválidas y errores de archivo

## Clases principales
1. **SistemaControlEmpleados**: Clase principal con la lógica del sistema
2. **Empleado**: Clase modelo que implementa Serializable para persistencia

## Autor
- **Nombre**: Irvin José González Mateo
- **Carnet**: 0900-24-24648
- **Curso**: Programación 1

## Notas importantes
- El archivo de datos se crea automáticamente en la primera ejecución
- Los cambios no se guardan automáticamente (excepto al salir)
- Se recomienda usar la opción "Guardar cambios" frecuentemente
- El sistema está diseñado para fines educativos demostrando:
  - Manejo de archivos binarios
  - Serialización de objetos
  - Programación estructurada en Java
  - Diseño de interfaces de consola