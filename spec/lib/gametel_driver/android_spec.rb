require 'gametel_driver/android'

class AndroidTest
  include GametelDriver::Android
end

describe GametelDriver::Android do
  let(:android) { AndroidTest.new }
  context "resolving android paths" do
    it "should be able to locate the android jar" do
      ENV.stub(:[]).with('ANDROID_HOME').and_return('/path/to/android')
      android.path_to(8).should eq '/path/to/android/platforms/android-8/android.jar'

    end
  end
end
