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
      @keystore = keystore
      process.run(*jarsign, *keypasses, *the_key, apk, key_alias)
      process.run(*zipalign(apk), *signed(apk))
    end

    private
    def process
      @process ||= Brazenhead::Process.new
    end

    def jarsign
      "jarsigner -verbose -sigalg MD5withRSA -digestalg SHA1".split
    end

    def zipalign(apk)
      "zipalign -v 4".split << apk
    end

    def signed(apk)
      File.join dir(apk), new_name(apk)
    end

    def dir(apk)
      File.dirname(apk)
    end

    def new_name(apk)
      File.basename(apk, '.apk') << '-signed.apk'
    end

    def keypasses
      "-storepass #{@keystore[:password]} -keypass #{@keystore[:keystore_password]}".split 
    end

    def the_key
      ["-keystore", File.expand_path(@keystore[:path])]
    end

    def key_alias
      @keystore[:alias]
    end

  end
end
