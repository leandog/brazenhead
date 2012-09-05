require 'gametel_driver/package'

class PackageTest
  include GametelDriver::Package
end

describe GametelDriver::Package do
  let(:package) { PackageTest.new }
  let(:process) { double('childprocess') }
  let(:android) { double('gameteldriver-android') }
  let(:apk) { '/path/to/some_apk.apk' }

  before(:each) do
    ChildProcess.stub(:new).and_return(process)
  end

  context "updating a manifest" do
    let(:manifest) { '/path/to/AndroidManifest.xml' }
    let(:android_jar) { '/path/to/android.jar' }

    it "should update the manifest" do
      package.stub(:path_to).and_return(android_jar)
      process.should_receive(:build).with('aapt', 'p', '-u', '-f', '-F', apk, '-M', manifest, '-I', android_jar)

      package.update_manifest(apk, manifest)
    end
  end

  context "signing a package with the default" do
    let(:default_keystore) { {:path => '~/.android/debug.keystore', :alias => 'androiddebugkey', :password => 'android', :keystore_password => 'android' } }

    it "should locate the default keystore" do
      package.should_receive(:default_keystore)
      package.sign_default(apk)
    end

    it "should sign the package" do
      package.should_receive(:default_keystore).and_return(default_keystore)
      package.should_receive(:sign).with(apk, default_keystore)
      package.sign_default(apk)
    end
  end
end
