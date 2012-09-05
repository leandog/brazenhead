module GametelDriver
  class Server
    def generate(info)
      validate(info)

      Dir.mktmpdir do |dir|
        ['gametel_driver-release-unsigned.apk', 'AndroidManifest.xml'].each do |file|
          File.copy_file("../../#{file}", dir)
        end
      end
    end

    private
    def validate(info)
      missing_argument_err unless info[:package]
      invalid_package_err unless package_exists(info[:package])
    end

    def package_exists(package)
      File.exists? package
    end

    def missing_argument_err
      raise Exception.new("Missing a :package argument") 
    end

    def invalid_package_err
      raise Exception.new("Invalid :package argument")
    end

  end
end
