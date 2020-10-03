/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;
import juegoTexto.Controlador;
import juegoTexto.VistaTextual;
import java.util.ArrayList;
/**
 *
 * @author ravolk
 */
public class Civitas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        VistaTextual vista=new VistaTextual();
        ArrayList<String> jugadores;
        jugadores=new ArrayList<String>();
        jugadores.add("J1");
        jugadores.add("J2");
        CivitasJuego juego=new CivitasJuego(jugadores);
        Dado.getInstance().setDebug(true);
        Controlador control=new Controlador(juego,vista);
        control.juega();
    }
    
}
