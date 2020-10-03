#encoding:utf-8

require_relative "casilla"
require_relative "tablero"
require_relative "dado"
require_relative "mazo_sorpresas"
require_relative "jugador"
require_relative 'diario'

module Civitas
  
=begin


  tablero = Tablero.new(1)
  tablero.añadeCasilla(Casilla.new_por_nombre("Casilla 1"))
  tablero.añadeCasilla(Casilla.new_por_nombre("Casilla 2"))
  tablero.añadeJuez()
  puts tablero.correcto(3)
  
  puts tablero.nueva_posicion(1,4)
  puts tablero.calcularTirada(4,1)
  
  puts "..Se tira el dado..\n"
  puts Dado.instance.tirar
  puts "..Salgo de la carcel.."
  puts Dado.instance.salgoDeLaCarcel
  puts "..quien empieza.."
  puts Dado.instance.quienEmpieza(3)
  puts "..ultimo resultado del dado.."
  puts Dado.instance.ultimoResultado
  
  puts "\n..Prueba clase MazoSorpresas\n"
  sorpresa1 = Sorpresa.new("Sorpresa 1")
  sorpresa2 = Sorpresa.new("Sorpresa 2")
  
  mazo = MazoSorpresas.new
  mazo.alMazo(sorpresa1) 
  mazo.alMazo(sorpresa2)
  
  mazo.inhabilitarCartaEspecial(sorpresa2)
  
  puts Diario.instance.leer_evento + "\n"
  
  mazo.inhabilitarCartaEspecial(sorpresa2)
  mazo.habilitarCartaEspecial(sorpresa1)
  
  puts Diario.instance.leer_evento  
  puts Diario.instance.leer_evento

  casilla_prueba = Casilla.new_por_nombre("Casilla1")
  jugadores_prueba = [Jugador.new("j1"),Jugador.new("j2")]
  
=end  
  
  jugadores = [Civitas::Jugador.new_por_nombre("Juan"),Civitas::Jugador.new_por_nombre("Antonio")]
  
  tablero = Civitas::Tablero.new(3)
  
  sorpresa = Civitas::Sorpresa.new_por_tablero(Civitas::TipoSorpresa::PAGARCOBRAR, tablero)
  
  sorpresa.aplicar_a_jugador(0,jugadores)
  
  puts Diario.instance.leer_evento;
  
  
end
