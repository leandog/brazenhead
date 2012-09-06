module GametelDriver
  module Signer
    include GametelDriver::Android

    def default_keystore
      {:path => default_key_path,
       :alias => 'androiddebugkey',
       :password => 'android',
       :keystore_password => 'android'}
    end

    def sign(apk, keystore)
      process.run('jarsigner', '-verbose', '-storepass', keystore[:password], '-keypass', keystore[:keystore_password], '-keystore', keystore[:path], apk, keystore[:alias])
    end

    private
    def process
      @process ||= GametelDriver::Process.new
    end
  end
end
