#encoding:utf-8
require_relative 'dado'
require_relative 'civitas_juego'
require_relative 'controlador'
require_relative 'vista_textual'

module Civitas

  class Demo
    
    @@JUGADORES = Array.new
    
    #inicializa los nombres de los jugadores
    def self.inicializa_jugadores
      print "Nº De jugadores: "
      tam = gets.to_i
      
      for i in 0..tam-1
        print "nombre del jugador #{i+1}: "
        nombre = gets
        @@JUGADORES << nombre
      end 
    end
    
    
    def self.main
      
      
      vista = Vista_textual.new      
      inicializa_jugadores
      juego = CivitasJuego.new(@@JUGADORES)
      Dado.instance.debug = true
      controlador = Controlador.new(juego,vista)
      controlador.juega
      
      
    end
    
  end

end

Civitas::Demo.main