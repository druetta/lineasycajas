package LineasYCajas;

public class Main {

    public static void printMatrix(Tablero t) {
        Celda[][] m = t.matriz;
        for (int i = 0; i < m.length; i++) { // Recorre las filas
            for (int j = 0; j < m[i].length; j++) { // Recorre las columnas
                System.out.print((m[i][j]).estado + "      ");
            }
            System.out.println(); // Salto de línea después de cada fila
        }
    }


    public static void main(String[] args) {

        Partida partida = new Partida();
        //printMatrix(partida.tablero); // Descomentar si se quiere ver la logica aplicada a la matriz
        InterfazJuego juego = new InterfazJuego(partida);
        juego.setVisible(true);
    }


}
