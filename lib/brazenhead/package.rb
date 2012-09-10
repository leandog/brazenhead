require 'brazenhead/android'
require 'brazenhead/signer'

module Brazenhead
  module Package
    include Brazenhead::Android
    include Brazenhead::Signer

    def update_manifest(apk, manifest, min_sdk = 8)
      process.run(*update, *package(apk), *with(manifest), *using(path_to(min_sdk)))
    end

    private
    def process
      @process ||= Brazenhead::Process.new
    end

    def update
      "aapt p -u -f".split
    end

    def package(apk)
      ["-F", apk]
    end

    def with(manifest)
      ["-M", manifest]
    end

    def using(path)
      ["-I", path]
    end

  end

end
