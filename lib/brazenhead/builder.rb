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
      @keystore = keystore
      invalid_package_err(apk) unless File.exists? @source_apk
      install_server
      manifest_info
    end

    private
    def install_server
      Dir.mktmpdir do |temp_dir|
        copy_base_files_to temp_dir
        update_manifest_in temp_dir
        store_resources temp_dir
        sign test_apk_in(temp_dir), @keystore
        reinstall test_apk_in(temp_dir)
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
      manifest_path = join dir, manifest

      replace manifest_path, target_match, target_replace
      update_manifest test_apk_in(dir), manifest_path, manifest_info.target_sdk
    end

    def store_resources(dir)
      original_dir = Dir.pwd
      Dir.chdir dir
      assets = File.join dir, "assets"
      Dir.mkdir assets
      process.run(*"aapt dump resources".split, @source_apk)
      resource_file = File.join(assets, "resources.txt")
      File.write resource_file, process.last_stdout
      process.run('aapt', 'add', test_apk_in(dir), "assets/resources.txt")
      Dir.chdir original_dir
    end

    def test_apk_in(dir)
      join dir, test_apk
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
