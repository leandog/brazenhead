require 'brazenhead/process'

module Brazenhead
  class ManifestInfo

    def package
      first_capture /\bpackage="([^"]+)"/
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
    end

    private
    def manifest
      @manifest ||= process.run('aapt', 'dump', 'xmltree', @apk, 'AndroidManifest.xml').last_stdout
    end

    def first_capture(regex)
      match = regex.match(manifest)
      match.captures[0] unless match.nil?
    end

    def sdk(which)
      found = first_capture /android:#{which}SdkVersion.*=\(.*\)0x(\h+)/
      found.hex unless found.nil?
    end


    def process
      @process ||= Brazenhead::Process.new
    end
  end
end
