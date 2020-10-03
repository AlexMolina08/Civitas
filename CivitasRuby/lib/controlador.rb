#encoding:utf-8

require_relative 'civitas_juego'
require_relative 'vista_textual'
require_relative 'operaciones_juego'
require_relative 'enumerados'
require_relative 'respuestas'
require_relative 'salidas_carcel'
require_relative 'operacion_inmobiliaria'

module Civitas
  
  class Controlador
    
    def initialize(juego , vista)
      @juego = juego
      @vista = vista
    end
    
    def juega
      
      @juego.muestra_tablero
      @vista.setCivitasJuego(@juego)
      
      
      #LOOP DEL JUEGO
      
      while (!@vista.modelo.final_del_juego)
        
        @vista.actualizarVista
        @vista.pausa
        
        siguiente = @vista.modelo.siguiente_paso
        
        @vista.mostrarSiguienteOperacion(siguiente)
        
        if(siguiente != OperacionesJuego::PASAR_TURNO)
          @vista.mostrarEventos
        end
        
        fin = @vista.modelo.final_del_juego
        
        if(fin == false)
          
          if(siguiente == OperacionesJuego::COMPRAR)
            respuesta = @vista.comprar
            
            if(respuesta == Respuestas::SI)
              @vista.modelo.comprar
            end
            
            @vista.modelo.siguiente_paso_completado(OperacionesJuego::COMPRAR)
            
          elsif siguiente == OperacionesJuego::GESTIONAR
            
            lista_gestiones_inmobiliarias = [OperacionesInmobiliarias::VENDER,
              OperacionesInmobiliarias::HIPOTECAR,OperacionesInmobiliarias::CANCELAR_HIPOTECA,
              OperacionesInmobiliarias::CONSTRUIR_CASA,OperacionesInmobiliarias::CONSTRUIR_HOTEL,
              OperacionesInmobiliarias::TERMINAR
            ]
            
            @vista.gestionar
            
            operacion = Operacion_Inmobiliaria.new(@vista.getGestion,@vista.getPropiedad)
            
            if(operacion.gestion == OperacionesInmobiliarias::CANCELAR_HIPOTECA)
              @vista.modelo.cancelar_hipoteca(operacion.num_propiedad)
            
            elsif(operacion.gestion == OperacionesInmobiliarias::CONSTRUIR_CASA)
              @vista.modelo.construir_casa(operacion.num_propiedad)
            
            elsif(operacion.gestion == OperacionesInmobiliarias::CONSTRUIR_HOTEL)
              
              @vista.modelo.construir_hotel(operacion.num_propiedad)
            
            elsif(operacion.gestion == OperacionesInmobiliarias::HIPOTECAR)
              @vista.modelo.hipotecar(operacion.num_propiedad)
            
            elsif(operacion.gestion == OperacionesInmobiliarias::VENDER)
              @vista.modelo.vender(operacion.num_propiedad)
            
            elsif(operacion.gestion == OperacionesInmobiliarias::TERMINAR)
              @vista.modelo.siguiente_paso_completado(OperacionesJuego::GESTIONAR)
            end
            
          elsif(siguiente == OperacionesJuego::SALIR_CARCEL)
            salida = @vista.salirCarcel
            
            if(salida == Salidas_Carcel::PAGANDO)
              @vista.modelo.salir_carcel_pagando
            
            else
              @vista.modelo.salir_carcel_tirando
            end
            
            @vista.modelo.siguiente_paso_completado(OperacionesJuego::SALIR_CARCEL)
          end
        end
      end
      
      @vista.modelo.ranking
      
    end
  end
end
