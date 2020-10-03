#encoding:utf-8

require_relative 'jugador'
require_relative 'gestor_estados'
require_relative 'estados_juego'
require_relative 'dado'
require_relative 'sorpresa'
require_relative 'tablero'
require_relative 'titulo_propiedad'
require_relative 'diario'

module Civitas
  
  class CivitasJuego
    
    
    def initialize(nombres)
      @jugadores = Array.new
      for nombre in nombres
        jugador = Jugador.new_por_nombre(nombre)
        @jugadores << jugador
      end 
      
      @indice_jugador_actual = Dado.instance.quienEmpieza(@jugadores.length)
      @gestor_estados = Gestor_estados.new
      @estado = @gestor_estados.estado_inicial
      @tablero = Tablero.new(Casilla.Carcel)
      @mazo = MazoSorpresas.new
      
      inicializar_mazo_sorpresas(@tablero) 
      inicializar_tablero(@mazo)
     
      
      
    end
    
    
    def muestra_tablero
      @tablero.muestra_tablero
    end
    
    def cancelar_hipoteca(ip)
      
      @jugadores[@indice_jugador_actual].cancelar_hipoteca(ip)
      
    end
    
    
    
    def comprar
      
      res=false
      jugador_actual=@jugadores[@indice_jugador_actual]
      num_casilla_actual=jugador_actual.num_casilla_actual
      casilla=@tablero.casillas[num_casilla_actual]
      titulo=casilla.titulo
      res=jugador_actual.comprar(titulo)
      
      res
      
    end
    
    def construir_casa(ip)
      @jugadores[@indice_jugador_actual].construir_casa(ip)
    end
    
    def construir_hotel(ip)
      @jugadores[@indice_jugador_actual].construir_hotel(ip)
    end
    
    def actualizar_info
      
      @jugadores[@indice_jugador_actual].to_string
      
      
      
    end
    
    def final_del_juego
      algunArruinado = false;
      for jugador in @jugadores
        if(jugador.en_bancarrota)
          algunArruinado = true
        end
      end
      
      algunArruinado
      
    end
    
    def get_casilla_actual
      @tablero.casilla(@jugadores[@indice_jugador_actual].num_casilla_actual)
    end
    
    def get_jugador_actual
      @jugadores[@indice_jugador_actual]
    end
    
    def hipotecar(ip)
      get_jugador_actual.hipotecar(ip)
    end
    
    def info_jugador
      get_jugador_actual.to_string
    end
    
    def salir_carcel_pagando
      @jugadores[@indice_jugador_actual].salirCarcelPagando();
    end
    
    def salir_carcel_tirando
      @jugadores[@indice_jugador_actual].salirCarcelTirando();
    end
    
    def siguiente_paso
      jugador_actual = @jugadores[@indice_jugador_actual]
      operacion=@gestor_estados.operaciones_permitidas(jugador_actual, @estado)
      
      if(operacion==OperacionesJuego::PASAR_TURNO)
         pasar_turno
         siguiente_paso_completado(operacion)
         
       elsif(operacion==OperacionesJuego::AVANZAR)
           avanza_jugador
           siguiente_paso_completado(operacion)
         end
         operacion
      end
    
    
    def siguiente_paso_completado(operacion)
      
      @estado=@gestor_estados.siguiente_estado(get_jugador_actual, @estado, operacion)
      
    end
    
    def vender(ip)
      get_jugador_actual.vender(ip)
    end
    
    def avanza_jugador
      
      jugador_actual = @jugadores[@indice_jugador_actual]
      posicion_actual = jugador_actual.num_casilla_actual
      tirada = Dado.instance.tirar
      posicion_nueva = @tablero.nueva_posicion(posicion_actual,tirada)
      
      casilla = @tablero.casilla(posicion_nueva)
      
      contabilizar_pasos_por_salida(jugador_actual)
      
      jugador_actual.mover_a_casilla(posicion_nueva)
      casilla.recibe_jugador(@indice_jugador_actual,@jugadores)
      
      contabilizar_pasos_por_salida(jugador_actual)
       
    end
   

    private
    
    def contabilizar_pasos_por_salida(jugador_actual)

      pasos = @tablero.get_por_salida
      while pasos > 0
        
        jugador_actual.pasa_por_salida
        pasos = @tablero.get_por_salida
        
      end
      
    end
    
    def inicializar_mazo_sorpresas(tablero)

      @mazo.alMazo(Sorpresa.new_por_mazo(TipoSorpresa::IRCARCEL,tablero))
      @mazo.alMazo(Sorpresa.new_por_mazo(TipoSorpresa::IRCASILLA,tablero))
      @mazo.alMazo(Sorpresa.new_por_mazo(TipoSorpresa::PAGARCOBRAR,tablero))
      @mazo.alMazo(Sorpresa.new_por_mazo(TipoSorpresa::PORCASAHOTEL,tablero))
      @mazo.alMazo(Sorpresa.new_por_mazo(TipoSorpresa::PORJUGADOR,tablero))
      @mazo.alMazo(Sorpresa.new_por_mazo(TipoSorpresa::SALIRCARCEL,tablero))

    end
    
    def inicializar_tablero(mazo)
      casillas = ["Calle Mulillas","Calle Dtor Oloriz" , "Calle Froilán"]
      
      for casilla in casillas
        titulo = TituloPropiedad.new(casilla,200,1.25,500,24000,100)
        @tablero.añadeCasilla(Casilla.new_por_titulo(titulo))
      end
      
      
    end
    
    def pasar_turno
      if @indice_jugador_actual < @jugadores.length - 1
        @indice_jugador_actual += 1
      else
        @indice_jugador_actual = 0
      end
      
    end
    
    def ranking
      
      @jugadores.sort! { |a,b| a.saldo <=> b.saldo }
      
    end
    
  end
end