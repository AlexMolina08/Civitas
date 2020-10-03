require_relative 'dado'
require_relative 'tablero'
require_relative 'sorpresa'
require_relative 'tipo_sorpresa'
require_relative 'civitas_juego'


#PREGUNTA 3 : La línea discontinua indica dependencia , un caso de ejemplo es 
#si un método de la clase Examen utiliza una referencia a una instancia de civitas_juego

module Test
  
    class Examen


      #Método de clase
      def self.principal
        resultado = 0
        tablero = Civitas::Tablero.new(10)
        
        tipos = [Civitas::TipoSorpresa::PORJUGADOR,Civitas::TipoSorpresa::PAGARCOBRAR,Civitas::TipoSorpresa::PORCASAHOTEL]
        
        mazo = Civitas::MazoSorpresas.new
        
        #Tiramos el dado 10 veces
         i = 0
        while i < 10
          resultado = Civitas::Dado.instance.tirar
          i = i+1
          mazo.alMazo(Civitas::Sorpresa.new_por_tablero(tipos[resultado%3], tablero))
        end
        
        puts mazo.to_string
        
      end
      
      def self.hazeHotel(juego) #<= Atributo de tipo CivitasJuego
        
        juego.construir_hotel
        
      end
      
      
      
      

  end
end

Test::Examen.principal #cuando se ejecute el archivo , se ejecutará siempre
                      #este método de clase