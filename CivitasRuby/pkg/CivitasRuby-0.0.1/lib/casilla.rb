#encoding:utf-8

require_relative 'jugador'
require_relative 'tipo_casilla'
require_relative 'titulo_propiedad'
require_relative 'tipo_casilla'
require_relative 'sorpresa'

module Civitas
  
    attr_reader :titulo , :nombre
  
    class Casilla
      private_class_method :new
      attr_reader :nombre ,:titulo
      attr_accessor :nombre


      @@Carcel = 3
      
      def self.Carcel
        @@Carcel
      end
      
      def initialize(nombre , importe , titulo , mazo , sorpresa , tipo)

        @nombre = nombre
        @importe = importe
        @titulo = titulo    
        @mazo = mazo
        @sorpresa = sorpresa
        @tipo = tipo
        

      end

      def self.new_por_nombre(nombre)
        tipo = TipoCasilla::DESCANSO
        
        if(nombre == "Salida")
          tipo = TipoCasilla::DESCANSO
        elsif(nombre == "Cárcel")
          tipo = TipoCasilla::DESCANSO
        elsif(nombre == "Juez")
          tipo = TipoCasilla::JUEZ
        elsif(nombre == "Aparcamiento")
          tipo = TipoCasilla::CALLE
        else
          tipo = TipoCasilla::Calle
        end
        
        new(nombre,0,nil,nil,nil,tipo)

      end

      def self.new_por_titulo(titulo)
        tipo = TipoCasilla::CALLE
        new(titulo.nombre,titulo.precio_compra,titulo,nil,nil,tipo)
        
      end
      
      def self.new_por_cantidad(cantidad,nombre)
        tipo = TipoCasilla::IMPUESTO
        
        new(nombre,cantidad,nil,nil,nil,tipo)
      end

      def self.new_juez(numCasillaCarcel)
        tipo = TipoCasilla::JUEZ
        new("Juez",0,nil,nil,nil,tipo)
      end
      
      def self.new_sorpresa(mazo,nombre)
        tipo = TipoCasilla::SORPRESA
        
        new("Sorpresa",0,nil,nil,nil,tipo)
      end
      


      def to_string
        
        resultado = "Casilla: "+@nombre+"\nimporte:"+@importe.to_s+"\n\n"
        
        if(@titulo != nil)
          resultado+=@titulo.to_string+"\n\n"
        end
        
        
        
        resultado
        
        
      end


      #METODOS PRIVADOS   
      private
      
      def informe(iactual,todos)
        Diario.instance.ocurre_evento(todos[iactual].nombre + " ha caido en #{@nombre}")

      end

      def recibe_jugador_impuesto(iactual , todos)

        if( jugador_correcto(iactual,todos) )
         
          informe(iactual,todos)
          todos[iactual].paga_impuesto(@importe)
        end
      end

      def recibe_jugador_calle(iactual , todos)
        if(jugador_correcto(iactual,todos))
            informe(iactual,todos)
            jugador=todos[iactual]

            if(@titulo.tiene_propietario and !@titulo.propietario.encarcelado)
               
              if(@titulo.propietario != jugador and ! @titulo.hipotecado)
                jugador.puede_comprar = false
                jugador.paga_alquiler(@titulo.get_precio_alquiler)
                @titulo.propietario.recibe(@titulo.get_precio_alquiler)
                
              elsif(@titulo.hipotecado)
                jugador.puede_comprar = false
              end
            
            else
              
              jugador.puede_comprar = true
            end
        end
      end

      public
      def recibe_jugador(iactual,todos)
          case @tipo
          when TipoCasilla::CALLE
              recibe_jugador_calle(iactual,todos)
          when TipoCasilla::IMPUESTO
              recibe_jugador_impuesto(iactual,todos)
          when TipoCasilla::JUEZ
              recibe_jugador_juez(iactual,todos)
          when TipoCasilla::SORPRESA
              recibe_jugador_sorpresa(iactual,todos)
          else
              informe(iactual,todos)
          end
      end
      
      private
      def jugador_correcto(iactual,todos)

        iactual >= 0 && todos.size() > iactual

      end

      def recibe_jugador_juez(iactual,todos)
        if(jugadorCorrecto(iactual, todos))
          informe(iactual, todos);
          todos[iactual].encarcelar(@@carcel);
        end
      end

      def recibe_jugador_sorpresa(iactual , todos)
        if(jugador_correcto(iactual,todos))
          informe(iactual,todos)
          @sorpresa=@mazo.siguiente
          @sorpresa.aplicar_a_jugador(iactual, todos)
        end
      end
  end
end