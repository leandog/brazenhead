require 'brazenhead/android'

class AndroidTest
  include Brazenhead::Android
end

describe Brazenhead::Android do
  let(:android) { AndroidTest.new }
  context "resolving android paths" do
    it "should be able to locate the android jar" do
      ENV.stub(:[]).with('ANDROID_HOME').and_return('/path/to/android')
      android.path_to(8).should eq '/path/to/android/platforms/android-8/android.jar'
    end

    it "should be able to locate the path to the default keystore" do
      File.stub(:expand_path).with("~/.android/debug.keystore").and_return("/some/expanded/.android/debug.keystore")
      android.default_key_path.should eq "/some/expanded/.android/debug.keystore"
    end
  end
end
