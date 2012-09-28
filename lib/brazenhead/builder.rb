require 'tempfile'
require 'brazenhead/manifest_info'
require 'brazenhead/package'
require 'brazenhead/process'
require 'ADB'

module Brazenhead
  class Builder
    include Brazenhead::Package
    include ADB

    def build_for(apk, keystore)
      @source_apk = File.expand_path apk
      invalid_package_err(apk) unless File.exists? @source_apk

      @keystore = keystore
      @keystore[:path] = File.expand_path @keystore[:path]

      Dir.mktmpdir do |temp_dir|
        install_server temp_dir
      end

      manifest_info
    end

    private
    def install_server(dir)
      Dir.chdir(dir) do |here|
        copy_base_files
        update_test_manifest
        sign test_apk, @keystore
        reinstall test_apk
        reinstall @source_apk
      end
    end

    def reinstall(apk, timeout=90)
      install apk, "-r", {}, timeout
    end

    def copy_base_files
      [test_apk, android_manifest].each do |file|
        FileUtils.cp driver_path_for(file), Dir.pwd
      end
    end

    def driver_path_for(file)
      File.join File.expand_path("../../../", __FILE__), 'driver', file
    end

    def update_test_manifest
      replace android_manifest, /\btargetPackage="[^"]+"/,  "targetPackage=\"#{the_target}\""
      update_manifest test_apk, android_manifest, manifest_info.target_sdk
    end

    def replace(file, match, replacement)
      File.write(file, File.read(file).gsub(match, replacement))
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

    def android_manifest
      'AndroidManifest.xml'
    end

    def invalid_package_err(apk)
      raise Exception.new("Invalid package path:  #{apk}")
    end

    def process
      @process ||= Brazenhead::Process.new
    end

  end
end
