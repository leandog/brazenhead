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
        copy_base_files_to here
        update_manifest_in here
        store_resources here
        sign test_apk, @keystore
        reinstall test_apk
        reinstall @source_apk
      end
    end

    def reinstall(apk, timeout=90)
      install apk, "-r", {}, timeout
    end

    def copy_base_files_to(dir)
      [test_apk, manifest].each do |file|
        FileUtils.copy_file driver_path_for(file), join(dir, file)
      end
    end

    def driver_path_for(file)
      join(File.expand_path("../../../", __FILE__), 'driver', file)
    end

    def join(*paths)
      File.join(*paths)
    end

    def update_manifest_in(dir)
      manifest_path = join dir, manifest

      replace manifest_path, target_match, target_replace
      update_manifest test_apk, manifest_path, manifest_info.target_sdk
    end

    def store_resources(dir)
      Dir.mkdir "assets"
      dump_resources @source_apk, "assets/resources.txt"
      add_file test_apk, "assets/resources.txt"
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

    def process
      @process ||= Brazenhead::Process.new
    end

  end
end
