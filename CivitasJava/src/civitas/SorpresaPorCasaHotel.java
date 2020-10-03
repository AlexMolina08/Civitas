package civitas;

/**
 *
 * @author alex
 */

import java.util.ArrayList;


public class SorpresaPorCasaHotel extends Sorpresa{
    
    
    /*
        Una sorpresa que le haga pagar o cobrar al jugador por el número de 
        casas que tenga, necesita un atributo valor (precio que debe pagar ó cobrar)
    */
    private int valor;
    
    SorpresaPorCasaHotel (String texto,int valor){
        super(texto);
        this.valor = valor;
    }
    
    @Override
    void aplicarAJugador (int actual, ArrayList<Jugador> todos){
        
        /*
            Si el jugador es correcto , se llama al metodo del padre 
            y se modifica el saldo del jugador en funcion del numero de propiedades
            que tenga
        */
        
        if (jugadorCorrecto(actual, todos)){
            super.aplicarAJugador(actual, todos);
            Jugador j = todos.get(actual);
            j.modificarSaldo(j.cantidadCasasHoteles() * valor);
        }
    }
}