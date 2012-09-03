require 'json'

module GametelDriver
  class Request
    def build(method, args)
      "commands=#{[call(method, args)].to_json}"
    end

    def call(method, args)
      target = parse_target(args[-1])
      variable = parse_variable(args[-1])
      build_call(method, strip_hash_arg(args), variable, target)
    end

    private

    def build_call(method, args, variable, target)
      call = {:name => method} if args.empty?
      call = {:name => method, :arguments => args} unless args.empty?
      call[:variable] = variable if variable
      call[:target] = target if target
      call
    end

    def parse_target(hsh)
      target = hsh.delete(:target) if hsh.is_a?(Hash)
      return target.nil? ? nil : target
    end

    def parse_variable(hsh)
      variable = hsh.delete(:variable) if hsh.is_a?(Hash)
      return variable.nil? ? nil : variable
    end

    def strip_hash_arg(args)
      args.delete_at(-1) if args[-1].is_a?(Hash) and args[-1].empty?
      args
    end
  end
end
