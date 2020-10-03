package civitas;

import java.util.ArrayList;

public class Sorpresa{

    private String texto;
    private int valor;
    
    //asociaciones
    MazoSorpresas mazo;
    TipoSorpresa tipo;
    Tablero tablero;
    
    
    //paquete
    
    Sorpresa(TipoSorpresa tipo , Tablero tablero){//Constructor Enviar Carcel
        this(tipo,tablero,-1,tipo.toString());
    }
    
    Sorpresa(TipoSorpresa tipo , Tablero tablero , int valor , String texto){//Envia otra casilla
        
        
        this.tipo = tipo;
        this.tablero = tablero;
        this.texto = texto;
        this.mazo = null;
        this.valor = valor;
    }
    
    Sorpresa(TipoSorpresa tipo , int valor , String texto){//Resto sorpresas
        
        this(tipo,null,valor,texto);
        
    }
    
    Sorpresa(TipoSorpresa tipo , MazoSorpresas mazo){//EVITAR CARCEL
        init();
        this.tipo = tipo;
        this.texto= tipo.toString();
        this.mazo = mazo; 
        
    }
    
    void aplicarAJugador(int actual , ArrayList<Jugador> todos){
        

        switch(tipo){         
            case IRCARCEL:
                aplicarAJugador_irCarcel(actual,todos);
            case IRCASILLA:
                aplicarAJugador_irACasilla(actual, todos);
            case SALIRCARCEL:
                aplicarAJugador_salirCarcel(actual, todos);
            case PAGARCOBRAR:
                aplicarAJugador_pagarCobrar(actual, todos);
            case PORCASAHOTEL:
                    aplicarAJugador_porCasaHotel(actual, todos);
            case PORJUGADOR:
                aplicarAJugador_porJugador(actual, todos);
        }
           
        
    }
    
    void salirDelMazo(){
       
        if(tipo == TipoSorpresa.SALIRCARCEL)
        {
            mazo.inhabilitarCartaEspecial(this);
        }
        
    }
    
    void usada(){
        
        if(tipo == TipoSorpresa.SALIRCARCEL)
            mazo.habilitarCartaEspecial(this);
        
    }
    
    //public
    public boolean jugadorCorrecto(int actual , ArrayList<Jugador> todos){
        
        return (actual >= 0) && (actual<todos.size());
    }
    
    @Override
    public String toString(){
        
        return "\nnombre de la sorpresa:"+tipo+valor+"\n";
    }
    
    //private
    
    private void init(){
        
        valor = -1;
        mazo = null; 
        tablero = null;
        
    }
    
    private void aplicarAJugador_irACasilla(int actual , ArrayList<Jugador> todos){
        
        if(jugadorCorrecto(actual,todos)){
            
            informe(actual, todos);
            
            int casillaActual = todos.get(actual).getNumCasillaActual();

            int tirada=tablero.CalcularTirada(casillaActual, valor);
            
            int nuevaPosicion = tablero.nuevaPosicion(casillaActual, tirada);
            
            todos.get(actual).moverACasilla(nuevaPosicion);
            
            tablero.getCasilla(nuevaPosicion).recibeJugador(actual, todos);
            
        }
        
        
    }
    
    private void aplicarAJugador_irCarcel(int actual , ArrayList<Jugador> todos){
        
        boolean esCorrecto = jugadorCorrecto(actual, todos);
        
        if(esCorrecto){
            informe(actual, todos);
            todos.get(actual).encarcelar(tablero.getCarcel());
        }     
    }
    
    private void aplicarAJugador_pagarCobrar(int actual , ArrayList<Jugador> todos){
        
        Jugador jugador = todos.get(actual);
        boolean esCorrecto = jugadorCorrecto(actual, todos); 
        
        if(esCorrecto){
            informe(actual,todos);
            jugador.modificarSaldo(valor);
        } 
    }
    
    private void aplicarAJugador_porCasaHotel(int actual , ArrayList<Jugador> todos){
        
        Jugador jugador = todos.get(actual);
        boolean esCorrecto = jugadorCorrecto(actual, todos);
        
        if(esCorrecto){
            informe(actual, todos);
            jugador.modificarSaldo(valor * jugador.cantidadCasasHoteles());
            
        }     
    }
        private void aplicarAJugador_porJugador(int actual , ArrayList<Jugador> todos){
        
        Jugador jugador = todos.get(actual);
        boolean esCorrecto = jugadorCorrecto(actual, todos);
        
        if(esCorrecto)
        {
            informe(actual,todos);
            Sorpresa pagarCobrar1 = new Sorpresa(TipoSorpresa.PAGARCOBRAR , -1*valor , "cobra");
            Sorpresa pagarCobrar2 = new Sorpresa(TipoSorpresa.PAGARCOBRAR , (todos.size()-1)*valor , "cobra");
            
            for(int i = 0 ; i < todos.size() ; i++)
            {
                if(todos.get(i) != jugador)
                {
                    pagarCobrar1.aplicarAJugador_pagarCobrar(i, todos);
                }
            }
            
            pagarCobrar2.aplicarAJugador_pagarCobrar(actual, todos);
        }    
        
    }
    
    
    private void aplicarAJugador_salirCarcel(int actual , ArrayList<Jugador> todos){
        
        Jugador jugador = todos.get(actual);
        boolean esCorrecto = jugadorCorrecto(actual, todos);
        
        if(esCorrecto){
            boolean haySalvoConducto = false;
            informe(actual, todos);
            
            for(int i = 0 ; i < todos.size() ; i++){
                if (todos.get(i).tieneSalvoconducto()){
                    haySalvoConducto = true;   
                }
            }
            
            if(!haySalvoConducto){
                jugador.obtenerSalvoConducto(this);
                salirDelMazo();
            }
            
        }
        
    }
    
    private void informe(int actual , ArrayList<Jugador> todos){
        
        Diario.getInstance().ocurreEvento("Se ha aplicado una sorpresa a "
        +todos.get(actual).getNombre());
        
    }
    
    
        public static void main(String [] args){
        
        Jugador j1=new Jugador("Roberto");
        
        Tablero t1=new Tablero(4);
        t1.añadeJuez();
        t1.añadeCasilla(new Casilla(new TituloPropiedad("calle1",200,2,200,500,250)));
        t1.añadeCasilla(new Casilla(new TituloPropiedad("calle2",300,2,200,600,260)));
        
        Sorpresa s1=new Sorpresa(TipoSorpresa.IRCASILLA,t1,3,"ve");
        
        
    }
}
