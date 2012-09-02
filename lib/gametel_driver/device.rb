require 'net/http'

module GametelDriver
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
      @last_response
    end

    def last_response
      @last_response
    end

    private

    def http
      @http ||= Net::HTTP.new '127.0.0.1', 7777
    end

    
  end
end
