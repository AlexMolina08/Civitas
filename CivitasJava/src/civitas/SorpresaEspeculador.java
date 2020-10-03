package civitas;

/**
 *
 * @author alex
 */

import java.util.ArrayList;

/*
    Al aplicar esta sorpresa , el jugador se convierte en especulador, lo que 
    implica que:

    *)Puede construir el doble de casas y hoteles respecto a un jugador normal
      -factorEspeculador-
    *)Si se le va a encarcelar, puede optar a pagar una fianza si no tienen
      el salvoconducto o la carta de libertad (atributo fianza)
    *)Paga la mitad de impuestos 
*/

public class SorpresaEspeculador extends Sorpresa{
    
    private float fianza;
    
    SorpresaEspeculador(float fianza){
        super("Conversión a especulador");
        this.fianza = fianza;
    }
    
    void aplicarAJugador (int actual, ArrayList<Jugador> todos){
        
        /*
            Si el jugador es correcto , se llama al metodo del padre
            y se le asigna el atributo fianza al jugador
        */
        if (jugadorCorrecto(actual, todos)){
            super.aplicarAJugador(actual, todos);
            todos.set(actual, new JugadorEspeculador(todos.get(actual),fianza));
        }
    }
}
