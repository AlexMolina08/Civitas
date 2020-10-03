module Civitas
  class Dado
    #esta clase sigue el patrón singleton (única instancia)
    attr_reader :random , :ultimoResultado
    attr_writer :debug
    
    
    @@SALIDA_CARCEL = 5
    @@NUMERO_CARAS = 6
    
    
    def initialize
      
      @random = rand(1..@@NUMERO_CARAS) 
      @ultimoResultado = 0
      @debug = false
      
    end
    
    @@instance = Dado.new
    
    def self.instance
      return @@instance
    end
    
    def tirar
      resultado = 1
      if(!@debug)
        resultado = rand(1..@@NUMERO_CARAS - 1) + 1
      end
      @ultimoResultado = resultado
 
      Diario.instance.ocurre_evento("Se ha tirado el dado y ha salido un #{@ultimoResultado}")
      
      return @ultimoResultado
    end
    
    def salgoDeLaCarcel
      return (@ultimoResultado >= 5)
    end
    
    def quienEmpieza(n)
      return rand(n)
    end
    
    
  end
  
end
