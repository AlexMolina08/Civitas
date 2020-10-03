package civitas;

/**
 *
 * @author alex
 */

import java.util.ArrayList;

public class SorpresaIrCasilla extends Sorpresa{
    
    /*
        Una sorpresa que te lleva a una casilla necesita el tablero y la
        casilla a la que ir (valor)
    */
    
    private Tablero tablero;
    private int valor;
    
    SorpresaIrCasilla (Tablero tablero, int valor, String texto){
        super(texto); 
        this.tablero = tablero;
        this.valor = valor;
    }
    
    @Override
    void aplicarAJugador (int actual, ArrayList<Jugador> todos){
        
        /*
            Si el jugador es correcto , se llama al metodo del padre y 
            se mueve al jugador a la casilla correspondiente
        */
        
        if (jugadorCorrecto(actual, todos)){
            super.aplicarAJugador(actual, todos);
            
            //Calcular posicion en el tablero por si pasa por salida
            int casillaActual = (todos.get(actual).getNumCasillaActual());
            int tirada = tablero.calcularTirada(casillaActual, valor);
            casillaActual = tablero.nuevaPosicion (casillaActual, tirada);
            
            //Mover a nueva posicion del tablero
            todos.get(actual).moverACasilla(casillaActual);
            
            //La casilla recibe al jugador
            tablero.getCasilla(casillaActual).recibeJugador(actual, todos);
        }
    }
    
}
