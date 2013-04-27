require 'brazenhead/builder'
require 'brazenhead/device'

module Brazenhead
  class Server
    include Brazenhead::Package
    include ADB

    def initialize(apk, keystore = default_keystore)
      @apk = apk
      @keystore = keystore
    end

    def start(activity, base_package = nil)
      build
      instrument(runner, :packageName => their_package, :fullLauncherName => full(activity, base_package) , :class => the_test)
    end

    def stop
      device.stop
    end

    private
    def build
      forward "tcp:7777", "tcp:54767"
      @manifest_info ||= Brazenhead::Builder.new.build_for(@apk, @keystore)
    end

    def device
      @device ||= Brazenhead::Device.new
    end

    def the_test
      "#{leandog}.TheTest"
    end

    def full(activity, base_package)
      "#{base_package || their_package}.#{activity}"
    end

    def their_package
      @manifest_info.package
    end

    def runner
      "#{leandog_package}/#{leandog}.BrazenheadInstrumentation"
    end

    def leandog
      'com.leandog.brazenhead'
    end

    def leandog_package
      "#{their_package}.brazenhead"
    end

  end
end
