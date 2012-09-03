require 'json'

module GametelDriver
  class Request
    def build(method, args)
      "commands=#{[call(method, args)].to_json}"
    end

    def call(method, args)
      call = {:name => method, :target => 'Robotium'} if args.empty?
      call = {:name => method, :arguments => args, :target => 'Robotium'} unless args.empty?
      call
    end
  end
end
