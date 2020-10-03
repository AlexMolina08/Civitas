module Civitas
  class Diario  
    #esta clase sigue el patrón singleton (única instancia)
    
    private
    def initialize
      @eventos = Array.new
    end
    
    @@instance = Diario.new 
    
    public
    def self.instance
      return @@instance
    end

    def ocurre_evento(e)
      @eventos.push(e)
    end

    def eventos_pendientes
      return (@eventos.length > 0)
    end

    def leer_evento
      e = @eventos.shift
      return e
    end


    
  end
end
