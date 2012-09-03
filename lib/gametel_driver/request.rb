require 'json'

module GametelDriver
  class Request
    def build(method, args)
      "commands=#{[call(method, args)].to_json}"
    end

    def call(method, args)
      target = parse_target(args[-1])
      args = strip_hash_arg(args)
      call = {:name => method, :target => target} if args.empty?
      call = {:name => method, :arguments => args, :target => target} unless args.empty?
      call
    end

    private

    def parse_target(hsh)
      hsh.is_a?(Hash) ? hsh.delete(:target) : "LastResultOrRobotium"
    end

    def strip_hash_arg(args)
      args.delete_at(-1) if args[-1].is_a?(Hash) and args[-1].empty?
      args
    end
  end
end
