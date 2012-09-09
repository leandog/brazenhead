require 'spec_helper'

describe Brazenhead::Server do
  let(:server) { Brazenhead::Server.new(apk) }
  let(:builder) { double('brazenhead-builder').as_null_object }
  let(:manifest) { double('manifest-info') }
  let(:apk) { 'someapk.apk' }
  let(:activity) { 'SomeActivity' }

  before(:each) do
    Brazenhead::Builder.stub(:new).and_return(builder)
    builder.stub(:build_for).with(apk).and_return(manifest)
    server.stub(:shell)
    server.stub(:instrument)
    manifest.stub(:package).and_return('com.example')
  end

  context "installing the server for the first time" do
    it "should install the server" do
      server.start(activity)
    end

    it "should only install it the first time" do
      builder.should_receive(:build_for).once.and_return(manifest)
      server.start(activity)
      server.start(activity)
    end
  end

  context "instrumenting the application" do
    let(:runner) { 'com.leandog.brazenhead/com.leandog.brazenhead.BrazenheadInstrumentation' }

    it "should use the package from the target manifest" do
      manifest.should_receive(:package)
      server.start(activity)
    end

    it "should start instrumenting" do
      expected = {:packageName => 'com.example', :fullLauncherName => 'com.example.SomeActivity', :class => 'com.leandog.brazenhead.TheTest'}
      server.should_receive(:instrument).with(runner, expected)
      server.start(activity)
    end

    it "should be able to stop instrumenting" do
      device = double('brazenhead-device')
      Brazenhead::Device.should_receive(:new).and_return(device)
      device.should_receive(:stop)
      server.stop
    end

  end

end
