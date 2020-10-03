package civitas;

import java.util.ArrayList;

/**
 *
 * @author alex
 */
public class SorpresaPagarCobrar extends Sorpresa{
    
    /*
        Una sorpresa de tipo pagar-cobrar necesita un atributo valor (dinero a 
        pagar o cobrar)
    */
    
    private int valor;
    
    SorpresaPagarCobrar(String texto , int valor){
        super(texto);
        this.valor = valor;
    }
    
    
    @Override
    void aplicarAJugador (int actual, ArrayList<Jugador> todos){
        
        /*
            Si el jugador es correcto , se llama al metodo del padre
            y se modifica su saldo
        */
        if (jugadorCorrecto(actual, todos)){
            super.aplicarAJugador(actual, todos);
            todos.get(actual).modificarSaldo(valor);
        }
    }
}