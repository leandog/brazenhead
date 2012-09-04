require 'gametel_driver/process'

module GametelDriver
  class ManifestInfo
    def initialize(apk)
      @apk = apk
      manifest
    end

    private
    def manifest
      process.run('aapt', 'dump', 'xmltree', @apk, 'AndroidManifest.xml')
    end

    def process
      @process ||= GametelDriver::Process.new
    end
  end
end
