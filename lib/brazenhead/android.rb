module Brazenhead
  module Android
    def path_to(sdk)
      File.join(ENV['ANDROID_HOME'], "platforms/android-#{sdk}", 'android.jar')
    end

    def default_key_path
      File.expand_path("~/.android/debug.keystore")
    end
  end
end
