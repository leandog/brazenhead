require 'brazenhead/package'

class PackageTest
  include Brazenhead::Package
end

describe Brazenhead::Package do
  let(:package) { PackageTest.new }
  let(:process) { double('brazenhead-process').as_null_object }
  let(:android) { double('brazenhead-android') }
  let(:apk) { '/path/to/some_apk.apk' }

  before(:each) do
    Brazenhead::Process.stub(:new).and_return(process)
  end

  context "updating a manifest" do
    let(:manifest) { '/path/to/AndroidManifest.xml' }
    let(:android_jar) { '/path/to/android.jar' }

    it "should update the manifest" do
      package.stub(:path_to).and_return(android_jar)
      process.should_receive(:run).with('aapt', 'p', '-u', '-f', '-F', apk, '-M', manifest, '-I', android_jar)

      package.update_manifest(apk, manifest)
    end
    
  end

end
