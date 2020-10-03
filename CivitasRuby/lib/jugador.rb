#encoding:utf-8


require_relative 'diario'
require_relative 'sorpresa'
require_relative 'mazo_sorpresas'
require_relative 'tipo_sorpresa'
require_relative 'titulo_propiedad'
require_relative 'jugador'


module Civitas
 
    class Jugador


      @@casas_max=4
      @@casas_por_hotel=4
      @@hoteles_max=4
      @@paso_por_salida=1000.0
      @@precio_libertad=200.0

      attr_reader :nombre , :num_casilla_actual , :encarcelado , :casas_por_hotel , :puede_comprar,
        :hoteles_max , :precio_libertad , :paso_por_salida , :propiedades , :saldo , :salvoconducto 
      
      attr_accessor :nombre , :num_casilla_actual , :encarcelado , :casas_por_hotel , :puede_comprar,
        :hoteles_max , :precio_libertad , :paso_por_salida , :propiedades , :saldo , :salvoconducto 

      private :precio_libertad ,:paso_por_salida


      def initialize(encarcelado,nombre,num_casilla_actual,puede_comprar,saldo,salvoconducto,propiedades)
        @encarcelado = encarcelado
        @nombre = nombre
        @num_casilla_actual = num_casilla_actual
        @puede_comprar = puede_comprar
        @saldo = saldo
        @salvoconducto = salvoconducto
        @propiedades = propiedades
      end

      def self.new_por_nombre(nombre)

        new(false,nombre,0,false,95000,nil,Array.new)

      end
      

      def self.new_por_copia(jugador)
        new(jugador.encarcelado, jugador.nombre, jugador.num_casilla_actual, jugador.puede_comprar, jugador.saldo,
          jugador.salvoconducto, jugador.propiedades)
      end
      
      def compare_to(otro)
=begin
a <=> b :=
  if a < b then return -1
  if a = b then return  0
  if a > b then return  1
  if a and b are not comparable then return nil
=end
      @saldo<=>otro.saldo
        
      end

      def is_encarcelado

      end

      def to_string   

        datos = "Nombre del jugador: #{@nombre} Saldo del jugador: #{@saldo}
Número de propiedades del jugador: #{@propiedades.length()}
Número casilla actual del juagdor: #{@num_casilla_actual}"

        datos;
      end

      def comprar(titulo)
        
        result = false
        
        if(@encarcelado)
          return result
        
        elsif(@puede_comprar)
          
          precio = titulo.precio_compra
          
          if(puedo_gastar(precio))
            result = titulo.comprar(self)
          
          
            if(result)
              @propiedades<<titulo
              Diario.instance.ocurre_evento("++#{@nombre} ha adquirido la propiedad #{titulo.nombre}++")
              @puede_comprar = false
            end
          end
        end
      result
      end

      def cancelar_hipoteca(ip)
        result = false; 
        
        if(@encarcelado)
          return result
        
        elsif(existe_la_propiedad(ip) and @propiedades[ip].hipotecado)
          propiedad = @propiedades[ip];
          cantidad = propiedad.get_importe_cancelar_hipoteca
          
          if(puedo_gastar(cantidad))
            result = propiedad.cancelar_hipoteca(self)
            
            if(result)
              Diario.instance.ocurre_evento("#{@nombre} cancela la hipoteca de la propiedad #{ip}")
            end
            
          end
          result
        end
      end

      def cantidad_casas_hoteles
        suma = 0
        for i in(0..@propiedades.length)
          suma += @propiedades[i].num_casas
          suma += @propiedades[i].num_hoteles
        end
        
        suma
      end



      def construir_casa(ip)
        result=false
        
        if(@encarcelado)
          return result
        else
          existe = existe_la_propiedad(ip)
          if(existe)
            
            propiedad = @propiedades[ip]
            puedo_edificar = puedo_edificar_casa(propiedad)
            precio = propiedad.precio_edificar
            
            if(puedo_gastar(precio) and propiedad.num_casas < @@casas_max)
              puedo_edificar = true
            end
            
            if(puedo_edificar)
              result = propiedad.construir_casa(self)
            end
              
            if(result)
              Diario.instance.ocurre_evento("#{@nombre} construye una casa en #{@propiedades[ip].nombre}")
            end
          end
        end
        result
      end

      def construir_hotel(ip)
        result = false
        
        if(@encarcelado)
          return result
        
        elsif(@existe_la_propiedad[ip])
          propiedad = @propiedades[ip]
          puedo_edificar = puedo_edificar_hotel(propiedad)
          precio = propiedad.precio_edificar
          
          if(puedo_gastar(precio) and propiedad.num_hoteles < @@hoteles_max and propiedad.num_casas == @@casas_max)
            puedo_edificar = true
          end
          
          if(puedo_edificar)
            result = propiedad.construir_hotel(self)
            propiedad.derruir_casas(@@casas_por_hotel,self)
            Diario.instance.ocurre_evento("#{@nombre} construye un hotel en #{@propiedades[ip].nombre}")
          end
        end
        result
      end


      def en_bancarrota
        devolucion=false
        
        if(@saldo<= 0 )
            devolucion=true
        end

        devolucion
      end

      def encarcelar(num_casilla_carcel)
        
          if debe_ser_encarcelado
            
            if(mover_a_casilla(num_casilla_carcel))
              @encarcelado = true
              Diario.instance.ocurre_evento("#{@nombre} entra en la prisión")
            else
              Diario.instance.ocurre_evento{"#{@nombre} no ha podido entrar en Prisión"}
            end
          end
          
          @encarcelado
      end

      def debe_ser_encarcelado

        if @encarcelado
          devolver = false
        
        elsif tiene_salvoconducto
          perder_salvoconducto
          devolver = false
          Diario.instance.ocurre_evento("#{@nombre} utiliza la carta SalvoConducto y se libra de Prisión")
        
        else
          devolver = true
        end
        devolver
      end

      

      def hipotecar(ip)
        result=false
        
        if(@encarcelado)
          return result
        elsif(existe_la_propiedad(ip))
          propiedad = @propiedades[ip]
          result = propiedad.hipotecar(self)
        end
        
        if(result)
          Diario.instance.ocurre_evento("El jugador #{@nombre} hipoteca su propiedad #{@propiedades[ip].nombre}")
        end
        
        result

      end


      def modificar_saldo(cantidad)
        
        @saldo+=cantidad
        Diario.instance.ocurre_evento("Se ha modificado el saldo de #{@nombre}(#{cantidad})")
        return true
      end

      def mover_a_casilla(num_casilla)
        
        if(@encarcelado)
          devolver = false
        else
          Diario.instance.ocurre_evento("#{@nombre} se ha movido de #{@num_casilla_actual} a #{num_casilla}")
          @num_casilla_actual = num_casilla
          @puede_comprar = false
          devolver = true
        end
        
        devolver
      end

      def obtener_salvoconducto(sorpresa)

        if (@encarcelado)
          devolver = false
        
        else
        
          @salvoconducto = sorpresa
          devolver = true
        end
        
        devolver
      end

      def paga(cantidad)

        return modificar_saldo(cantidad*-1)
      end

      
      def paga_alquiler(cantidad)
        
        if(@encarcelado)
          devolver = false
        else
        
          devolver = paga(cantidad)
        end
        devolver
      end

      def paga_impuesto(cantidad)

        if(@encarcelado)
          devolver = false
        else 
        
          devolver = paga(cantidad)
        
        end
        return devolver
      end

      def pasa_por_salida()
          modificar_saldo(@@paso_por_salida)
          Diario.instance.ocurre_evento("#{@nombre} ha pasado por la salida")
      end

      def   puede_comprar_casilla
        
        @puede_comprar = true
        
        if(@encarcelado)
          @puede_comprar = false
        end
        
        @puede_comprar
        
      end

      def recibe(cantidad)
      
        if(@encarcelado)
          resultado = false
        else
          resultado = modificar_saldo(cantidad)
        end
        resultado
      end

      def salir_carcel_pagando()
        devolver = false
        
        if(@encarcelado and puede_salir_carcel_pagando)
          
          paga(@@precio_libertad)
          @encarcelado = false
          Diario.instance.ocurre_evento("#{@nombre} ha pagado para salir de la Prisión")
        end
        
        devolver
      end

      def salir_carcel_tirando()
        
        devolver = false
        
        if (Dado.instance.salgoDeLaCarcel)
          @encarcelado = false
          Diario.instance.ocurre_evento("#{@nombre} tira el Dado para salir de la Prisión")
        end
        devolver
      end

      def   tiene_algo_que_gestionar()
      
        devolver = false
        
        if(@propiedades.length > 0 )
          devolver = true
        end
        devolver
      end

      def tiene_salvoconducto()
        devolver = false
        if(@salvoconducto != nil)
          devolver = true
        end
        devolver
      end

      def vender(ip)
        
        if(@encarcelado)
          devolver = false
        else
        
          if(existe_la_propiedad(ip) and !@propiedades[ip].hipotecado)
            propiedad = @propiedades[ip]
            devolver = propiedad.vender(self)
            Diario.instance.ocurre_evento("#{@nombre} ha vendido la propiedad #{@propiedades[ip].nombre}")
            @propiedades.delete_at(ip)
          end
        end
        devolver
      end
      
      private
      def   perder_salvoconducto
          @salvoconducto.usada
          @salvoconducto=nil
      end
      
      def existe_la_propiedad(ip)
        
        devolver = false
        
        for i in(0..@propiedades.length-1)
          if @propiedades[i].nombre == ip
            devolver = true
          end
        end
        devolver
      end
      
      def puede_salir_carcel_pagando
      
        devolver = false
        
        if @saldo >= @@precio_libertad
          devolver = true
        end
        
        devolver
        
      end
      
      def   puedo_edificar_casa(propiedad)
        return propiedad.num_casas<=@@casa_max
      end
      
      def   puedo_edificar_hotel(propiedad)
        return propiedad.num_hoteles<=@@hoteles_max
      end
      
      def puedo_gastar(precio)
        
        if(@encarcelado)
          devolver = false
        
        else
          
          if (@saldo >= precio)
            devolver = true
          end
        end
        devolver
      end
          
     private_class_method :new

    end
end

j = Civitas::Jugador.new_por_nombre("Antonio")