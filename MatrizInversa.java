import java.io.*;
import java.nio.file.*;
import java.util.*;

public class MatrizInversa {

    public static void main(String[] args) {
        String entrada = "matriz.txt";
        String salida = "matriz_inversa.txt";

        try {
            double[][] A = leerMatriz(entrada);

            if (A.length != 4 || A[0].length != 4) {
                escribirTexto(salida, "La matriz no es 4x4");
                return;
            }

            double[][] inv = inversa(A);
            if (inv == null) {
                escribirTexto(salida, "La matriz no tiene inversa");
                return;
            }

            escribirMatriz(salida, inv);
            System.out.println("Inversa generada.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ---- Inversa por Gauss-Jordan ----
    static double[][] inversa(double[][] m) {
        int n = 4;
        double[][] A = new double[n][n];
        double[][] I = new double[n][n];

        // Copiar matriz y crear identidad
        for (int i = 0; i < n; i++) {
            I[i][i] = 1;
            for (int j = 0; j < n; j++)
                A[i][j] = m[i][j];
        }

        // Gauss-Jordan
        for (int i = 0; i < n; i++) {

            // Si el pivote es 0, intercambiar filas
            if (A[i][i] == 0) {
                int j = i + 1;
                while (j < n && A[j][i] == 0) j++;
                if (j == n) return null; 
                double[] tmpA = A[i]; A[i] = A[j]; A[j] = tmpA;
                double[] tmpI = I[i]; I[i] = I[j]; I[j] = tmpI;
            }

            // Hacer el pivote = 1
            double piv = A[i][i];
            for (int j = 0; j < n; j++) {
                A[i][j] /= piv;
                I[i][j] /= piv;
            }

            // Hacer ceros en la columna i
            for (int k = 0; k < n; k++) {
                if (k == i) continue;
                double factor = A[k][i];
                for (int j = 0; j < n; j++) {
                    A[k][j] -= factor * A[i][j];
                    I[k][j] -= factor * I[i][j];
                }
            }
        }

        return I;
    }

    static double[][] leerMatriz(String archivo) throws IOException {
        List<double[]> filas = new ArrayList<>();
        for (String linea : Files.readAllLines(Paths.get(archivo))) {
            if (!linea.trim().isEmpty()) {
                filas.add(Arrays.stream(linea.trim().split("\\s+"))
                        .mapToDouble(Double::parseDouble).toArray());
            }
        }
        return filas.toArray(new double[0][]);
    }

    static void escribirMatriz(String archivo, double[][] m) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (double[] fila : m) {
                for (int i = 0; i < fila.length; i++) {
                    bw.write(String.format("%.4f", fila[i]));
                    if (i < fila.length - 1) bw.write(" ");
                }
                bw.newLine();
            }
        }
    }

    static void escribirTexto(String archivo, String texto) throws IOException {
        Files.write(Paths.get(archivo), texto.getBytes());
    }
}
