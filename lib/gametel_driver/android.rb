module GametelDriver
  module Android
    def path_to(sdk)
      File.join(ENV['ANDROID_HOME'], "platforms/android-#{sdk}", 'android.jar')
    end
  end
end
