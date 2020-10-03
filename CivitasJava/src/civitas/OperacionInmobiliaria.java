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
public class OperacionInmobiliaria {
    GestionesInmobiliarias gestInm;
    int indice;
    
    OperacionInmobiliaria()
    {
        indice=0;
        gestInm=null;
    }
    
    public OperacionInmobiliaria(GestionesInmobiliarias gestInm,int indice)
    {
        this.gestInm=gestInm;
        this.indice=indice;
    }
    
    public GestionesInmobiliarias getGestionInmobiliaria()
    {
        return gestInm;
    }
    
    public int getIndice()
    {
        return indice;
    }
    
}
