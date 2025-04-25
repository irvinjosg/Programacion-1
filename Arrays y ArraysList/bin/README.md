# Proyecto Java: Arrays y Arraylist


## Descripción
Este repositorio contiene dos programas en Java de consola orientados a la práctica de estructuras de datos y manejo de excepciones:

1. **ListaDeCompras.java**: Aplicación para gestionar dinámicamente una lista de compras. Permite agregar, mostrar, eliminar, buscar y ordenar productos alfabéticamente :contentReference[oaicite:0]{index=0}&#8203;:contentReference[oaicite:1]{index=1}.  
2. **OperacionesArray.java**: Herramienta para realizar operaciones básicas sobre un array de 10 números enteros: mostrar, sumar, encontrar máximo y mínimo, e invertir el orden :contentReference[oaicite:2]{index=2}&#8203;:contentReference[oaicite:3]{index=3}.

## Estructura de Archivos


├── README.md               ← Documento de instrucciones y descripción
├── ListaDeCompras.java     ← Programa de gestión de lista de compras
└── OperacionesArray.java   ← Programa de operaciones sobre arrays
Requisitos
Java Development Kit (JDK) 8 o superior instalado.

Terminal o símbolo de sistema con javac y java en el PATH.

Compilación
Desde la carpeta raíz del proyecto, ejecute:


Copiar
Editar
javac ListaDeCompras.java
javac OperacionesArray.java
Esto generará los archivos ListaDeCompras.class y OperacionesArray.class.

Ejecución
ListaDeCompras


Copiar
Editar
java ListaDeCompras
Aparecerá un menú interactivo con las siguientes opciones:

Agregar un producto a la lista

Mostrar la lista de compras

Eliminar un producto de la lista

Buscar un producto en la lista

Ordenar la lista alfabéticamente

Salir

OperacionesArray

Copiar
Editar
java OperacionesArray
Ingrese 10 números enteros (valida que sean números).

Utilice el menú para:

Mostrar el array original

Calcular la suma de sus elementos

Encontrar el valor máximo y mínimo

Invertir el orden del array

Salir

Ejemplos de Uso
ListaDeCompras:

less
Copiar
Editar
MENÚ DE LISTA DE COMPRAS
1. Agregar un producto a la lista
...
Seleccione una opción: 1
Ingrese el nombre del producto: Leche
Producto agregado correctamente.
OperacionesArray:

python-repl
Copiar
Editar
Ingrese 10 números enteros:
Elemento 1: 5
...
MENÚ DE OPCIONES
1. Mostrar el array original
...
Seleccione una opción: 2
Suma de los elementos: 47
Manejo de Errores
Ambos programas usan try-catch para capturar InputMismatchException y evitar que entradas no válidas (como texto donde se espera un entero) rompan la ejecución, solicitando al usuario reingresar correctamente el dato.