package civitas;

/**
 *
 * @author alex
 */

import java.util.ArrayList;

public class SorpresaIrCarcel extends Sorpresa{
    
    /*
        Una sorpresa de tipo irCarcel necesita el tablero donde está la carcel
        y la posición de la carcel (atributo valor)
    */
    
    private Tablero tablero;
    private int valor;
    
    SorpresaIrCarcel(Tablero tablero){
        super("A PRISIÓN !!");
        this.tablero = tablero;
        valor = tablero.getCarcel();
    }
    
    @Override
    void aplicarAJugador (int actual, ArrayList<Jugador> todos){
        /*
            Si el jugador es correcto , se llama al metodo del padre
            y se le encarcela
        */
        if (jugadorCorrecto(actual, todos)){
            super.aplicarAJugador(actual, todos);
            todos.get(actual).encarcelar(tablero.getCarcel());
        }
    }
}
