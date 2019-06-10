package com.android.roger.sudokito;

import java.util.ArrayList;
import java.util.List;


public class Sudoku {
    int[][] tablero;
    int filasAndColumnas;
    int columnasCuadrado;
    int dificultad;
    List<Integer> sudokuResueltito;
    // Constructor
    public Sudoku(int filasAndColumnas, int dificultad) {

        this.filasAndColumnas = filasAndColumnas;
        this.dificultad = dificultad;
        columnasCuadrado = 3;

        tablero = new int[filasAndColumnas][filasAndColumnas];
        rellenarValores();
    }

    // Sudoku Generator
    private void rellenarValores() {
        // Rellenamos la diagonal de columnasCuadrado x columnasCuadrado matrices ya que
        // asi nos sera mucho mas facil rellenar los otros numeros
        rellenarDiagonal();

        // Rellenamos los bloques que nos faltan.
        rellenarResto(0, columnasCuadrado);

        // Eliminamos numeros y añadimos 0.
        ponerCeros();
    }

    // Fill the diagonal columnasCuadrado number of columnasCuadrado x
    // columnasCuadrado matrices
    private void rellenarDiagonal() {

        for (int u = 0; u < filasAndColumnas; u = u + columnasCuadrado)

            cuadradoRelleno(u, u);
    }

    // Retorna false si dado el cuadrado 3 x 3 contiene nums.
    private boolean cuadradoVacio(int rowStart, int colStart, int num) {
        for (int u = 0; u < columnasCuadrado; u++)
            for (int k = 0; k < columnasCuadrado; k++)
                if (tablero[rowStart + u][colStart + k] == num)
                    return false;

        return true;
    }

    // Rellenamos cuadrados
    private void cuadradoRelleno(int row, int col) {
        int num;
        for (int u = 0; u < columnasCuadrado; u++) {
            for (int k = 0; k < columnasCuadrado; k++) {
                do {
                    num = (int) Math.floor((Math.random() * 9 + 1));
                } while (!cuadradoVacio(row, col, num));

                tablero[row + u][col + k] = num;
            }
        }
    }

    // Comprobamos si se puede o no poner en la celda.
    private boolean estaLibre(int u, int k, int num) {
        return (sinNumeroFila(u, num) && sinNumeroCol(k, num)
                && cuadradoVacio(u - u % columnasCuadrado, k - k % columnasCuadrado, num));
    }

    // Comprobamos si hay numero o no en la fila
    private boolean sinNumeroFila(int u, int num) {
        for (int k = 0; k < filasAndColumnas; k++)
            if (tablero[u][k] == num)
                return false;
        return true;
    }

    // Comprobamos si hay numero o no en la columna
    private boolean sinNumeroCol(int k, int num) {
        for (int u = 0; u < filasAndColumnas; u++)
            if (tablero[u][k] == num)
                return false;
        return true;
    }

    // Funcion recursiva para ir rellenando el resto del tablero.

    private boolean rellenarResto(int u, int k) {

        if (k >= filasAndColumnas && u < filasAndColumnas - 1) {
            u = u + 1;
            k = 0;
        }
        if (u >= filasAndColumnas && k >= filasAndColumnas)
            return true;

        if (u < columnasCuadrado) {
            if (k < columnasCuadrado)
                k = columnasCuadrado;
        } else if (u < filasAndColumnas - columnasCuadrado) {
            if (k == (int) (u / columnasCuadrado) * columnasCuadrado)
                k = k + columnasCuadrado;
        } else {
            if (k == filasAndColumnas - columnasCuadrado) {
                u = u + 1;
                k = 0;
                if (u >= filasAndColumnas)
                    return true;
            }
        }

        for (int num = 1; num <= filasAndColumnas; num++) {
            if (estaLibre(u, k, num)) {
                tablero[u][k] = num;
                if (rellenarResto(u, k + 1))
                    return true;

                tablero[u][k] = 0;
            }
        }
        return false;
    }

    // Eliminamos numeros a 0 segun la dificultad.
    private void ponerCeros() {
        obtenerSudokuResuelto();
        int count = 0;
        // mientras que las celdas quitadas no lleguen al numero de dificulad seguimos
        // quitando.
        if (dificultad != 0 && dificultad < 50) {
            while (count != dificultad) {

                int celda = (int) Math.floor((Math.random() * 80 + 1));
                int u = (celda / filasAndColumnas);

                int k = celda % 9;

                if (k != 0)
                    k = k - 1;

                if (tablero[u][k] != 0) {
                    count++;
                    tablero[u][k] = 0;
                }
            }
        }
    }

    // imprimimos el sudoku
    public void printSudoku() {
        for (int u = 0; u < filasAndColumnas; u++) {
            for (int k = 0; k < filasAndColumnas; k++)
                System.out.print(tablero[u][k] + " ");
            System.out.println();
        }
        System.out.println();
    }

    public List<Integer> obtenerSudoku() {
        obtenerSudokuResuelto();
        List<Integer> sudokuFinal = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuFinal.add(tablero[i][j]);
            }
        }
        return sudokuFinal;
    }

    private void obtenerSudokuResuelto() {
        List<Integer> sudokuResuelto = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuResuelto.add(tablero[i][j]);
            }
        }
        setSudokuResueltito(sudokuResuelto);


    }

    public List<Integer> getSudokuResueltito() {
        return sudokuResueltito;
    }

    public void setSudokuResueltito(List<Integer> sudokuResueltito) {
        this.sudokuResueltito = sudokuResueltito;
    }
}