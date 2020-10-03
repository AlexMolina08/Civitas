package civitas;

import java.util.ArrayList;

/**
 *
 * @author alex
 */
public class SorpresaSalirCarcel extends Sorpresa{
    
    private MazoSorpresas mazo;
    
    SorpresaSalirCarcel (MazoSorpresas mazo){
        super( "HAS SALIDO DE PRISIÓN !" );
        this.mazo = mazo;
    }
    
    void salirDelMazo(){
        mazo.inhabilitarCartaEspecial(this);
    }
    
    void usada(){
        mazo.habilitarCartaEspecial(this);
    }
    
    @Override
    void aplicarAJugador (int actual, ArrayList<Jugador> todos){
        
        /*
            Si el jugador es correcto ,se llama al metodo del padre,
            si ningun jugador tiene el salvoconducto , lo obtiene el jugador 
            actual
        */
        
        if (jugadorCorrecto(actual, todos)){
            super.aplicarAJugador(actual, todos);

            int numJugadores = todos.size();
            
            Boolean alguienTieneSalvoConducto = false;
            
            for (int i=0; i<numJugadores; ++i)
                if (todos.get(i).tieneSalvoconducto()) alguienTieneSalvoConducto = true;
            
            if (!alguienTieneSalvoConducto){
                todos.get(actual).obtenerSalvoConducto(this);
                this.salirDelMazo();
            }     
        }
    }
}
