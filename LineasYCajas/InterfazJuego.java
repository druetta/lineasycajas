package LineasYCajas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazJuego extends JFrame {
    private final Partida partida;
    private final JPanel panelTablero;
    private String nombreA;
    private String nombreB;

    private final Color rojoOscuro = new Color(255, 50, 50);
    private final Color azulOscuro = new Color(8, 100, 255);

    public InterfazJuego(Partida partida) {
        this.partida = partida;
        this.panelTablero = new JPanel(new GridLayout(11, 11)); // Tablero de 9x5

        setTitle("Líneas y Cajas");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inicializarTablero();
        add(panelTablero);
    }

    private void inicializarTablero() {
        Celda[][] matriz = partida.tablero.matriz;

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                Celda celda = matriz[i][j];
                if (celda.estado == 'X') {
                    // Vértices - no son clickeables
                    // panelTablero.add(new JLabel());
                    JPanel panelCaja = new JPanel();
                    panelCaja.setBackground(Color.BLACK);
                    panelTablero.add(panelCaja);
                } else if (celda.estado == 'H' || celda.estado == 'V') {
                    // Líneas Horizontales y Verticales - Botones
                    JButton botonLinea = new JButton();
                    botonLinea.addActionListener(new ManejadorLinea(i, j));
                    panelTablero.add(botonLinea);
                } else if (celda.estado == 'C') {
                    // Caja - Panel de color para indicar el propietario
                    JPanel panelCaja = new JPanel();
                    panelCaja.setBackground(Color.WHITE); // Color por defecto, cambiar según el jugador
                    panelTablero.add(panelCaja);
                }
            }
        }
    }

    private class ManejadorLinea implements ActionListener {
        private final int fila, columna;

        private Color rojoClaro = new Color(255, 102, 102);
        private Color azulClaro = new Color(102, 150, 255);

        public ManejadorLinea(int fila, int columna) {
            this.fila = fila;
            this.columna = columna;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Celda celda = partida.tablero.matriz[fila][columna];

            // Marcar la línea según el jugador actual y desactivar el botón
            if (partida.turno) {
                celda.estado = 'A';
                ((JButton) e.getSource()).setBackground(rojoClaro);
                ((JButton) e.getSource()).setText("A");
                partida.puntosA++;
            } else {
                celda.estado = 'B';
                ((JButton) e.getSource()).setBackground(azulClaro);
                ((JButton) e.getSource()).setText("B");
                partida.puntosB++;
            }
            ((JButton) e.getSource()).setEnabled(false); // Desactivar el botón después de usarlo

            if (!actualizarCajas()) {
                partida.CambioTurno();
            }
            actualizarCajas(); // CAMBIAR ACA SI SE QUIERE MANTENER ALTERNADO LOS TURNOS
                                // , Y SI SE QUIERE PINTAR AL QUE ENCERRO LA CAJA O AL OTRO

            partidaFinalizada(); // Si la partida acabo, se mostrara el dialogo del ganador


        }
    }

    private boolean actualizarCajas() {
        boolean bandera = false;
        Celda[][] matriz = partida.tablero.matriz;
        for (int i = 1; i < 11; i += 2) {
            for (int j = 1; j < 11; j += 2) {
                if (matriz[i][j].estado == 'C') {
                    JPanel panelCaja = (JPanel) panelTablero.getComponent(i * 11 + j);

                    // Verificar si la caja ya fue capturada
                    if (panelCaja.getBackground() != Color.WHITE) {
                        continue; // La caja ya tiene color, así que la saltamos
                    }

                    // Verificar si las 4 líneas alrededor de la caja están marcadas
                    boolean encerrada = (matriz[i-1][j].estado == 'A' || matriz[i-1][j].estado == 'B') &&
                            (matriz[i+1][j].estado == 'A' || matriz[i+1][j].estado == 'B') &&
                            (matriz[i][j-1].estado == 'A' || matriz[i][j-1].estado == 'B') &&
                            (matriz[i][j+1].estado == 'A' || matriz[i][j+1].estado == 'B');

                    if (encerrada) {
                        panelCaja.setBackground(partida.turno ? rojoOscuro : azulOscuro); // Fijar color al jugador que encierra la caja
                        Coordenada coo = new Coordenada(i, j);
                        partida.tablero.posicionesCajas.remove(coo);
                        bandera = true;
                    }
                }
            }
        }
        return bandera;
    }

    public boolean partidaFinalizada() {
         if (partida.tablero.posicionesCajas.isEmpty()) {
            mostrarGanador(); // Mostrar el ganador si la partida ha finalizado
            int resultado3 = JOptionPane.showConfirmDialog(this,
                    "¿Desean volver a jugar...?", "Reiniciar Juego",
                    JOptionPane.OK_CANCEL_OPTION);
            if (resultado3 == JOptionPane.OK_OPTION) {
                setVisible(false);
                Main.main(new String[0]);
            }
            if (resultado3 == JOptionPane.CANCEL_OPTION) {
                setVisible(false);
            }
            return true; // Indica que la partida ha finalizado
        }
        return false; // La partida sigue en juego
    }


    private void mostrarGanador() {
        int puntosJugadorA = partida.puntosA; // Puntos del jugador A
        int puntosJugadorB = partida.puntosB; // Puntos del jugador B

        String mensaje;
        Color color;

        if (puntosJugadorA > puntosJugadorB) {
            mensaje = "¡Ganador: Jugador A!";
            color = rojoOscuro;
        } else {
            mensaje = "¡Ganador: Jugador B!";
            color = azulOscuro;
        }

        JLabel mensajeLabel = new JLabel(mensaje);
        mensajeLabel.setForeground(color);

        JOptionPane.showMessageDialog(this, mensajeLabel, "Resultado", JOptionPane.INFORMATION_MESSAGE);
    }

}

