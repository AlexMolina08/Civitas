/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

/**
 *
 * @author ravolk
 */
import java.util.ArrayList;
public class Tablero {

   private final int numCasillaCarcel;
   private final ArrayList<Casilla> casillas;
   private int porSalida;
   private boolean tieneJuez;

    public Tablero(int param)
    {
        if(param>=1)
        {numCasillaCarcel=param;}
        else
        {
            numCasillaCarcel=1;}

        casillas=new ArrayList<Casilla>();

        casillas.add(new Casilla("Salida"));
        porSalida=0;
        tieneJuez=false;
    }

    private Boolean correcto()
    {
        return (casillas.size()>numCasillaCarcel && tieneJuez);
    }

    private Boolean correcto(int numCasilla)
    {
        return correcto() && numCasilla<=casillas.size();
    }

    int getCarcel()
    {
        return numCasillaCarcel;
    }

    int getPorSalida()
    {   int sal=0;
        if(porSalida>0)
        {
            sal=porSalida;
            porSalida--;
        }

        return sal;
    }

    void añadeCasilla(Casilla casilla)
    {
        if(casillas.size()== numCasillaCarcel)
        {
            casillas.add(new Casilla("Cárcel"));
        }
            casillas.add(casilla);
        
        if(casillas.size()==numCasillaCarcel)
        {
            casillas.add(new Casilla("Cárcel"));
        }
    }

    void añadeJuez()
    {
        if(!tieneJuez)
        {
            Casilla juez=new Casilla(numCasillaCarcel,"JUEZ");
            casillas.add(juez);
            tieneJuez=true;
        }
    }
    
    Casilla getCasilla(int numCasilla)
    {
        if(correcto(numCasilla))
        {
            return casillas.get(numCasilla);
        }
        else
            return null;
    }
    
    int nuevaPosicion(int actual,int tirada)
    {
        if(correcto())
        {
            porSalida+=(actual+tirada)/casillas.size();
            return (actual+tirada)%casillas.size();  
        }
        else
            return -1;
    }
    
    int CalcularTirada(int origen,int destino)
    {
        int resultado;
        if((destino-origen)<=0)
            resultado=destino-origen+casillas.size();
        else
            resultado=destino-origen;
        
        return resultado;
    }
    
    public int size()
    {
        return casillas.size();
    }
            
    
    
}

