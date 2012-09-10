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
      jarsign(apk)
      verify(apk)
      process.run(*zipalign(apk), *signed(apk))
    end

    private
    def process
      @process ||= Brazenhead::Process.new
    end

    def jarsign(apk)
      process.run(*jar_command, *keypasses, *the_key, apk, key_alias)
    end

    def jar_command
      "jarsigner -sigalg MD5withRSA -digestalg SHA1".split
    end

    def verify(apk)
      process.run(*"jarsigner -verify".split, apk)
      error_signing(apk) unless process.last_stdout.include? "jar verified"
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

    def error_signing(apk)
      raise Exception, "error signing #{apk} (#{process.last_stdout})"
    end

  end
end
