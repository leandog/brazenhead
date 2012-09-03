require 'spec_helper'

describe ManifestInfo do

  before(:each) do
    @process = double('gametel-process')
    GametelDriver::Process.stub(:new).and_return(@process)
  end

  it "should load the manifest upon initialize" do
    @process.should_receive(:run).with('aapt', 'dump', 'xmltree', 'some_apk.apk', 'AndroidManifest.xml')
    ManifestInfo.new 'some_apk.apk'
  end

end
