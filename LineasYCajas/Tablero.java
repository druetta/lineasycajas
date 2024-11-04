package LineasYCajas;

import java.util.HashSet;
import java.util.Set;

public class Tablero {
    public final Celda[][] matriz = new Celda[11][11];
    public final Set<Coordenada> posicionesCajas = new HashSet<>();


    public Tablero() {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (i % 2 == 0 && j % 2 == 0) {          // par : par, Vertice
                    matriz[i][j] = new Celda('X');
                }else if (i % 2 != 0 && j % 2 == 0) {    // impar : par, Vertical
                    matriz[i][j] = new Celda('V');
                } else if (i % 2 == 0) {                 // par : impar, Horizontal (j es impar si o si)
                    matriz[i][j] = new Celda('H');
                }else{                                   // impar : impar, Caja
                    matriz[i][j] = new Celda('C');
                    posicionesCajas.add(new Coordenada(i, j));
                }
            }
        }

    }


}
