#encoding:utf-8
module Civitas


  class TituloPropiedad

    @@FACTOR_INTERESES_HIPOTECA=1.1

    attr_reader   :num_casas , :num_hoteles , :nombre , :precio_compra , :alquiler_base,
                  :precio_edificar    , :propietario, :hipotecado
    
    private  :alquiler_base
    
    attr_accessor :precio_edificar    , :propietario, :hipotecado  , :nombre
    

    def initialize(nombre,alquiler_base,factor_revalorizacion,hipoteca_base,precio_compra,precio_edificar)    
      @alquiler_base=alquiler_base
      @factor_revalorizacion=factor_revalorizacion
      @hipoteca_base=hipoteca_base
      @hipotecado=false
      @nombre=nombre
      @num_casas=0
      @num_hoteles=0
      @precio_compra=precio_compra
      @precio_edificar=precio_edificar
      @propietario=nil

    end
    
  def nombre
    @nombre
  end

   def   to_string

           "nombre: "+@nombre+"\nAlquiler Base: "+@alquiler_base.to_s+\
                  "\nfactor revalorizacion: "+@factor_revalorizacion.to_s+\
                  "\nhipoteca base: "+@hipoteca_base.to_s+"\nhipotecado: "+@hipotecado.to_s+\
                  "\nnºcasas: "+@num_casas.to_s+"\nnºhoteles"+@num_hoteles.to_s+\
                  "\nprecio compra: "+@precio_compra.to_s+"\nprecio edificar: "+\
                  @precio_edificar.to_s;

   end

   def actualiza_propietario_por_conversion(jugador)
      @propietario=jugador
   end

   def cancelar_hipoteca(jugador)
    realizado = false
    
    if(es_este_el_propietario(jugador) and jugador.paga(get_importe_cancelar_hipoteca) and @hipotecado)
      @hipotecado = false
      realizado = true
    end
    
    realizado
   end

   def cantidad_casas_hoteles
     @num_casas+@num_hoteles
   end

   def comprar(jugador)
     realizado = false
     
     if(!tiene_propietario and jugador.paga(@precio_compra))
       actualiza_propietario_por_conversion(jugador)
       realizado = true
     end
     realizado
   end

   def   construir_casa(jugador)
     construido = false
     if(es_este_el_propietario(jugador) and jugador.paga(@precio_edificar) and @num_casas <= 4)
       
       @num_casas += 1
       construido = true
       
     end
     construido
   end

   def construir_hotel(jugador)
     
     construido = false
     
     if(es_este_el_propietario(jugador) and jugador.paga(@precio_edificar) and @num_casas >=4 and @num_hoteles <= 4)
       @num_casas-=4
       @num_hoteles+=1
       construido = true
     end
     
     construido
   end

   def derruir_casas(n,jugador)
     realizada = false
     
     if(es_este_el_propietario(jugador) and @num_casas >= n) 
       @num_casas -= n
       realizada = true
     end
     
     return realizada
   end
   
   def derruir_hoteles(n,jugador)
     realizada = false
     
     if(es_este_el_propietario(jugador) and @num_hoteles >= n)
       @num_hoteles -= n
       realizada = true
     end
     
     realizada 
   end

   def hipotecar(jugador)
     salida = false
     
     if(!@hipotecado and es_este_el_propietario(jugador))
        @propietario.recibe(get_importe_hipoteca)
        @hipotecado=true
        @salida=true
      end
      salida
   end

   public
   def tiene_propietario
      aux = false
      if(propietario!=nil)
        aux = true
      end
      aux
   end
private
   def tramitar_alquiler(jugador)
        if(tiene_propietario and !es_este_el_propietario(jugador))
          jugador.paga_alquiler(get_precio_alquiler)
          @propietario.recibe(get_precio_alquiler)
        end
   end

   def   vender(jugador)
     @devolucion=false
      if(es_este_el_propietario(jugador) and !hipotecado)
        @propietario.recibe(get_precio_venta)
        @propietario=nil
        @num_casas=0
        @num_hoteles=0
        @devolucion=true
      end

      return @devolucion
   end
   public
   def get_precio_alquiler
      if(!@propietario.encarcelado)
       precio_alquiler = @alquiler_base*(1+(@num_casas*0.5)+(@num_hoteles*2.5))    
      end
      precio_alquiler
   end
    
   #PRIVADOS
   
   private
    def es_este_el_propietario(jugador)
         if(jugador==propietario)
           return true
         end 
           return false
    end
   
    
  
    def get_precio_venta
      @precio_compra+@num_casas*@precio_edificar+@num_hoteles*@precio_edificar*@factor_revalorizacion
    end
    
   def get_importe_cancelar_hipoteca
     @hipoteca_base*@factor_intereses_hipoteca
   end
   
  def propietario_encarcelado
   
    resultado = false
    
    if(@propietario.encarcelado)
      resultado = true
    end
    resultado
  end
   
  
  end
end

