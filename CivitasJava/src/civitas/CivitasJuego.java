package civitas;


import java.util.Collections;
import java.util.ArrayList;
import java.util.Random;
public class CivitasJuego {
    
    private int indiceJugadoractual;
    private ArrayList<Jugador> jugadores;
    private MazoSorpresas mazo;
    private Tablero tablero;
    private GestorEstados gestorEstados; 
    private EstadosJuego estado;
    
    CivitasJuego(ArrayList<String> nombres)
    {
       
       jugadores = new ArrayList<Jugador>();
       
       for(String n:nombres)
            jugadores.add(new Jugador(n));
       
       gestorEstados = new GestorEstados();
       estado = gestorEstados.estadoInicial();
       
       indiceJugadoractual = Dado.getInstance().quienEmpieza(jugadores.size());
       
       mazo = new MazoSorpresas();

        inicializaTablero(mazo);
        inicializarMazoSorpresas(tablero);

       
    }

    public int getIndiceJugadoractual() {
        return indiceJugadoractual;
    }

    public MazoSorpresas getMazo() {
        return mazo;
    }
    
    void actualizarInfo(){
        
        Jugador jugador = jugadores.get(indiceJugadoractual);
        
        System.out.println(jugador.toString());
        
        
    }
//    
    private void avanzaJugador()
    {
       Jugador jugadorActual=jugadores.get(indiceJugadoractual);
       int posicionActual=jugadorActual.getNumCasillaActual();
       int tirada=Dado.getInstance().tirar();
       int posicionNueva=tablero.nuevaPosicion(posicionActual,tirada);
       Casilla casilla=tablero.getCasilla(posicionNueva);
       
       
       contabilizarPasosPorSalida(jugadorActual);
       jugadorActual.moverACasilla(posicionNueva);
       casilla.recibeJugador(indiceJugadoractual, jugadores);
       contabilizarPasosPorSalida(jugadorActual);
       
    }
    
    public Boolean cancelarHipoteca(int ip)
    {
        return jugadores.get(indiceJugadoractual).cancelarHipoteca(ip);
    }        
    public Boolean comprar()
    {   
        boolean res=false;
        Jugador jugadorActual=jugadores.get(indiceJugadoractual);
        int numCasillaActual=jugadorActual.getNumCasillaActual();
        Casilla casilla=tablero.getCasilla(numCasillaActual);
        TituloPropiedad titulo=casilla.getTituloPropiedad();
        res=jugadorActual.comprar(titulo);
        
        return res;
    }
    
    public Boolean construirCasa(int ip){
       return jugadores.get(indiceJugadoractual).construirCasa(ip);
    }
    
    public Boolean construirHotel(int ip)
    {
       return jugadores.get(indiceJugadoractual).construirHotel(ip);        
    }
    
    private void contabilizarPasosPorSalida(Jugador jugadorActual)
    {
        while(tablero.getPorSalida()>0)
        {
            jugadorActual.pasaPorSalida();
        }   
    }
    
    public Boolean finalDelJuego()
    {
        Boolean resultado=false;
        for(int i=0;i<jugadores.size();i++)
        {
            resultado=jugadores.get(i).enBancarrota();
        }
        
        return resultado;       
    }
    
    public Casilla getCasillaActual()
    {
        return tablero.getCasilla(jugadores.get(indiceJugadoractual).getNumCasillaActual());        
    }
    
    public Jugador getJugadorActual()
    {
        return jugadores.get(indiceJugadoractual);
    } 
    
    public Boolean hipotecar(int ip)
    {
       return jugadores.get(indiceJugadoractual).hipotecar(ip);
    }            
    
    public String infoJugadorTexto()
    {
        return jugadores.get(indiceJugadoractual).toString();
    }   
    
    private void inicializarMazoSorpresas(Tablero tablero)
    {
        
        //Creación de una sorpresa de cada tipo
        //Almacenamiento en el mazo
        Random ram=new Random();
        for(TipoSorpresa tipo:TipoSorpresa.values())
        {
            switch(tipo)
            {
                case IRCARCEL:
                    mazo.alMazo(new Sorpresa(tipo,tablero));   
                    break;
                case IRCASILLA:
                   mazo.alMazo(new Sorpresa(tipo,tablero,ram.nextInt(tablero.size()),tipo.toString()));
                   break;
                case SALIRCARCEL:
                    mazo.alMazo(new Sorpresa(tipo,mazo));
                    break;
                default:
                    mazo.alMazo(new Sorpresa(tipo,ram.nextInt(400)+100,tipo.toString()));
            }
        }
        
    } 
    
    private void inicializaTablero(MazoSorpresas mazo)
    {
       tablero=new Tablero(Casilla.getCarcel());
       for(int i=1;i<3;i++)
       {
        TituloPropiedad prop=new TituloPropiedad("Calle"+i,100*i,3,50*i,500+100*i,200);
        Casilla casilla=new Casilla(prop);
        tablero.añadeCasilla(casilla); 
       }
       Casilla cas1;

       cas1=new Casilla(mazo,"SorpreCasilla");
       tablero.añadeCasilla(cas1);
       tablero.añadeCasilla(new Casilla(100f,"Impuesto"));
       tablero.añadeJuez();
       

       
    }
    
    private void pasarTurno()
    {   
        if(indiceJugadoractual<jugadores.size()-1){
            indiceJugadoractual++;     
        }else{
            indiceJugadoractual=0;
        }
        
    }
    
    private ArrayList<Jugador> ranking()
    {
      
      Collections.sort(jugadores);
      return jugadores;
    }
    
    
    public Boolean salirCarcelPagando()
    {
       return jugadores.get(indiceJugadoractual).salirCarcelPagando(); 
    }
    
    public Boolean salirCarcelTirando()
    {
       return jugadores.get(indiceJugadoractual).salirCarcelTirando();        
    }
    
    public OperacionesJuego siguientePaso()
    {
       Jugador jugadorActual=jugadores.get(indiceJugadoractual);
       OperacionesJuego operacion=gestorEstados.operacionesPermitidas(jugadorActual, estado);
       
       if(operacion==OperacionesJuego.PASAR_TURNO)
       {
           pasarTurno();
       }
       else if(operacion==OperacionesJuego.AVANZAR)
       {
           avanzaJugador();
       }
            siguientePasoCompletado(operacion);
       
       return operacion;
       
    }
    
    public void siguientePasoCompletado(OperacionesJuego operacion)
    {

        estado=gestorEstados.siguienteEstado(getJugadorActual(),estado,operacion);
       
    }
    
    public Boolean vender(int ip)
    {
       return jugadores.get(indiceJugadoractual).vender(ip);
    }
    
    
    
    public static void main(String args[]){
        
        ArrayList jugadores = new ArrayList<String>();
        
        jugadores.add("paco");
        jugadores.add("juan");
        
        CivitasJuego game = new CivitasJuego(jugadores);
        
        game.actualizarInfo();
        
        MazoSorpresas generado = game.getMazo();
        
        Sorpresa s = generado.siguiente();
        
        
        
    }
    
    public String getEstado()
    {
        return estado.toString();
    }
    
}
