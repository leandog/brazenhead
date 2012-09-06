require File.join(File.dirname(__FILE__),  '/', 'request')

module Brazenhead
  class CallAccumulator
    def method_missing(method, *args)
      calls << request.call(method.to_s.to_java_call, args)
    end

    def message
      "commands=#{calls.to_json}"
    end

    def clear
      @calls = []
    end

    private
    
    def calls
      @calls ||= []
    end

    def request
      @request ||= Brazenhead::Request.new
    end

  end
end
