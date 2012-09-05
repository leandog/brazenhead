module GametelDriver
  class Server
    def generate(apk)
      validate(apk)

      Dir.mktmpdir do |dir|
        copy_base_files_to dir
        manifest_info = GametelDriver::ManifestInfo.new(apk)
        the_target = manifest_info.package
        contents = File.read(File.join(dir, manifest))
        File.write(File.join(dir, manifest), contents.gsub(/\btargetPackage="[^"]+"/, "targetPackage=\"#{the_target}\""))
      end
    end

    private
    def validate(apk)
      invalid_package_err unless package_exists(apk)
    end

    def copy_base_files_to(dir)
        base_files.each do |file|
          File.copy_file("../../#{file}", dir)
        end
    end

    def base_files
      [test_apk, manifest]
    end

    def test_apk
      'gametel_driver-release-unsigned.apk'
    end

    def manifest
      'AndroidManifest.xml'
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
