require 'spec_helper'

describe GametelDriver::ManifestInfo do
  let(:process) { double('gametel-process').as_null_object }
  let(:manifest_info) { GametelDriver::ManifestInfo.new 'some_apk.apk' }

  before(:each) do
    GametelDriver::Process.stub(:new).and_return(process)
  end

  it "should load the manifest upon initialize" do
    process.should_receive(:run).with('aapt', 'dump', 'xmltree', 'some_apk.apk', 'AndroidManifest.xml')
    manifest_info
  end

  it "should grab the minimum sdk" do
    process.should_receive(:last_stdout).and_return("
E: uses-sdk (line=39)
A: android:minSdkVersion(0x0101020c)=(type 0x10)0x0f
A: android:targetSdkVersion(0x01010270)=(type 0x10)0xe")

    manifest_info.min_sdk.should eq 15
  end

  it "should default the minimum sdk to 1" do
    process.should_receive(:last_stdout).and_return("
E: uses-sdk (line=39)
A: android:notTheminSdkVersion(0x0101020c)=(type 0x10)0x0f
A: android:targetSdkVersion(0x01010270)=(type 0x10)0xe")

    manifest_info.min_sdk.should eq 1
  end

  it "should grab the target sdk" do
    process.should_receive(:last_stdout).and_return("
E: uses-sdk (line=39)
A: android:notTheminSdkVersion(0x0101020c)=(type 0x10)0x0f
A: android:targetSdkVersion(0x01010270)=(type 0x10)0xe")

    manifest_info.target_sdk.should eq 14
  end

  it "should default the target sdk" do
    process.should_receive(:last_stdout).and_return("
E: uses-sdk (line=39)
A: android:notTheminSdkVersion(0x0101020c)=(type 0x10)0x0f")

    manifest_info.target_sdk.should be_nil
  end

end
