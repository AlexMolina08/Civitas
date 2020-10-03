
package juegoTexto;
import civitas.CivitasJuego;
import civitas.OperacionesJuego;
import civitas.Respuestas;
import civitas.OperacionInmobiliaria;
import civitas.GestionesInmobiliarias;
import civitas.SalidasCarcel;


public class Controlador 
{
    private CivitasJuego juegoModel ;
    private VistaTextual vista;
    
    public Controlador(CivitasJuego juegoModel,VistaTextual vista)
    {
        this.juegoModel=juegoModel;
        this.vista=vista;
        
    }
    
    public void juega()
    {
        vista.setCivitasJuego(juegoModel);
        
        while(!juegoModel.finalDelJuego())
        {
           vista.actualizarVista();
           vista.pausa();


           OperacionesJuego operacion=juegoModel.siguientePaso();
           vista.mostrarSiguienteOperacion(operacion);
           vista.mostrarEstado();
           if(operacion!=OperacionesJuego.PASAR_TURNO)
           {
               vista.mostrarEventos();
           }
           if(!juegoModel.finalDelJuego())
           {
               switch(operacion)
               {
                   case COMPRAR:
                       if(vista.comprar()==Respuestas.SI)
                       {
                           juegoModel.comprar();
                       }
                       break;
                   case GESTIONAR:
                       boolean salir=false;
                       do{                       
                       vista.gestionar();
                       int gestion=vista.getGestion();
                       int propiedad=vista.getPropiedad();
                       OperacionInmobiliaria opInm=new OperacionInmobiliaria(GestionesInmobiliarias.values()[gestion],propiedad);
                       switch(opInm.getGestionInmobiliaria())
                       {
                           case VENDER:
                               juegoModel.vender(opInm.getIndice());
                               break;
                           case HIPOTECAR:
                               juegoModel.hipotecar(opInm.getIndice());
                               break;
                           case CANCELAR_HIPOTECA:
                               juegoModel.cancelarHipoteca(opInm.getIndice());
                               break;
                           case CONSTRUIR_CASA:
                               juegoModel.construirCasa(opInm.getIndice());
                               break;
                           case CONSTRUIR_HOTEL:
                               juegoModel.construirHotel(opInm.getIndice());
                               break;
                           case TERMINAR:
                               salir=true;                              
                              juegoModel.siguientePaso();                          
                       }
                       }
                        while(!salir);
                        break;
                   case SALIR_CARCEL:
                        if(vista.salirCarcel()==SalidasCarcel.PAGANDO) 
                        {
                            juegoModel.salirCarcelPagando();
                        }
                        else
                        {
                            juegoModel.salirCarcelTirando();
                        }
                        juegoModel.siguientePaso();
                       
               }
                       
                      
           }
        }

    }
    
    
    
}
