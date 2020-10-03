package civitas;
import java.util.ArrayList;
import java.util.Collections; //Para barajar el mazo

public class MazoSorpresas{

     private Sorpresa ultimaSorpresa; 
     private boolean barajada;
     private int usadas;
     private boolean debug;
     
     //asociaciones
     private ArrayList<Sorpresa> sorpresas; //0..*
     private ArrayList<Sorpresa> cartasEspeciales; //Almacenará la carta sorpresa SALIRCARCEL 1..*
    
     
    
     
    //paquete
    MazoSorpresas(){
         debug = false;
         this.init();     
    }
     
    MazoSorpresas(boolean debug){
         debug = debug;
         
         if(debug == true)
             Diario.getInstance().ocurreEvento("Se ha creado un mazo sorpresas en modo depuración");
         
         this.init();   
    }
     
    void alMazo(Sorpresa s){
         if(!barajada)
             sorpresas.add(s);
    }
     
    Sorpresa getUltimaSorpresa(){
         return sorpresas.get(sorpresas.size() - 1);
    }
     
    void habilitarCartaEspecial(Sorpresa especial){
        boolean resultado = cartasEspeciales.remove(especial);
        if(resultado){
            sorpresas.add(especial);
            Diario.getInstance().ocurreEvento("Se ha habilitado una carta especial");
        }
              
    }
     
    void inhabilitarCartaEspecial(Sorpresa especial){
        boolean resultado = sorpresas.remove(especial);
        if(resultado){
            cartasEspeciales.add(especial);
            Diario.getInstance().ocurreEvento("Se ha inhabilitado una carta especial");
        }     
    }
     
    Sorpresa siguiente(){
         
        if(!barajada  || usadas == sorpresas.size()){      
             if(!debug){   
                 Collections.shuffle(sorpresas);
                 barajada = true;
                 usadas = 0;  
             }
             
             usadas ++;
             //Quito la primera carta y la guardo en el atributo ultimaSorpresa
             //el método de instancia remove devuelve el elemento eliminado
             ultimaSorpresa = sorpresas.get(0);
             sorpresas.remove(0);
             //Pongo la sorpresa eliminada de la primera posicion en la última
             sorpresas.add(ultimaSorpresa);
         }
         
         return ultimaSorpresa;
    }
         
    
    void muestraSorpresas(){
        for(Sorpresa sorpresa:sorpresas)
            System.out.println(sorpresa.toString() +  " ");
    }
    
    //private
    private void init(){
         //Inicializamos las listas a un contenedor vacío , con capacidad inicial de 10
         sorpresas = new ArrayList(); 
         cartasEspeciales = new ArrayList();
         
         barajada = false;
         usadas = 0;
    }

}