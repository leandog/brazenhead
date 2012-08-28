require 'net/http'

module Command
  
  attr_reader :http, :last_response

  def connect(server = '127.0.0.1', port = 7777)
    @http = Net::HTTP.new server, port
  end

  def connected?
    !@http.nil?
  end

  def kill
    @http.post '/kill', {}.to_json
  end

  def execute(*commands)
    retries = 0
    begin
      @last_response = @http.post '/', "commands=#{commands.to_json}"
    rescue Exception => err
      retries += 1
      sleep 1
      retry unless retries > 20
      puts "Failed to send the command #{commands.to_json} #{retries} times..."
      raise err
    end
  end

end
World(Command)
