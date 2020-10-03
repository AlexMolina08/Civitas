require_relative 'tipo_sorpresa'

require_relative 'diario'
require_relative 'mazo_sorpresas'
require_relative 'tablero'
require_relative 'jugador'
require_relative 'casilla'

module Civitas
  
  class Sorpresa
    attr_reader :nombre
    
    
    def initialize(texto,valor,mazo,tablero,tipo)
      
      @texto = texto
      @valor = valor
      @mazo = mazo
      @tipo = tipo
      @tablero = tablero
      
      
    end
    
    def self.new_por_tablero(tipo , tablero)
      new("",-1,nil,tablero,tipo)
    end
    
    def self.new_por_tablero_valor(tipo , tablero , valor , texto)
      new(texto,valor,nil,tablero,tipo)
    end
    
    def self.new_por_valor(tipo , valor , texto)
      new(texto,valor,nil,nil,tipo)
    end
    
    def self.new_por_mazo(tipo , mazo)
      new("",-1,mazo,nil,tipo)
    end
    
    def jugador_correcto(actual,todos)
     resultado = false
     if actual < todos.length and actual >= 0
       resultado = true
     end
     resultado  
    end
    
    def aplicar_a_jugador(actual , todos)
      case @tipo
      when TipoSorpresa::IRCARCEL
          aplicar_a_jugador_ir_carcel(actual,todos);
      when TipoSorpresa::IRCASILLA
         aplicar_a_jugador_ir_a_casilla(actual, todos);
      when TipoSorpresa::SALIRCARCEL
          aplicar_a_jugador_salir_carcel(actual, todos);
      when TipoSorpresa::PAGARCOBRAR
          aplicar_a_jugador_pagar_cobrar(actual, todos);
      when TipoSorpresa::PORCASAHOTEL
          aplicar_a_jugador_por_casa_hotel(actual, todos);
      when TipoSorpresa::PORJUGADOR
          aplicar_a_jugador_por_jugador(actual,todos)
        
      end
    end
    
    def salir_del_mazo()
      
      sorpresa = Sorpresa.new_por_tablero(TipoSorpresa::SALIRCARCEL , @tablero)
      
      if(@tipo == TipoSorpresa::SALIRCARCEL)
        @mazo.inhabilitarCartaEspecial(sorpresa)
      end
    end
    
    
    def to_string
      
      "Sorpresa de tipo: "+@tipo.to_s+"\n"
      
    end
    
    def usada
      sorpresa = Sorpresa.new_por_tablero(TipoSorpresa::SALIRCARCEL,@tablero)
      
      if @tipo == TipoSorpresa::SALIRCARCEL
        @mazo.inhabilitarCartaEspecial(sorpresa)
      end
    end
    
    private
    
    def informe(actual , todos)
      Diario.instance.ocurre_evento("Se ha aplicado una sorpresa a "+todos[actual].nombre)
    end
    
    
    def aplicar_a_jugador_ir_carcel(actual,todos)
      
      if(jugador_correcto(actual,todos))
        informe(actual,todos)
        todos[actual].encarcelar(@tablero.carcel);
      end
      
    end
    
    def aplicar_a_jugador_ir_a_casilla(actual, todos)
      
      if(jugador_correcto(actual,todos))
        informe(actual,todos)
        aux = todos[actual].num_casilla_actual
        todos[actual].mover_a_casilla(@tablero.nueva_posicion(aux, @tablero.calcularTirada(aux,@valor)))
        @tablero.casilla(@tablero.nueva_posicion(aux,@tablero.calcularTirada(aux, @valor))).recibe_jugador(actual, todos)
      end
    end
      
    end

    def aplicar_a_jugador_salir_carcel(actual, todos)
      jugador = todos[actual]  

      es_correcto = jugador_correcto(actual,todos)
      
      if(es_correcto)
        
        hay_salvoconducto=false
        informe(actual,todos)
        
        for jug in todos
          
          if jug.tiene_salvoconducto
            hay_salvoconducto=true
          end
          
        end
        
        unless(hay_salvoconducto)
          @jugador.obtener_salvoconducto(self)
          salir_del_mazo()
        end
      end
    end
    
    def aplicar_a_jugador_pagar_cobrar(actual, todos)
      jugador = todos[actual]
      es_correcto = jugador_correcto(actual,todos)
      if(es_correcto)
        informe(actual,todos)
        jugador.modificar_saldo(@valor)
      end
    end
    
    def  aplicar_a_jugador_por_casa_hotel(actual, todos)
      
        jugador = todos[actual];
        es_correcto = jugador_correcto(actual, todos);
        
        if es_correcto 
            informe(actual, todos);
            jugador.modificar_saldo(@valor * jugador.cantidad_casas_hoteles());
        end
      
    end
    
    
    def aplicar_a_jugador_por_jugador(actual,todos)
      
      jugador = todos[actual]
      es_correcto = jugador_correcto(actual , todos)
      
      if es_correcto
        
        informe(actual , todos)
        pagar_cobrar1 = Sorpresa.new_por_valor(TipoSorpresa::PAGARCOBRAR , -1*@valor , "cobra")
        pagar_cobrar2 = Sorpresa.new_por_valor(TipoSorpresa::PAGARCOBRAR , (todos.length-1) * @valor , "cobra")
        
        i = 0
        for jug in todos
          
          if jug != jugador
            pagar_cobrar1.aplicar_a_jugador(i,todos)
          end
          
          i = i + 1
          
        end
        
        pagar_cobrar2.aplicar_a_jugador(actual,todos)
        
      end
      
    end
    
end


