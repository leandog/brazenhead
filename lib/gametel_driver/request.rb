require 'json'

module GametelDriver
  class Request
    def build(method, args)
      message = "commands=#{[{:name => method}].to_json}" if args.empty?
      message = "commands=#{[{:name => method, :arguments => args}].to_json}" unless args.empty?
      message
    end
  end
end
