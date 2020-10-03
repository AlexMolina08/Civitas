package civitas;


public class TituloPropiedad {
    
    private static final float factorInteresesHipoteca = 1.1f;
    
    private float alquilerBase;
    private float factorRevalorizacion;
    private float hipotecaBase;
    private boolean hipotecado;
    private String nombre;
    private int numCasas;
    private int numHoteles;
    private float precioCompra;
    private float precioEdificar;
    
    //asociaciones
    Jugador propietario;
    
    
    //paquete
    
    TituloPropiedad(String nom , float ab , float fr , float hb , float pc , float pe){
      
        nombre = nom;
        alquilerBase = ab;
        factorRevalorizacion = fr;
        hipotecaBase = hb;
        precioCompra = pc;
        precioEdificar = pe;
        
        numCasas=0;
        numHoteles=0;
        hipotecado=false;
        
        propietario = null;
        
        
    }
    
    void actualizarPropietarioPorConversion(Jugador jugador){
        
        propietario=jugador;
    }
    
    boolean cancelarHipoteca(Jugador jugador){
        boolean result=false;
            if(getHipotecado())
            {
              if(esEsteElPropietario(jugador)) 
              {
                  propietario.paga(getImporteCancelarHipoteca());
                  hipotecado=false;
                  result=true;
              }
            }
          return result;
    }
    
    int cantidadCasasHoteles(){
        
        return numCasas+numHoteles;
    }
    
    boolean comprar(Jugador jugador){
        
        boolean result=false;
        if(!tienePropietario())
        {
            propietario=jugador;
            result=true;
            propietario.paga(getPrecioCompra());
        }
        return result;
    }
    
    boolean construirCasa(Jugador jugador){
        
        boolean seConstruye=false;
        if(esEsteElPropietario(jugador))
        {
            seConstruye=true;
            numCasas++;
        }
        return seConstruye;
    }
    
    boolean construirHotel(Jugador jugador){
        
        boolean result=false;
        if(esEsteElPropietario(jugador))
        {
            propietario.paga(precioEdificar);
            numHoteles++;
            result=true;
        }
                
          
        
        return result;
    }
    
    boolean derruirCasas(int n , Jugador jugador){
        boolean derruidas = false;
        if(esEsteElPropietario(jugador) && numCasas >= n){
            numCasas -= n;
            derruidas = true;
        }
        
        return derruidas;
    }
    
    float getImporteCancelarHipoteca(){
        
        float cantidadRecibida = hipotecaBase*(1+(numCasas*0.5f)+(numHoteles*2.5f));
        
        return cantidadRecibida * factorInteresesHipoteca;
    }
    
    public String getNombre(){
        
        return nombre;
    }
    
    int getNumCasas()
    {
        return numCasas;
    }
    
    int getNumHoteles()
    {
        return numHoteles;
    }
    
    float getPrecioCompra()
    {
        return precioCompra;
    }
    
    float getPrecioEdificar()
    {
        return precioEdificar;
    }
    
    Jugador getPropietario(){
        
        return propietario;
    }
    
    boolean hipotecar(Jugador jugador)
    {
        boolean salida=false;
        if(! hipotecado && esEsteElPropietario(jugador))
        {
            propietario.recibe(getImporteHipoteca());
            hipotecado=true;
            salida=true;
        }
        return salida;

    }
    
    boolean tienePropietario(){
        
        return propietario!=null;
    }
    
    void tramitarAlquiler(Jugador jugador){
        
        if(tienePropietario() && !esEsteElPropietario(jugador))
        {
            float alquiler = getPrecioAlquiler();
            jugador.pagaAlquiler(alquiler);
            propietario.recibe(alquiler);   
        }
        
    }
    
    Boolean vender(Jugador jugador)
    {
        Boolean resultado=false;
        if(this.esEsteElPropietario(jugador)&& !this.hipotecado)
        {
            jugador.recibe(this.getPrecioVenta());
            propietario=null;
            numCasas=0;
            numHoteles=0;
            resultado=true;
             
        }
        return resultado;
    }
    
    //public
    public boolean getHipotecado(){
        
        return true;
    }
    
    @Override
    public String toString(){
        String hip = (hipotecado) ? "si":"no";
        return "\n\t\t\tnombre: "+nombre+"\n\t\t\tAlquiler Base: "+alquilerBase
                +"\n\t\t\tfactor revalorizacion: "+factorRevalorizacion +
                "\n\t\t\thipoteca base: "+hipotecaBase+"\n\t\t\thipotecado: "+hip
                +"\n\t\t\tnºcasas: "+numCasas+"\n\t\t\tnºhoteles"+numHoteles+
                "\n\t\t\tprecio compra: "+precioCompra+"\n\t\t\tprecio edificar: "
                +precioEdificar;
        
    }
    
    //private
    
    private boolean esEsteElPropietario(Jugador jugador){
        
        return jugador==propietario;
    }
    
    private float getImporteHipoteca(){
        

        return hipotecaBase*(1+(numCasas*0.5f)+(numHoteles*2.5f));
    }
    
    private float getPrecioAlquiler(){
        float precioAlquiler;
        
        if(!propietarioEncarcelado() && !hipotecado)
            precioAlquiler = alquilerBase*(1+(numCasas*0.5f)+(numHoteles*2.5f));
        else
            precioAlquiler = 0;
        
        return precioAlquiler;
        
    }
    
    private float getPrecioVenta(){
        
        return precioCompra+((cantidadCasasHoteles() * precioEdificar) * factorRevalorizacion );
    }
    
    private boolean propietarioEncarcelado(){
        boolean estaEncarcelado = true;
        if(!propietario.encarcelado || propietario == null)
            estaEncarcelado = false;
        
        
        return estaEncarcelado;
    }
 
    
    public static void main(String[]args){
        
        TituloPropiedad titulo = new TituloPropiedad("unNombre",3000f,3f,5,6,7);
        
        System.out.println(titulo.toString());
        
    }
    
}
