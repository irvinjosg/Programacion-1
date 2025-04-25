// Definición de la clase principal tiene que ser igual que el nombre del archivo
public class TareaSesion2 {

    // punto de entrada necesario de Java.
    public static void main(String[] args) {

        // Se crea un objeto Scanner para leer datos desde la entrada estándar (teclado)
        java.util.Scanner sc = new java.util.Scanner(System.in);

        // Ejercicio 1.1 (if): Determinar si un número es positivo, negativo o cero

        System.out.println("Ejercicio if 1.1: Evaluar si un número es positivo, negativo o cero."); // explicación
        System.out.print("Ingrese un número entero: "); // solicita un numero
        int num = sc.nextInt(); // lee el numero ingresado (de valor entero)

        // determinamos el tipo
        if (num > 0) { // matematicamente si es mayor a 0 es positivo
            System.out.println("El número es positivo.");
        } else if (num < 0) {// matematicamente si es menor a 0 es negativo
            System.out.println("El número es negativo.");
        } else { // si no es ninguna de las anteriores es 0.
            System.out.println("El número es cero.");
        }

        // Ejercicio 1.2 (if): Determinar si un año es bisiesto

        System.out.println("\nEjercicio if 1.2: Determinar si un año es bisiesto.");// explicación
        System.out.print("Ingrese un año: "); // solicita un año al usuario
        int año = sc.nextInt(); // Lee el año ingresado

        // Se evalúa la condición compuesta para determinar si el año es bisiesto
        if ((año % 4 == 0 && año % 100 != 0) || (año % 400 == 0)) {// % = dividido && = y != diferente de "x" == = igual
            System.out.println("El año " + año + " es bisiesto."); // en java concatenar con +
        } else {
            System.out.println("El año " + año + " no es bisiesto.");
        }

        // Ejercicio 2.1 (switch): Mostrar el día de la semana según un número (1-7)

        System.out.println("\nEjercicio switch 2.1: Mostrar el día de la semana.");
        System.out.print("Ingrese un número del 1 al 7: "); // ingresar un campo valido
        int dia = sc.nextInt(); // lee el número ingresado

        // se utiliza switch para determinar y mostrar el día correspondiente a la
        // selección
        switch (dia) {
            case 1:
                System.out.println("Lunes");
                break; // fin
            case 2:
                System.out.println("Martes");
                break; // fin
            case 3:
                System.out.println("Miércoles");
                break; // fin
            case 4:
                System.out.println("Jueves");
                break; // fin
            case 5:
                System.out.println("Viernes");
                break; // fin
            case 6:
                System.out.println("Sábado");
                break; // fin
            case 7:
                System.out.println("Domingo");
                break; // fin
            default:// "si no se cumple ningun caso mostrar="
                System.out.println("Número inválido."); // caso por defecto si no es 1-7
                break; // fin
        }

        // Ejercicio 2.2 (switch): Menú de selección de acciones

        System.out.println("\nEjercicio switch 2.2: Menú de selecciones.");
        System.out.print("Ingrese un número del 1 al 3: "); // ingresar un campo valido
        int seleccion = sc.nextInt(); // lee el número ingresado

        // se utiliza switch para determinar y mostrar el día correspondiente a la
        // selección
        switch (seleccion) {
            case 1:
                System.out.println("¡Bienvenido a progra 1 sección B!");
                break; // fin
            case 2:
                java.util.Date fechaActual = new java.util.Date();// obtiene automáticamente la fecha y hora en el
                                                                  // momento en que se ejecuta esa línea.
                System.out.println("Fecha actual: " + fechaActual.toString()); // muestra la informacón con un formato
                                                                               // legible.
                break; // fin
            case 3: // no hace nada ejecuta el final del programa
                break; // fin
            default:// "si no se cumple ningun caso mostrar="
                System.out.println("Número inválido."); // caso por defecto si no es 1-3
                break; // fin
        }

        // Ejercicio 3.1 (while): Sumar números hasta que se ingrese un número negativo

        System.out.println("\nEjercicio while 3.1: Sumar números hasta ingresar un número negativo.");
        int suma = 0; // en 0 como punto de partida
        while (true) { // bucle while que se ejecuta de forma indefinida hasta que se ingrese un número
                       // negativo
            System.out.print("Ingrese un número (número negativo para terminar): ");
            int numero = sc.nextInt(); // lee el número ingresado
            if (numero < 0) { // numeros >= 0 son positivos. < 0 son negativos
                break; // si el número es negativo, se rompe el bucle
            }
            suma += numero; // += = incrementar la variable
        }
        System.out.println("La suma total es: " + suma);// cuando ingresan negativo se muestra la variable

        // Ejercicio 3.2 (while): Repetir una acción según la respuesta del usuario

        System.out.println("\nEjercicio while 3.2: Repetir acción.");
        int contador = 0; // sontador en 0 como punto de partida
        String respuesta; // variable para almacenar la respuesta del usuario

        // bucle do-while que se ejecuta al menos una vez
        do {
            System.out.println("incrementar numero: " + (contador + 1));
            contador++; // sube el contador
            System.out.print("¿Desea continuar? (y/n): ");
            respuesta = sc.next(); // lee la respuesta del usuario
        } while (respuesta.equalsIgnoreCase("y")); // Continua si la respuesta es "y"
        System.out.println("La acción se repitió " + contador + " veces.");

        // Ejercicio 4.1 (for): Imprimir los primeros 10 números naturales

        System.out.println("\nEjercicio for 4.1: Imprimir los primeros 10 números naturales.");

        // bucle for que recorre del 1 al 10 e imprime cada número
        for (int i = 1; i <= 10; i++) { // i = variable de conteo que incrementa en si misma hasta ser mayor a 10
            System.out.println(i); // muestra la operacion matematica de la variable i.
        }

        // Ejercicio 4.2 (for): Sumar los primeros N números naturales

        System.out.println("\nEjercicio for 4.2: Sumar los primeros n números naturales.");
        System.out.print("Ingrese un número entero N: "); // Solicita el valor de N
        int N = sc.nextInt(); // lee el valor ingresado para N
        int sumaN = 0; // sumador en 0 como punto de partida

        // bucle for que recorre desde 1 hasta N y suma cada número
        for (int i = 1; i <= N; i++) { // i = suma hasta el limite definido del usuario
            sumaN += i;
        }
        System.out.println("La suma de los primeros " + N + " números naturales es: " + sumaN); // se muestra la suma

        // Se cierra el objeto Scanner para liberar recursos
        sc.close();
    }
}
