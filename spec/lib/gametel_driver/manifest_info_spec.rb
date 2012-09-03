require 'spec_helper'

describe ManifestInfo do

  before(:each) do
    @process = double('process')
    @process.stub(:wait)
    ChildProcess.stub(:build) { @process }
  end

  it "should load the manifest upon initialize" do
    ChildProcess.should_receive(:build).with('aapt', 'dump', 'xmltree', 'some_apk.apk', 'AndroidManifest.xml')
    @process.should_receive(:wait)
    ManifestInfo.new 'some_apk.apk'
  end

end
