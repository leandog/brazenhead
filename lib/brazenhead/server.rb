require 'brazenhead/builder'

module Brazenhead
  class Server
    include Brazenhead::Package
    include ADB

    def initialize(apk)
      @apk = apk
    end

    def start(activity)
      build
    end

    private
    def build
      @build ||= builder.build_for(@apk)
    end
    
    def builder
      @builder ||= Brazenhead::Builder.new
    end

  end
end
