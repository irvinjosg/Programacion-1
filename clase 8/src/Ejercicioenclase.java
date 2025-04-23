import java.util.Arrays;

public class Ejercicioenclase {
    public static void main(String[] args) {
        // Array
        int[] numeros = {1, 2, 3, 4, 5};
        System.out.println("El valor de mi array: " + Arrays.toString(numeros));
    
    int buscado = 4;
    boolean encontrado = false;
    for (int num : numeros) {
        if (num == buscado) {
            encontrado = true;
            break;
        }
    }
    System.out.println("El número " + buscado + " esta en el array?" + encontrado);
    
    int posicion = -1;
    for (int i = 0; i < numeros.length; i++) {
        if (numeros[i] == buscado) {
            posicion = i;
            break;
        }
    }
    if (posicion != -1) {
        System.out.println("El número " + buscado + " se encuentra en la posición " + posicion);
        } 
        else {
        System.out.println("El número " + buscado + " no se encuentra en el array");
        }
    


    }
}
