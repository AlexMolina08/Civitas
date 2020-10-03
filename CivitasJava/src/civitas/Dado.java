package civitas;

import java.util.Random;
import civitas.Diario;
/**
 * Esta clase sigue el patrón singleton
 * @author alex
 */
public class Dado{

	private static final Dado instance = new Dado(); //Única insancia de dado  
	private static final int salidaCarcel = 5;
        private static final int numeroCaras = 6;
        
        private Random random;
        private int ultimoResultado;
        private boolean debug;
        
        
        
        private Dado(){
            
            random = new Random();
            ultimoResultado = 0;
            debug = false;
            
        }
        
        //paquete
        
        int tirar(){
            if(!debug)
                ultimoResultado = random.nextInt(numeroCaras - 1) + 1 ;
            else
                ultimoResultado =  1;
            
            return ultimoResultado;
        }
        
        /*
         * Si sale un numero mayor o igual que 5 , se sale de la carcel
         * @return 
         */
        boolean salgoDeLaCarcel(){    
            return (ultimoResultado >= 5);
        }
        
        public int quienEmpieza(int n){
            int indiceJugador = new Random().nextInt(n);
            return indiceJugador;
        }
        
        void setDebug(boolean d){
            debug = d;
            //Creo un string local que se inicializa con "Normal" o "Debug" 
            //dependiendo del valor de debug
            String modo = (debug == false) ? "Normal" : "Depuración";
            
            Diario.getInstance().ocurreEvento("El dado ha sido puesto en modo "+modo);
            
        }
        
        int getUltimoResultado(){
            return ultimoResultado;
        }
        
        //public
        public static Dado getInstance(){
		return instance;
	}
        
        //private
        
        
        
	

}