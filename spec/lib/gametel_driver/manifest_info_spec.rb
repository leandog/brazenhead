require 'spec_helper'

describe GametelDriver::ManifestInfo do
  let(:process) { double('gametel-process') }

  before(:each) do
    GametelDriver::Process.stub(:new).and_return(process)
  end

  it "should load the manifest upon initialize" do
    process.should_receive(:run).with('aapt', 'dump', 'xmltree', 'some_apk.apk', 'AndroidManifest.xml')
    GametelDriver::ManifestInfo.new 'some_apk.apk'
  end

end
