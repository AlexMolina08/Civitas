package civitas;

/**
 *
 * @author alex
 */

import java.util.ArrayList;

public class SorpresaPorJugador extends Sorpresa{
    
    /*
      Una sorpresa que haga pagar o cobrar a un jugador por cada uno de los 
      jugadores , necesita un atributo valor
    */
    
    private int valor;
    
    SorpresaPorJugador (String texto,int valor){
        super(texto);
        this.valor = valor;
    }
    
    void aplicarAJugador (int actual, ArrayList<Jugador> todos){
        /*
            Si el jugador es correcto, se llama al método del padre 
            y el resto de jugadores le
        
        */
        
        if (jugadorCorrecto(actual, todos)){
            
            super.aplicarAJugador(actual, todos);
            
            int numJugadores = todos.size();
            
            Sorpresa pagar = new SorpresaPagarCobrar("Pagas "+ valor +" a "+todos.get(actual).getNombre() ,
                                                     -valor);
            Sorpresa cobro = new SorpresaPagarCobrar(" Recibes " + valor + " de cada jugador",
                                                     (numJugadores-1)*valor);
            //Aplico pagos
           
            for (int i=0; i<numJugadores; ++i){
                if (i != actual) pagar.aplicarAJugador(i,todos);
            }
            
            //El jugador actual cobra
            cobro.aplicarAJugador(actual, todos);
            
        }
    }   
}