require 'net/http'

module Command
  
  attr_reader :http, :last_response

  def connect(server = '127.0.0.1', port = 7777)
    @http = Net::HTTP.new server, port
  end

  def kill
    @http.post '/kill', {}.to_json
  end

  def execute(*commands)
    @last_response = @http.post '/', "commands=#{commands.to_json}"
  end

end
World(Command)
