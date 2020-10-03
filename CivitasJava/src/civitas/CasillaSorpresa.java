package civitas;

/**
 *
 * @author alex
 */

import java.util.ArrayList;

public class CasillaSorpresa extends Casilla{
    
    /*
        Una casilla de tipo sorpresa tiene que tener una sorpresa y el mazo donde está
    */
    
    MazoSorpresas mazo;
    Sorpresa sorpresa;
    
    CasillaSorpresa(String nombre , MazoSorpresas mazo){
        super(nombre);
        this.mazo = mazo;
    }
    
     @Override
    void recibeJugador(int actual, ArrayList<Jugador> todos ){
        /*
            Si el jugador es correcto , se llama al metodo del padre ,
            se obtiene una sorpresa del mazo y se le aplica al jugador 
        */
        if (jugadorCorrecto(actual,todos)){
            super.recibeJugador(actual, todos);
            Sorpresa sorpresa = mazo.siguiente();
            sorpresa.aplicarAJugador(actual, todos);
        }
    }
    
    @Override
    public String toString(){   
        String info = super.toString();
        if (sorpresa != null)
                info += "\t- Sorpresa = " + sorpresa.toString() + "\n";
        
        return info;
    } 
}
