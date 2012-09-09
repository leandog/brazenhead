require 'brazenhead/android'

class AndroidTest
  include Brazenhead::Android
end

describe Brazenhead::Android do
  let(:android) { AndroidTest.new }

  context "resolving android paths" do

    before(:each) do
      ENV.stub(:[]).with('ANDROID_HOME').and_return('/path/to/android')
      File.stub(:exists?).and_return(true)
    end

    it "should be able to locate the android jar" do
      ENV.stub(:[]).with('ANDROID_HOME').and_return('/path/to/android')
      android.path_to(8).should eq '/path/to/android/platforms/android-8/android.jar'
    end

    it "should raise an error if the path is not found" do
      File.should_receive(:exists?).and_return(false)
      api = 8
      lambda { android.path_to(api) }.should raise_error (message="the path to android-#{api} was not found")
    end
  end

  context "locating the keystore" do
    it "should be able to locate the path to the default keystore" do
      File.stub(:expand_path).with("~/.android/debug.keystore").and_return("/some/expanded/.android/debug.keystore")
      android.default_key_path.should eq "/some/expanded/.android/debug.keystore"
    end
  end
end
