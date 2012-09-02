require 'gametel_driver/version'
require 'json'

module GametelDriver
  def method_missing(method, *args)
    call_method_on_driver(convert_to_java_call(method.to_s), args)
  end

  def last_response
    @last_response
  end

  private

  def call_method_on_driver(method, *args)
    retries = 0
    begin
      @last_response = http.post '/', build_message(method, args)
    rescue
      retries += 1
      sleep 1
      retry unless retries == 20
      $stderr.puts "Failed to send the command #{commands.to_json} #{retries} times..."
      raise
    end
    @last_response
  end

  def convert_to_java_call(target)
    return target if target !~ /_/ && target =~ /[A-Z]+.*/
    camel = target.split('_').map{|e| e.capitalize}.join
    camel.sub(camel[0], camel[0].downcase)
  end

  def http
    @http ||= Net::HTTP.new '127.0.0.1', 7777
  end

  def build_message(method, *args)
    commands = [{:name => method}]
    "commands=#{commands.to_json}"
  end
end
