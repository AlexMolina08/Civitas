/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;

/**
 *
 * @author jesus
 */
public class testP4 {
    
    public static void main (String args[]){
        
        //Declaracion jugador y titulo de propiedad
        TituloPropiedad newYork = new TituloPropiedad("new york",
                                        25.0f,50.0f,1000.0f,2000.0f,500.0f);
        
        Jugador j1 = new Jugador("Alex");

        
        
        //Comprar casilla
        j1.puedeComprarCasilla();
        j1.comprar(newYork);
        newYork.actualizarPropietarioPorConversion(j1);
        
        //Mostrar que la transacción ha sido un éxito
        System.out.println(j1.toString());
        System.out.println(newYork.toString());
        j1.pagaImpuesto(200);
        
        //Cambiar a jugador especulador
        SorpresaEspeculador convertir = new SorpresaEspeculador(150);
        ArrayList<Jugador> todos = new ArrayList<>();
        todos.add(j1);
        convertir.aplicarAJugador(0, todos);
        
        //Muestro el propietario
        System.out.println(newYork.getPropietario().toString());
        
        todos.get(0).pagaImpuesto(200);
        
        todos.get(0).encarcelar(0);
        
        
        //Leer diario
        while(Diario.getInstance().eventosPendientes())
            System.out.println("\t-" + Diario.getInstance().leerEvento() );
        
    }
}

