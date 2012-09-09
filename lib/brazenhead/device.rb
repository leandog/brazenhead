require 'net/http'

module Brazenhead
  class Device

    def send(message)
      retries = 0
      begin
        @last_response = post '/', message
      rescue
        retries += 1
        sleep 0.5
        retry unless retries == 20
        $stderr.puts "Failed to send the command #{message} #{retries} times..."
        raise
      end
      @last_response
    end
    
    def stop
      post '/kill'
    end

    def last_response
      @last_response
    end

    private

    def http
      @http ||= Net::HTTP.new '127.0.0.1', 7777
    end

    def post(endpoint, message={})
      http.post endpoint, message
    end

    
  end
end
