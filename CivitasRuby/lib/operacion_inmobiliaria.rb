#encoding:utf-8
module Civitas
  
  class Operacion_Inmobiliaria
    
    
    attr_reader :gestion , :num_propiedad
    attr_accessor :gestion , :num_propiedad
    
    def initialize(gestion , num_propiedad)
      @gestion = gestion
      @num_propiedad = num_propiedad
    end
    
  end
  
end