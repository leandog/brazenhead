module Brazenhead
  module Android
    def path_to(sdk)
      jar = android_jar(sdk)
      android_jar_missing(sdk) unless File.exists? jar
      jar
    end

    def default_key_path
      File.expand_path("~/.android/debug.keystore")
    end

    private
    def android_home
      ENV['ANDROID_HOME']
    end

    def platform(sdk)
      "platforms/android-#{sdk}"
    end

    def android_jar(sdk)
      File.join(android_home, platform(sdk), 'android.jar')
    end

    def android_jar_missing(sdk)
      raise Exception, "the path to android-#{sdk} was not found"
    end
  end
end
