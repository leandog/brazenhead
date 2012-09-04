require 'gametel_driver/process'

module GametelDriver
  class ManifestInfo
    def min_sdk
      match = /android:minSdkVersion.*=\(.*\)0x(\h+)/.match manifest
      min = 1
      min = match.captures[0].hex unless match.nil?
      min
    end

    def target_sdk
      match = /android:targetSdkVersion.*=\(.*\)0x(\h+)/.match manifest
      target = match.captures[0].hex unless match.nil?
      target
    end

    def initialize(apk)
      @apk = apk
      manifest
    end

    private
    def manifest
      @manifest ||= dump_manifest
    end

    def dump_manifest
      process.run('aapt', 'dump', 'xmltree', @apk, 'AndroidManifest.xml')
      process.last_stdout
    end

    def process
      @process ||= GametelDriver::Process.new
    end
  end
end
