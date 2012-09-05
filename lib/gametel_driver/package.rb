require 'gametel_driver/android'

module GametelDriver
  module Package
    include GametelDriver::Android

    def update_manifest(apk, manifest, min_sdk = 8)
      ChildProcess.new.build('aapt', 'p', '-u', '-f', '-F', apk, '-M', manifest, '-I', path_to(min_sdk))
    end
  end

end
