import java.io.*;
import java.util.ArrayList;

public class MatrizInversa {

    public static void main(String[] args) {

        String archivoEntrada = "matriz.txt";          // matriz original
        String archivoSalida = "matriz_inversa.txt";   // matriz inversa

        try {
            // Leer matriz desde el archivo
            double[][] M = leerMatriz(archivoEntrada);

            // Verificar si es 2x2
            if (M.length != 2 || M[0].length != 2) {
                escribirArchivo(archivoSalida, "Solo funciona con matrices 2x2.");
                return;
            }

            // Calcular inversa
            double[][] inv = inversa2x2(M);

            if (inv == null) {
                escribirArchivo(archivoSalida, "La matriz no tiene inversa (determinante = 0)");
                return;
            }

            // Escribir inversa en archivo
            escribirMatriz(archivoSalida, inv);

            System.out.println("Matriz inversa generada. Revisa el archivo: " + archivoSalida);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}