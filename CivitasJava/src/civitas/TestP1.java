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

public class TestP1 {
    
    
    static void main()
            {
               
               for(int i=0;i<100;i++)
               {
                 System.out.println(Dado.getInstance().quienEmpieza(4));
               }
               
              Dado.getInstance().setDebug(true);
              System.out.println(Dado.getInstance().tirar());
              System.out.println(Dado.getInstance().tirar());
              System.out.println(Dado.getInstance().tirar());
        Dado.getInstance().setDebug(false);
                             System.out.println(Dado.getInstance().tirar());
                             System.out.println(Dado.getInstance().tirar());
        Dado.getInstance().getUltimoResultado();
        Dado.getInstance().salgoDeLaCarcel();
               
               MazoSorpresas mazi=new MazoSorpresas();
/*               
               Sorpresa s1=new Sorpresa("s1");
               Sorpresa s2=new Sorpresa("s2");
               Sorpresa s3=new Sorpresa("s3");
               
               mazi.alMazo(s1);
               mazi.alMazo(s2);
               mazi.alMazo(s3);
              
               mazi.siguiente();
               
               
               mazi.inhabilitarCartaEspecial(s3);*/
                              
               
               System.out.println(Diario.getInstance().leerEvento());
               
               Casilla c1=new Casilla("Madrid");
               Casilla c2=new Casilla("Granada");
               Tablero T1=new Tablero(3);
               T1.añadeCasilla(c1);
               T1.añadeJuez();
               T1.añadeCasilla(c2);
               
               
            }
}

