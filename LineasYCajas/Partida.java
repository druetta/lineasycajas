package LineasYCajas;

import java.util.HashSet;
import java.util.Set;

public class Partida {

    public int puntosA;
    public int puntosB;
    public boolean turno; // true: jugador A, false: Jugador B

    public Tablero tablero;

    public Partida() {
        puntosA = 0;
        puntosB = 0;
        tablero = new Tablero();
    }

    public void CambioTurno(){
        turno = !turno;
    }

}
