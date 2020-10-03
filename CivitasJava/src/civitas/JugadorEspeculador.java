/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

/**
 *
 * @author alex
 */
public class JugadorEspeculador extends Jugador{
    

    private static final int FactorEspeculador = 2;
    private float fianza;

    
    JugadorEspeculador(Jugador otro, float fianza){
        super(otro);
        this.fianza = fianza;
    }
    //------------------------------------
    public static int getFactorEspeculador(){
        return FactorEspeculador;
    }
    
    public float getFianza(){
        return fianza;
    }
    
    
    protected static int getHotelesMax(){
        return Jugador.getHotelesMax()*FactorEspeculador;
    }
    
    
    protected static int getCasasMax(){
        return Jugador.getCasasMax()*FactorEspeculador;
    }

    @Override
    boolean pagaImpuesto(float cantidad){
        float cantidadEspeculador = cantidad/FactorEspeculador;
        return super.pagaImpuesto(cantidadEspeculador);
    }
    
    @Override
    void atracame(Jugador atracador){
        
        if(!haRobado){
            if(tieneAlgoQueGestionar()){

                int indice_actual = 0;
                int indice_propiedad_barata = indice_actual;
                TituloPropiedad propiedadRobada;

                //Recorremos el Array buscando el indice de la propiedad más barata            
                for(TituloPropiedad propiedad : propiedades){
                    if(propiedad.getPrecioCompra() < propiedades.get(indice_propiedad_barata).getPrecioCompra())
                        indice_propiedad_barata = indice_actual;

                    indice_actual++;
                }

                propiedadRobada = propiedades.get(indice_propiedad_barata);
                propiedades.remove(indice_propiedad_barata);
                atracador.obtiene_propiedad(propiedadRobada);

            }
        }
        
    }
    
    
    @Override
    public String toString(){
        String representacion,tipo;
        
        tipo = "\t- JUGADOR ESPECULADOR\n";
        representacion = super.toString() + tipo;
        
        return representacion;
    }
    
    @Override
    protected boolean debeSerEncarcelado(){
        boolean debe = false;

        debe = super.debeSerEncarcelado();
        if (debe){      //No tiene salvoconducto
            if (fianza <= getSaldo()){      //Pago fianza para no ir
                debe = false;
                modificarSaldo(-fianza);
                
                //Informar a diario
                String evento = "El jugador " + getNombre() 
                             + " se libra de la cárcel mediante fianza";
                Diario.getInstance().ocurreEvento(evento);
            }
                
        }

        return debe;
    }

}