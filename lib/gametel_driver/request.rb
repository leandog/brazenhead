require 'json'

module GametelDriver
  class Request
    def build(method, *args)
      "commands=#{[{:name => method}].to_json}"
    end
  end
end
