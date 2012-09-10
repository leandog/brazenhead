require 'tempfile'
require 'brazenhead/manifest_info'
require 'brazenhead/package'
require 'ADB'

module Brazenhead
  class Builder
    include Brazenhead::Package
    include ADB

    def build_for(apk, keystore)
      @source_apk = apk
      @keystore = keystore
      invalid_package_err(apk) unless File.exists? @source_apk
      install_server
      manifest_info
    end

    private
    def install_server
      Dir.mktmpdir do |dir|
        copy_base_files_to(dir)
        update_manifest_in(dir)
        sign(test_apk_in(dir), @keystore)
        reinstall test_apk_in(dir)
        reinstall @source_apk
      end
    end

    def reinstall(apk, timeout=90)
      install apk, "-r", {}, timeout
    end

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

    def update_manifest_in(dir)
      manifest_path = join(dir, manifest)

      replace(manifest_path, target_match, target_replace)
      update_manifest(test_apk_in(dir), manifest_path, manifest_info.target_sdk)
    end

    def test_apk_in(dir)
      join(dir, test_apk)
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
      @the_target ||= manifest_info.package
    end

    def manifest_info
      @info ||= Brazenhead::ManifestInfo.new(@source_apk)
    end

    def test_apk
      'brazenhead-release-unsigned.apk'
    end

    def manifest
      'AndroidManifest.xml'
    end

    def invalid_package_err(apk)
      raise Exception.new("Invalid package path:  #{apk}")
    end

  end
end
