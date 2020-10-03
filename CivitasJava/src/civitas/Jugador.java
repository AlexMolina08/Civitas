package civitas;

import java.util.ArrayList;

public class Jugador implements Comparable<Jugador>{

    
    //ATRIBUTOS DE CLASE
    protected static int CasasMax = 4;
    protected static int CasasPorHotel = 4;
    protected static int HotelesMax = 4;
    protected static float PasoPorSalida = 1000;
    protected static float PrecioLibertad = 200;
    private static float SaldoInicial = 7500;
    
    //ATRIBUTOS DE INSTANCIA
    protected boolean encarcelado;
    private boolean puedeComprar;
    private String nombre;
    private int numCasillaActual;
    private float saldo;
    
    //asociaciones
    private ArrayList<TituloPropiedad> propiedades;
    private Sorpresa salvoconducto;
    
    //paquete
    Jugador(String nombre){
        this.nombre = nombre;
        encarcelado = false;
        puedeComprar = false;
        numCasillaActual = 0;
        saldo = SaldoInicial;
        propiedades = new ArrayList<TituloPropiedad>();
        salvoconducto = null;
    }
    
    protected Jugador(Jugador otro)
    {
        this.encarcelado=otro.encarcelado;
        this.nombre=otro.nombre;
        this.numCasillaActual=otro.numCasillaActual;
        this.puedeComprar=otro.puedeComprar;
        this.saldo=otro.saldo;
        this.propiedades=otro.propiedades;
        this.salvoconducto=otro.salvoconducto;
    }
    
    boolean cancelarHipoteca(int ip)
    {   

        boolean result=false;
        if(!encarcelado)
        {
            if(existeLaPropiedad(ip))
            {
               TituloPropiedad propiedad=propiedades.get(ip);
               float cantidad=propiedad.getImporteCancelarHipoteca();
               boolean puedoGastar=puedoGastar(cantidad);
               if(puedoGastar)
               {
                   result=propiedad.cancelarHipoteca(this);
                   
                   if(result)
                   {
                       Diario.getInstance().ocurreEvento("El jugador"+nombre+"cancela la hipoteca de la propiedad"+ip);
                       
                   }
                   
               }
            }
        }
        
        return result;
    }
    
    
    int cantidadCasasHoteles()
    {
        int resultado=0;
        for(int i=0;i<propiedades.size();i++)
        {
            resultado=propiedades.get(i).cantidadCasasHoteles();
        }
        
        return resultado;
        
         
    }
    
    boolean comprar(TituloPropiedad titulo){
        boolean result=false;
            if(!encarcelado)
            {
                if(puedeComprar)
                {
                    float precio=titulo.getPrecioCompra();
                    if(puedoGastar(precio))
                    {
                        result=titulo.comprar(this);
                        if(result)
                        {
                            propiedades.add(titulo);
                            Diario.getInstance().ocurreEvento("El jugador"+this+ " compra la propiedad "+titulo.toString());
                        }
                        puedeComprar=false;
                    }
                }
            }
        return result;
    }
    
    boolean construirCasa(int ip){
        boolean result=false;
            
            if(encarcelado)
            {
                return result;
            }
            else
            {
                boolean puedoEdificarCasa=false;
                boolean existe=existeLaPropiedad(ip);
                if(existe)
                {
                    TituloPropiedad propiedad=propiedades.get(ip);
                    puedoEdificarCasa=puedoEdificarCasa(propiedad);
                    float precio=propiedad.getPrecioEdificar();
                    if(puedoGastar(precio) && puedoEdificarCasa)
                    {
                        result=propiedad.construirCasa(this);
                        Diario.getInstance().ocurreEvento("El jugador"+nombre+" construye casa en la propiedad"+ip);
                    }
                }
            }
            
        return result;
    }
    
    boolean construirHotel(int ip){
        
        boolean result=false;
        if(!encarcelado)
        {
            if(existeLaPropiedad(ip))
            {
                TituloPropiedad propiedad=propiedades.get(ip);
                boolean puedoEdificarHotel=puedoEdificarHotel(propiedad);
                puedoEdificarHotel=false;
                float precio=propiedad.getPrecioEdificar();
                
                if(puedoGastar(precio) && puedoEdificarHotel && propiedad.getNumCasas()==getCasasPorHotel())
                {
                       puedoEdificarHotel=true;
                }
                
                if(puedoEdificarHotel)
                {
                    result=propiedad.construirHotel(this);
                    int casasPorHotel=getCasasPorHotel();
                    propiedad.derruirCasas(casasPorHotel,this);
                    Diario.getInstance().ocurreEvento("El jugador "+nombre+ " construye hotel en la propiedad "+ip);
                }
            }
        }
        
        return result;
    }
    
    Boolean enBancarrota()
    {
        Boolean resultado=false;
        if(saldo<0)
        {
            resultado=true;
        }
        return resultado;
    }
    
    boolean encarcelar(int numCasillaCarcel){
        
        if(debeSerEncarcelado()){
            
            moverACasilla(numCasillaCarcel);
            encarcelado = true;
            Diario.getInstance().ocurreEvento("Se ha encarcelado al jugador "+nombre);
            
        }
        
        return encarcelado;
    }
    
    int getCasasPorHotel(){
        return CasasPorHotel;
    }
    
    int getNumCasillaActual(){
        return numCasillaActual;
    }
    
    String getNombre(){
        return nombre;
    }
    
    boolean getPuedeComprar(){
        return puedeComprar;
    }
    
    boolean hipotecar(int ip){
        boolean result=false;
        if(!encarcelado)
        {
            if(existeLaPropiedad(ip))
            {
                TituloPropiedad propiedad=propiedades.get(ip);
                result=propiedad.hipotecar(this);

            }
            if(result)
            {
                Diario.getInstance().ocurreEvento("El jugador "+nombre+ " hipoteca la propiedad "+ip);
            }
        }
        return result;
    }
    
    boolean modificarSaldo(float cantidad){
        
        saldo += cantidad;
        Diario.getInstance().ocurreEvento("Saldo de "+nombre+" incrementado(+"+cantidad+")");
        return true;
    }
    
    boolean moverACasilla(int numCasilla){
        boolean resultado = false;
        if(!encarcelado){
            numCasillaActual = numCasilla;
            puedeComprar = false;
            Diario.getInstance().ocurreEvento(nombre+" se ha movido a la casilla "+numCasillaActual);
            resultado = true;
        }
        
        return resultado;
    }
    
    boolean obtenerSalvoConducto(Sorpresa sorpresa){
        boolean seObtieneSalvoConducto = encarcelado;
        
        if(!seObtieneSalvoConducto){
            salvoconducto = sorpresa;
            
        }
        
        return seObtieneSalvoConducto;
        
    }
    
    boolean paga(float cantidad){
        
        boolean resultado = modificarSaldo(cantidad*-1);
        
        return resultado;
    }
    
    boolean pagaAlquiler(float cantidad){
        boolean resultado = false;
        
        if(!encarcelado)
            resultado = paga(cantidad);
        
        return resultado;
    }
    
    boolean pagaImpuesto(float cantidad){
        boolean resultado = false;
        
        if(!encarcelado)
            resultado = paga(cantidad);
        
        return resultado;
        
        
    }
    
    boolean pasaPorSalida(){
        
        modificarSaldo(PasoPorSalida);
        Diario.getInstance().ocurreEvento(nombre+" ha pasado por la salida");
        return true;
    }
    
            
    boolean puedeComprarCasilla(){
       
        puedeComprar = !encarcelado;
        
        return puedeComprar;
    }   
    
    boolean recibe(float cantidad){
        
        boolean resultado = false;
        
        if(!encarcelado)
            resultado = modificarSaldo(cantidad);
        
        return resultado;
        
    }
    
    boolean salirCarcelPagando(){
        boolean resultado = false;
        
        if(puedeSalirCarcelPagando()){
            paga(PrecioLibertad);
            encarcelado=false;
            Diario.getInstance().ocurreEvento(nombre+" ha sido puesto en libertad");
            resultado = true;
        }
        
        return resultado;
    }
    
    boolean salirCarcelTirando(){
        
        boolean resultado = Dado.getInstance().salgoDeLaCarcel();
        
        if(resultado){
            encarcelado = false;
            Diario.getInstance().ocurreEvento(nombre+" ha salido de la cárcel");
            resultado = true;
        }
        
        return resultado;
    }
    
    boolean tieneAlgoQueGestionar()
    {    
        return !propiedades.isEmpty();
    }
    
    Boolean tieneSalvoconducto()
    {
        Boolean resultado=false;
        if(salvoconducto!=null)
        {
            resultado=true;
        }
        
        return resultado;
    }
    
    boolean vender(int ip){
        
        boolean resultado = false;
        
        if(!encarcelado){
            
            if(existeLaPropiedad(ip)){
               resultado = propiedades.get(ip).vender(this);
            }
            
            if(resultado){
                Diario.getInstance().ocurreEvento(nombre+" vende la propiedad "+propiedades.get(ip).getNombre());
                
                propiedades.remove(ip);
            }
            
        }
        
        return resultado;
    }
    
    //publicos
    @Override
    public int compareTo(Jugador otro){

        /*
        if a < b then return -1
            if a = b then return  0
            if a > b then return  1
            if a and b no son comparables return null

        */
        int resultado = Float.compare(saldo, otro.saldo);

        return resultado;
    }
    
    public boolean isEncarcelado(){
        
        return encarcelado;
    }
    
    @Override
    public String toString(){
                
        String datos="Nombre:"+nombre+"\nSaldo: "+saldo+"\nEncarcelado:"+encarcelado+
                "\nCasilla actual:"+numCasillaActual+
                "\nPuede comprar: "+puedeComprar+
                "\nSalvoconducto: "+tieneSalvoconducto();
        
        return datos;
    }
    
    
    //privados
    
    Boolean existeLaPropiedad(int ip)
    {
        Boolean resultado=false;
        if(!propiedades.isEmpty())
        {
          if(propiedades.get(ip).getPropietario()==this)
            {
                resultado=true;
            }
        }

        return resultado;
    }
    
    
 private int getCasasMax()
    {
        return CasasMax;
    }
    
    int getHotelesMax()
    {
        return HotelesMax;
    }

    
    private float getPrecioLibertad()
    {
        return PrecioLibertad;
    }
    
    private float getPremioPasoSalida()
    {
       return PasoPorSalida; 
    }
    
    public ArrayList<TituloPropiedad> getPropiedades()
    {
        return propiedades;
    }
    
    public int getSizePropiedades()
    {
        return propiedades.size();
    }
    
    private void perderSalvoConducto(){
        
        salvoconducto.usada();
        salvoconducto = null;
        
    }
    
    private boolean puedeSalirCarcelPagando(){
        return (saldo >= PrecioLibertad);  
    }
    
    private Boolean puedoEdificarCasa(TituloPropiedad propiedad)
    {
        Boolean resultado=false;
        if(propiedad.getNumCasas()<CasasMax)
        {
            resultado=true;
        }
        
        return resultado;       
        
    }
    
    private Boolean puedoEdificarHotel(TituloPropiedad propiedad)
    {
        Boolean resultado=false;
        if(propiedad.getNumHoteles()<HotelesMax)
        {
            resultado=true;
        }
        
        return resultado;    
    }
    
    private boolean puedoGastar(float precio){
        
        boolean resultado = false;
        
        if(!encarcelado){
            resultado = (saldo>=precio);
        }
        
        return resultado;
    }
        
    //protected    
        protected float getSaldo()
    {
        return saldo;
    }
    
    protected boolean debeSerEncarcelado(){
        
        boolean seEncarcela = false;
        
        if(!encarcelado){
           if(tieneSalvoconducto()){
               perderSalvoConducto();
               Diario.getInstance().ocurreEvento("El jugador "+nombre+" se ha librado de la cárcel.");
           }else
               seEncarcela = true;
        }
            
        return seEncarcela;
    }
    public static float getSaldoInicial() {
        return SaldoInicial;
    }


    public Sorpresa getSalvoconducto() {
        return salvoconducto;
    }
    
    public static void main(String [] args){
        
        Jugador j1=new Jugador("Roberto");
        Jugador j2=new Jugador("Andres");
                j1.compareTo(j2);
        
        System.out.print(j1.toString());
        
        //j1.cantidadCasasHoteles();
        j1.debeSerEncarcelado();
        j1.enBancarrota();
        //j1.existeLaPropiedad(2);
        j1.getSaldo();
        j1.recibe(SaldoInicial);
        j1.puedoGastar(328943953);
    }

}
