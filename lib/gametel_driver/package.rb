require 'gametel_driver/android'
require 'gametel_driver/signer'

module GametelDriver
  module Package
    include GametelDriver::Android
    include GametelDriver::Signer

    def update_manifest(apk, manifest, min_sdk = 8)
      ChildProcess.new.build('aapt', 'p', '-u', '-f', '-F', apk, '-M', manifest, '-I', path_to(min_sdk))
    end

    def sign_default(apk)
      sign(apk, default_keystore)
    end

  end

end
