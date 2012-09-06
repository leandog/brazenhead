module GametelDriver
  class Server
    def generate(apk)
      @source_apk = apk
      invalid_package_err unless File.exists? apk

      Dir.mktmpdir do |dir|
        copy_base_files_to(dir)
        update_target_in(dir)
      end
    end

    private
    def copy_base_files_to(dir)
      [test_apk, manifest].each do |file|
        FileUtils.copy_file("../../#{file}", dir)
      end
    end

    def update_target_in(dir)
      manifest_path = File.join(dir, manifest)
      replace(manifest_path, target_match, target_replace)
    end

    def replace(file, match, replacement)
      File.write(file, File.read(file).gsub(match, replacement))
    end

    def target_match
      /\btargetPackage="[^"]+"/
    end

    def target_replace
      "targetPackage=\"#{the_target}\""
    end

    def the_target
      @the_target ||= GametelDriver::ManifestInfo.new(@source_apk).package
    end

    def test_apk
      'gametel_driver-release-unsigned.apk'
    end

    def manifest
      'AndroidManifest.xml'
    end

    def invalid_package_err
      raise Exception.new("Invalid :package argument")
    end

  end
end
