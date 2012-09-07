require 'brazenhead/android'
require 'brazenhead/process'

module Brazenhead
  module Signer
    include Brazenhead::Android

    def default_keystore
      {:path => default_key_path,
       :alias => 'androiddebugkey',
       :password => 'android',
       :keystore_password => 'android'}
    end

    def sign(apk, keystore)
      process.run('jarsigner', '-verbose', '-storepass', keystore[:password], '-keypass', keystore[:keystore_password], '-keystore', keystore[:path], apk, keystore[:alias])
      process.run('zipalign', '-v', '4', apk, File.join(File.dirname(apk), File.basename(apk, '.apk') + '-signed.apk'))
    end

    private
    def process
      @process ||= Brazenhead::Process.new
    end
  end
end