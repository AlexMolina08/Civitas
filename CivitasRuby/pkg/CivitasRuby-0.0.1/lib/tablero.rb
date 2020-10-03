#encoding:utf-8
module Civitas
  
  require_relative "casilla"
  
  class Tablero
    attr_reader :numCasillaCarcel , :porSalida , :casillas , :num_casillas
    
    
    @@num_casillas = 0
    
    def initialize(numCasillaCarcel)
      #atributos de instacia declarados aquí
      
      if numCasillaCarcel >= 1
        @numCasillaCarcel = numCasillaCarcel
      else
        @numCasillaCarcel = 1
      end
      
      @casillas = Array.new #creación Array vacío
      @porSalida = 0
      @tieneJuez = true
      
      añadeCasilla(Casilla.new_por_nombre("Salida"))
    end
    
    #cada vez que se llama a este método se supone que el jugador acaba de pasar
    #por la salida una vez
    def get_por_salida()
      aux = 0
      if @porSalida > 0 then 
        aux = @porSalida
        @porSalida -= 1
      end
      
      aux
      
    end
    
    def añadeCasilla(casilla)
      
      if (@casillas.size == @numCasillaCarcel)
        @casillas << Casilla.new_por_nombre("Carcel")
      end
      
      @casillas << casilla
      
      if (@casillas.size == @numCasillaCarcel)
        @casillas << Casilla.new_por_nombre("Cárcel")
      end
      
      @@num_casillas+=1
      
      @@num_casillas
      
    end
    
    #método auxiliar para saber cómo está configurado el tablero
    def muestra_tablero
      puts "TABLERO\n------------"
      for casilla in casillas
        puts "#{casilla.nombre}0"
      end
      puts "\n\n"
    end
    
    def añadeJuez()
      if(!@tieneJuez)
        añadeCasilla(Casilla.new_por_nombre("Juez"))
        @tieneJuez = true
      end
    end
    
    
    def get_casilla(num_casilla)
      @casillas[num_casilla]
      
    end
    
    def nueva_posicion(actual , tirada)
      unless(correct)
        new_pos = -1  
      else
        new_pos = (tirada + actual) 
        
        if(new_pos >= @casillas.size())

          new_pos %= @casillas.size()
          @porSalida  = @porSalida + 1

        end
      end
      
      new_pos
      
    end
    
    def calcularTirada(origen , destino) 
      distancia = 0
      if destino - origen >=0
        distancia = destino - origen
      else
        distancia = destino - origen + @casillas.length
      end
      distancia
      
    end
    
    def casilla(num_casilla)
      if correcto(num_casilla)
        return @casillas[num_casilla]
      else
        return nil
      end
    end
    
    private
    def correct()
      (@casillas.size > @numCasillaCarcel) and @tieneJuez
    end
    
    def correcto(numCasilla)
      
      correct && (@casillas.size > numCasilla)
      
    end

  end
  
end
