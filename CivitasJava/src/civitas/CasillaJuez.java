package civitas;

/**
 *
 * @author alex
 */

import java.util.ArrayList;

public class CasillaJuez extends Casilla{

    private static int carcel = 3; //numero de la casilla de carcel
    
    CasillaJuez(String nombre , int numCasillaCarcel){
        super(nombre);
        carcel = numCasillaCarcel;
    }
    
    @Override
    void recibeJugador(int actual, ArrayList<Jugador> todos ){
            /*
                Si el jugador es correcto , se llama al metodo del padre 
                y se encarcela al jugador
            */
        if(jugadorCorrecto(actual,todos)){
            super.recibeJugador(actual, todos);
            todos.get(actual).encarcelar(carcel);
        }
    }
    
    static int getNumCasillaCarcel(){
        return carcel;
    }
}
