CAJERO AUTOMÁTICO

DESCRIPCIÓN:
Sistema de cajero automático que gestiona saldos mediante archivos binarios. 
Mantiene persistencia de datos entre ejecuciones.

REQUISITOS:
- Java JDK 8 o superior
- IDE para ejecutar java
- Permisos de escritura en el directorio de ejecución

ESTRUCTURA DE ARCHIVOS:
cajero/
L src/
│   L com/
│       L cajero/
│           L Cajero.java
L bin/
    L com/
        L cajero/
            L Cajero.class

a fin de repasar y entender el uso de "package" se creó esta estructura.

COMPILACIÓN:
1. Abrir terminal en la raíz del proyecto
2. Ejecutar:
   javac -d bin src/com/cajero/Cajero.java

EJECUCIÓN:
Desde la raíz del proyecto ejecutar:
java -cp bin com.cajero.Cajero

FUNCIONALIDADES PRINCIPALES:
- Consulta de saldo en tiempo real
- Retiro de dinero con validaciones
- Creación automática de archivo de saldo inicial
- Manejo de errores en entradas numéricas
- Persistencia de datos en archivo binario

NOTAS IMPORTANTES:
- El archivo 'saldo.dat' se crea automáticamente en la primera ejecución
- Saldo inicial: $1000.00
- Los cambios se guardan inmediatamente después de cada operación
- Soporta decimales en los montos 

INSTRUCCIONES PARA DIFERENTES SISTEMAS:

WINDOWS:
- Usar barras invertidas en rutas: src\com\cajero\Cajero.java
- Ejecutar en PowerShell o CMD como administrador si hay problemas de permisos

LINUX/MAC:
- Usar los comandos tal cual se muestran
- Asegurar permisos de ejecución en el directorio
- Ejecutar con: sudo si se presentan problemas de acceso a archivos

Autor
Irvin Gonzalez
0900-24-24648