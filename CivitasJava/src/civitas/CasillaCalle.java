package civitas;

/**
 *
 * @author alex
 */
import java.util.ArrayList;

public class CasillaCalle extends Casilla{
    
    /*Una  casilla de tipo calle tiene un titulo de propiedad 
    añadimos el atributo */
    
    private TituloPropiedad titulo;
    
    CasillaCalle(TituloPropiedad titulo){
        super(titulo.getNombre()); //El nombre de la casilla será el nombre del titulo
                                   //Se llama al constructor de la clase padre
        this.titulo = titulo;
    }   
    
    void recibeJugador (int actual, ArrayList<Jugador> todos ){
        if (jugadorCorrecto(actual,todos)){
            super.recibeJugador(actual, todos); //Llamamos al metodo de casilla
            /*Vemos si este titulo tiene propietario y en base a ello
            se podrá comprar o no*/
            if (!titulo.tienePropietario())
                todos.get(actual).puedeComprarCasilla();    
            else  
                titulo.tramitarAlquiler(todos.get(actual));    
        }
    }
    
    TituloPropiedad getTituloPropiedad (){
        return titulo;
    }
    
    @Override
    public String toString(){

        String info = super.toString(); //Llamamos al metodo de la clase padre,
                                        //ya que esa info nos hace falta
                
        if (titulo != null)
                info += "\t- Titulo de propiedad = " + titulo.toString() + "\n";
        
        return info;
    }
}
