module GametelDriver
  class Server
    def generate(apk)
      validate(apk)

      Dir.mktmpdir do |dir|
        copy_base_files_to(dir)
        update_target_in(dir)
      end
    end

    private
    def validate(apk)
      @source_apk = apk
      invalid_package_err unless package_exists(apk)
    end

    def update_target_in(dir)
      File.write(manifest_path(dir), manifest_contents(dir).gsub(target_match, target_replace))
    end

    def target_match
      /\btargetPackage="[^"]+"/
    end

    def target_replace
      "targetPackage=\"#{the_target}\""
    end

    def manifest_contents(dir)
      File.read(manifest_path(dir))
    end

    def the_target
      @the_target ||= GametelDriver::ManifestInfo.new(@source_apk).package
    end

    def manifest_path(dir)
      File.join(dir, manifest)
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
