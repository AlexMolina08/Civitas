/*
Esta clase sólo tiene main porque será la que use para probar el juego,
la vista y el controlador
*/

package juegoTexto;

import civitas.CivitasJuego;
import GUI.Dado;
//import JuegoTexto.VistaTextual
//import JuegoTexto.Controlador
import java.util.ArrayList;

public class Civitas {
    
    
    public static void main (String args[]){
        
        //Creo jugadores
        ArrayList<String> nombresJugadores = new ArrayList<>();
        nombresJugadores.add("Alex");
        nombresJugadores.add("Rafa");
        nombresJugadores.add("Carlos");
        nombresJugadores.add("Antonio");
        
        //Creo juego en modo debug
        CivitasJuego juego = new CivitasJuego(nombresJugadores);
        Dado.getInstance().setDebug(true);
        
        //Jugar
        VistaTextual interfaz = new VistaTextual();
        Controlador controlador = new Controlador(juego,interfaz);
        controlador.juega();
        
        
        
    }
}