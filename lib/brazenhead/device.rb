require 'net/http'

module Brazenhead
  class Device

    def send(message)
      retries = 0
      begin
        @last_response = http.post '/', message
      rescue
        retries += 1
        sleep 0.5
        retry unless retries == 20
        $stderr.puts "Failed to send the command #{message} #{retries} times..."
        raise
      end
      raise Exception, @last_response.body unless @last_response.code == "200"
      @last_response
    end

    def stop
      http.post '/kill', ''
    end

    def last_response
      @last_response
    end

    def last_json
      body = last_response.body
      begin
        JSON.parse body
      rescue
        primitive_to_json body
      end
    end

    private

    def http
      @http ||= Net::HTTP.new '127.0.0.1', 7777
    end

    def primitive_to_json(string)
      JSON.parse("{\"value\": #{string}}")["value"]
    end

  end
end
