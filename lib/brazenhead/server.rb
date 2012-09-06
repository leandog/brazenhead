require 'tempfile'
require 'brazenhead/manifest_info'

module Brazenhead
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
        FileUtils.copy_file(driver_path_for(file), join(dir, file))
      end
    end

    def driver_path_for(file)
      join(File.expand_path("../../../", __FILE__), 'driver', file)
    end

    def join(*paths)
      File.join(*paths)
    end

    def update_target_in(dir)
      manifest_path = join(dir, manifest)
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
      @the_target ||= Brazenhead::ManifestInfo.new(@source_apk).package
    end

    def test_apk
      'brazenhead-release-unsigned.apk'
    end

    def manifest
      'AndroidManifest.xml'
    end

    def invalid_package_err
      raise Exception.new("Invalid :package argument")
    end

  end
end
