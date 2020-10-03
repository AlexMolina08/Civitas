package juegoTexto;

import civitas.CivitasJuego;
import civitas.Diario;
import civitas.OperacionesJuego;
import civitas.SalidasCarcel;
import civitas.Respuestas;
import civitas.GestionesInmobiliarias;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import civitas.Casilla;
import civitas.CivitasJuego;
import civitas.Diario;
import civitas.Jugador;
import civitas.OperacionesJuego;
import civitas.Respuestas;
import civitas.SalidasCarcel;
import civitas.TituloPropiedad;

public class VistaTextual {
  
  CivitasJuego juegoModel; 
  int iGestion=-1;
  int iPropiedad=-1;
  private static String separador = "=====================";
  
  private Scanner in;
  
  public VistaTextual () {
    in = new Scanner (System.in);
  }
  
  void mostrarEstado() {
    System.out.println (juegoModel.getEstado());
  }
              
  void pausa() {
    System.out.print ("Pulsa una tecla");
    in.nextLine();
  }

  int leeEntero (int max, String msg1, String msg2) {
    Boolean ok;
    String cadena;
    int numero = -1;
    do {
      System.out.print (msg1);
      cadena = in.nextLine();
      try {  
        numero = Integer.parseInt(cadena);
        ok = true;
      } catch (NumberFormatException e) { // No se ha introducido un entero
        System.out.println (msg2);
        ok = false;  
      }
      if (ok && (numero < 0 || numero >= max)) {
        System.out.println (msg2);
        ok = false;
      }
    } while (!ok);

    return numero;
  }

  int menu (String titulo, ArrayList<String> lista) {
    String tab = "  ";
    int opcion;
    System.out.println (titulo);
    for (int i = 0; i < lista.size(); i++) {
      System.out.println (tab+i+"-"+lista.get(i));
    }

    opcion = leeEntero(lista.size(),
                          "\n"+tab+"Elige una opción: ",
                          tab+"Valor erróneo");
    return opcion;
  }

  SalidasCarcel salirCarcel() {
    int opcion = menu ("Elige la forma para intentar salir de la carcel",
      new ArrayList<> (Arrays.asList("Pagando","Tirando el dado")));
    return (SalidasCarcel.values()[opcion]);
  }

  Respuestas comprar() 
  {
      String titulo="¿Quiere comprar la calle?";
      ArrayList<String> lista=new ArrayList<String>();
      lista.add("SI");
      lista.add("NO");
      if(menu(titulo,lista)==0)//Si la opcion marcada es SI
      {
          return Respuestas.SI;
      }
      else
      {
          return Respuestas.NO;
      }
              
  }

  
  
  void gestionar () 
  {
      String titulo="\nGestión";
      ArrayList<String> lista;
      lista=new ArrayList<>();
      ArrayList<String> listaprop;
      listaprop = new ArrayList<>();
      lista.add("Vender");
      lista.add("Hipotecar");
      lista.add("Cancelar Hipoteca");      
      lista.add("Construir Casa");
      lista.add("Construir Hotel");
      lista.add("Terminar");   
      for(int i=0;i<juegoModel.getJugadorActual().getSizePropiedades();i++)
      {
          listaprop.add(juegoModel.getJugadorActual().getPropiedades().get(i).getNombre());
      }
      
      iGestion=menu(titulo,lista);
      if(!juegoModel.getJugadorActual().getPropiedades().isEmpty() && iGestion!=lista.indexOf("Terminar"))
      {
          iPropiedad=menu("Propiedad:",listaprop);
      }
      else if(iGestion!=lista.indexOf("Terminar"))
      {
          System.out.print("\nNo posees ninguna propiedad\n");
      }
         
  }
  
  
  
  public int getGestion()
  {
      return iGestion;
  }
  
  
  
  public int getPropiedad()
  {
      return iPropiedad;
  }
    

  
  void mostrarSiguienteOperacion(OperacionesJuego operacion) 
  {
      System.out.print(operacion);
  }
  
  
  
  void mostrarEventos()
  {
      if(Diario.getInstance().eventosPendientes())
      {
          System.out.print("\nDiario:");
          System.out.print(Diario.getInstance().leerEvento());
          System.out.print("\n");
      }
  }
 
  
  
  public void setCivitasJuego(CivitasJuego civitas)
    {
        
        juegoModel=civitas;
        actualizarVista();

    }
  
  
  
  
public void actualizarVista()
  {
      System.out.print("\n----ESTADO JUGADOR----\n");
      System.out.print(juegoModel.getJugadorActual().toString());
      System.out.print("\n----ESTADO CASILLA----\n");
      System.out.print(juegoModel.getCasillaActual().toString());
      System.out.print("\n----------------------\n");      
  } 
}
