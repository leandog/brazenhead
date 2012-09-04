require 'gametel_driver/process'

module GametelDriver
  class ManifestInfo

    def package
      match = /\bpackage="([^"]+)"/.match(manifest)
      match.captures[0] unless match.nil?
    end

    def min_sdk
      sdk(:min) || 1
    end

    def max_sdk
      sdk(:max)
    end

    def target_sdk
      sdk(:target)
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

    def sdk(which)
      match = /android:#{which}SdkVersion.*=\(.*\)0x(\h+)/.match manifest
        match.captures[0].hex unless match.nil?
    end


    def process
      @process ||= GametelDriver::Process.new
    end
  end
end
