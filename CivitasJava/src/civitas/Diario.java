package civitas;




import java.util.ArrayList;

public class Diario {
    
  private static final  Diario instance = new Diario();
  
  private ArrayList<String> eventos;
  
  public static Diario getInstance() {
    return instance;
  }
  
  private Diario () {
    eventos = new ArrayList<>();
  }
  
  void ocurreEvento (String e) {
    eventos.add (e);
  }
  
  public boolean eventosPendientes () {
    return !eventos.isEmpty();
  }
  
  public String leerEvento () {
    String salida = "";
    if (!eventos.isEmpty()) {
      salida = eventos.remove(0); //Esto almacena en salida el primer Elemento que está en la lista
    }
    return salida;
  }
  
  
}
