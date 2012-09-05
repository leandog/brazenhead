require 'gametel_driver/package'

class PackageTest
  include GametelDriver::Package
end

describe GametelDriver::Package do
  let(:package) { PackageTest.new }
  let(:process) { double('childprocess') }
  let(:android) { double('gameteldriver-android') }

  before(:each) do
    ChildProcess.stub(:new).and_return(process)
  end

  context "updating a manifest" do
    let(:apk) { '/path/to/some_apk.apk' }
    let(:manifest) { '/path/to/AndroidManifest.xml' }
    let(:android_jar) { '/path/to/android.jar' }

    it "should update the manifest" do
      package.stub(:path_to).and_return(android_jar)
      process.should_receive(:build).with('aapt', 'p', '-u', '-f', '-F', apk, '-M', manifest, '-I', android_jar)

      package.update_manifest(apk, manifest)
    end
  end
end
