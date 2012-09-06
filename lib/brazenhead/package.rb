require 'brazenhead/android'
require 'brazenhead/signer'

module Brazenhead
  module Package
    include Brazenhead::Android
    include Brazenhead::Signer

    def update_manifest(apk, manifest, min_sdk = 8)
      process.run('aapt', 'p', '-u', '-f', '-F', apk, '-M', manifest, '-I', path_to(min_sdk))
    end

    def sign_default(apk)
      sign(apk, default_keystore)
    end

    private
    def process
      @process ||= Brazenhead::Process.new
    end

  end

end
