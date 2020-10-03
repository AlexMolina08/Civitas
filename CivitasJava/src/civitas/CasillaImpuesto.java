package civitas;

/**
 *
 * @author alex
 */
import java.util.ArrayList;


public class CasillaImpuesto extends Casilla {
    
    /*
        Una casilla de tipo impuesto tiene un importe
    */
    private float importe;
    
    CasillaImpuesto (String nombre , float cantidad){
        super(nombre);
        importe = cantidad;
    }
    
    void recibeJugador (int actual, ArrayList<Jugador> todos ){
        
        /*
            Si el jugador es correcto , se llama al metodo de la clase padre
            y además hacemos que el jugador pague el impuesto
        */
        if (jugadorCorrecto(actual,todos)){
            super.recibeJugador(actual, todos);
            todos.get(actual).pagaImpuesto(importe);
        }
    }
    
}
