module GametelDriver
  module Android
    def path_to(sdk)
      File.join(ENV['ANDROID_HOME'], "platforms/android-#{sdk}", 'android.jar')
    end

    def default_keystore
    end

    def sign(apk, keystore)
    end
  end
end
