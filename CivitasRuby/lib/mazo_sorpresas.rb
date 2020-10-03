#encoding:utf-8
require_relative "sorpresa"
require_relative "diario"

module Civitas

  class MazoSorpresas
       
    attr_reader :sorpresas , :usadas , :ultima_sorpresa
    
    def initialize(dbug=false)
     @barajada = false
     @usadas = 0
     @sorpresas = Array.new
     @cartas_especiales = Array.new
     @debug = dbug
     @ultima_sorpresa = nil
     
     if(@debug)
       Diario.instance.ocurre_evento("Se ha creado un mazo en modo depuración")
     end
    end
    
    
    def to_string
      i = 0
      resultado = "Sorpresas:\n"
      for sorpresa in @sorpresas
        resultado = resultado + sorpresa.to_string
      end
      resultado
    end
   

    
    def init
      @sorpresas = Array.new
      @ultima_sorpresa = nil
      @cartasEspeciales = Array.new
      @usadas = 0
      @barajada = false
    end
    
    def alMazo(sorpresa)
      if(!@barajada)
        @sorpresas<<sorpresa
      end
    end
    
    def siguiente
        
      if(!@barajada or @sorpresas.length == @usadas)
          if(!@debug)
            @sorpresas.shuffle!
          end
          
          @usadas = 0
          @barajada = true
        end
        
        @usadas += 1
        
        primera = @sorpresas.shift
        @sorpresas.push(primera)  
        @ultima_sorpresa = primera
        
        
        @ultima_sorpresa
    end
    
    def inhabilitarCartaEspecial(sorpresa)
      
      for i in (0..@sorpresas.length - 1)
        if @sorpresas[i] == sorpresa
          aux = @sorpresas[i]
          @sorpresas.delete_at(i)
          @cartas_especiales << aux
          Diario.instance.ocurre_evento("Se ha movido una carta de sorpresa a Especial")
        end
      end
      
    end
    
    def habilitarCartaEspecial(sorpresa)
    
      for i in (0..@cartas_especiales.length-1)
        if @catas_especiales[i] == sorpresa
          aux = @cartas_especiales[i]
          @cartas_especiales.delete_at(i)
          @sorpresas<<aux
          Diario.instance.ocurre_evento("Se ha movido una carta de Especiales a Sorpresas")
        end
      end
    end
    
    def tam_sorpresas
      @sorpreas.length
    end

  end
  
end