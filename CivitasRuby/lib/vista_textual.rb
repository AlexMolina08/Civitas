#encoding:utf-8
require_relative 'operaciones_juego'
require_relative 'enumerados'
require_relative 'respuestas'
require_relative 'salidas_carcel'
require_relative 'diario'
require 'io/console'

module Civitas

  class Vista_textual
    
    attr_reader :modelo
    
    def initialize
      @iGestion = -1
      @iPropiedad = -1
    end

    def mostrar_estado(estado)
      puts estado
    end

    
    def pausa
      print "Pulsa una tecla"
      STDIN.getch
      print "\n"
    end

    def lee_entero(max,msg1,msg2)
      ok = false
      begin
        print msg1
        cadena = gets.chomp
        begin
          if (cadena =~ /\A\d+\Z/)
            numero = cadena.to_i
            ok = true
          else
            raise IOError
          end
        rescue IOError
          puts msg2
        end
        if (ok)
          if (numero >= max)
            ok = false
          end
        end
      end while (!ok)

      return numero
    end



    def menu(titulo,lista)
      tab = "  "
      puts titulo
      index = 0
      lista.each { |l|
        puts tab+index.to_s+"-"+l
        index += 1
      }

      opcion = lee_entero(lista.length,
                          "\n"+tab+"Elige una opción: ",
                          tab+"Valor erróneo")
                        
      return opcion
    end

    
    def comprar
      lista_Respuestas = [Respuestas::SI, Respuestas::NO]
      opcion = menu("¿Comprar calle?", ["SI", "NO"])
      return (lista_Respuestas[opcion])
      
    end

    def gestionar
    lista_gestiones_inmobiliarias = [OperacionesInmobiliarias::VENDER , 
    OperacionesInmobiliarias::HIPOTECAR, 
    OperacionesInmobiliarias::CANCELAR_HIPOTECA, 
    OperacionesInmobiliarias::CONSTRUIR_CASA, 
    OperacionesInmobiliarias::CONSTRUIR_HOTEL,
    OperacionesInmobiliarias::TERMINAR ]

      opcion = menu("¿Qué gestión inmobiliaria quiere realizar?", 
        ["Vender", "Hipotecar","Cancelar hipoteca", "Construir casa", "Construir hotel", "Terminar"])
      

      @iGestion = lista_gestiones_inmobiliarias[opcion]
      
      nombres_propiedades = Array.new
      
      for i in (0..@modelo.get_jugador_actual.propiedades.length - 1)
        p = @modelo.get_jugador_actual.propiedades
        puts p[i].nombre
        nombres_propiedades << @modelo.get_jugador_actual.propiedades.at(i).nombre
      end
      
      @iPropiedad = menu("¿En qué propiedad?" , nombres_propiedades)
    end

    def getGestion
      @iGestion
    end

    def getPropiedad
      @iPropiedad
    end

    def mostrarSiguienteOperacion(operacion)
    
      case operacion
      when  OperacionesJuego::AVANZAR
         puts "---La siguiente operación es avanzar---"
      when  OperacionesJuego::COMPRAR
         puts "---La siguiente operación es comprar---"
      when OperacionesJuego::GESTIONAR
         puts "---La siguiente operación es gestionar---"
      when  OperacionesJuego::PASAR_TURNO
         puts "---La siguiente operación es pasar turno---"
      else
         puts "---La siguiente operación es salir de la cárcel---"
        
      end
    end

    def mostrarEventos
      
      
      while(Diario.instance.eventos_pendientes)
        puts Diario.instance.leer_evento
      end
      
    end

    def setCivitasJuego(civitas)
         @modelo=civitas
         actualizarVista
    end

    def actualizarVista
      system("clear")
      puts "***INFO_JUGADOR***"
      puts @modelo.get_jugador_actual.to_string
      puts "***INFO_CASILLA***"
      puts @modelo.get_casilla_actual.to_string
    end

    
    def salirCarcel
      lista_Salidas_carcel = [Salidas_carcel::PAGANDO, Salidas_carcel::TIRANDO]
      opcion = menu("Elige la forma para intentar salir de la cárcel", ["Pagando", "Tirando el dado"])
      return (lista_Salidas_carcel[opcion])
    end
    
  end

end
