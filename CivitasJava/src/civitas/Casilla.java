package civitas;

import java.util.ArrayList;

public class Casilla{




    private static int carcel; //numero de la casilla de carcel
    
    private String nombre;
    private float importe;
    
    //ASOCIACIONES
    private TipoCasilla tipo;
    private TituloPropiedad tituloPropiedad;
    private Sorpresa sorpresa;
    private MazoSorpresas mazo;
    
    
    
    
    Casilla(String nombre)
    {
        init();
        tipo = TipoCasilla.DESCANSO;
        this.nombre = nombre;
        
    }
    
    
    
    
    Casilla(TituloPropiedad titulo)
    {
        init();
        tipo = TipoCasilla.CALLE;
        tituloPropiedad=titulo;
        nombre = titulo.getNombre();
        
    }
    
    
    
    
    Casilla(float cantidad , String nombre){
        init();
        this.importe=cantidad;
        this.nombre=nombre;
        this.tipo = TipoCasilla.IMPUESTO;
    }
    
    
    
    Casilla(int numCasillaCarcel , String nombre){
        init();
        this.carcel=numCasillaCarcel;
        this.nombre=nombre;
        this.tipo = TipoCasilla.JUEZ;
    }
    
    
    
    Casilla(MazoSorpresas mazo , String nombre){
        init();
        this.mazo=mazo;
        this.nombre=nombre;
        tipo = TipoCasilla.SORPRESA;
    }
    
    

    
    public static int getCarcel()
    {
        return carcel;
    }   
    
     
     
    void recibeJugador(int iactual , ArrayList<Jugador> todos)
    {
        switch(tipo){
            case CALLE:
                recibeJugador_calle(iactual,todos);
                break;
            case IMPUESTO:
                recibeJugador_impuesto(iactual,todos);
                break;
            case JUEZ:
                recibeJugador_juez(iactual,todos);
                break;
            case SORPRESA:
                recibeJugador_sorpresa(iactual,todos);
                break;
            default:
                informe(iactual,todos);
                        
                    }
        
    }
    
    public String getNombre(){
        return nombre;
    }
        
    TituloPropiedad getTituloPropiedad()
    {
        return tituloPropiedad;
    }
    
    @Override //Sobrecarga del método de la clase padre Object 
    public String toString(){
        
        String info = "Casilla :"+nombre
                +"\n\timporte: "+importe
                +"\n\tMazo:"+mazo
                +"\n\tSorpresa:"+sorpresa
                +"\n\ttipo:"+tipo
                +"\n\tTitulo de propiedad:"+tituloPropiedad
                +"\n\tCárcel:"+carcel;
        
        return info;
        
    }
    
    
    public boolean jugadorCorrecto(int iactual , ArrayList<Jugador> todos){
        
        boolean indiceCorrecto = iactual >= 0 && todos.size() > iactual;
        
        return indiceCorrecto;
    }
    
    private void informe(int iactual , ArrayList<Jugador> todos){
        Diario.getInstance().ocurreEvento("El jugador "+todos.get(iactual).getNombre()
        +" ha caido en la casilla "+nombre+toString());
    }
    
    private void init(){
        nombre = "";
        tituloPropiedad = null;
        sorpresa = null;
        mazo = null;
        importe = 0f;
            
    }
    
    
    private void recibeJugador_calle(int actual , ArrayList<Jugador> todos)
    {
        if(jugadorCorrecto(actual,todos))
        {
            informe(actual,todos);
            Jugador jugador=todos.get(actual);
            
            if(!tituloPropiedad.tienePropietario())
            {
                jugador.puedeComprarCasilla();
            }
            else
            {
                tituloPropiedad.tramitarAlquiler(jugador);
                
            }
             
        }
        
    }
    
    private void recibeJugador_impuesto(int iactual , ArrayList<Jugador> todos)
    {
        
        
        
        if(jugadorCorrecto(iactual, todos)){
            
            informe(iactual, todos);
            todos.get(iactual).pagaImpuesto(importe);
            
        }
                 
    }
    
    private void recibeJugador_juez(int iactual , ArrayList<Jugador> todos)
    {
        
        if(jugadorCorrecto(iactual, todos))
        {
            informe(iactual, todos);
            todos.get(iactual).encarcelar(carcel);
        }     
    }
    
    private void recibeJugador_sorpresa(int iactual , ArrayList<Jugador> todos)
    {  
        if(jugadorCorrecto(iactual,todos))
        {
            sorpresa=mazo.siguiente();//IMPY
            informe(iactual,todos);
            sorpresa.aplicarAJugador(iactual, todos);
        }
    }
    
    
    
    
    //MAIN DE PRUEBA
    public static void main(String[]args)
    {
        int i=1;
        TituloPropiedad X=new TituloPropiedad("Calle",100*i,3,50*i,500+100*i,200);
        Casilla c = new Casilla(X);
        
        System.out.println(c.toString());
        
    }
    
    
}
