require 'spec_helper'

describe Brazenhead::Server do
  let(:server) { Brazenhead::Server.new(apk) }
  let(:builder) { double('brazenhead-builder').as_null_object }
  let(:manifest) { double('manifest-info') }
  let(:apk) { 'someapk.apk' }
  let(:activity) { 'SomeActivity' }
  let(:default_keystore) { server.default_keystore }

  before(:each) do
    Brazenhead::Builder.stub(:new).and_return(builder)
    builder.stub(:build_for).with(apk, anything()).and_return(manifest)
    server.stub(:shell)
    server.stub(:forward)
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

    it "should use the default keystore if none is provided" do
      builder.should_receive(:build_for).with(apk, server.default_keystore)
      server.start(activity)
    end

    it "should use the provided keystore if it is given" do
      other_keystore = {:path => 'other_keystore'}
      builder.should_receive(:build_for).with(apk, other_keystore)
      other_server = Brazenhead::Server.new(apk, other_keystore)
      other_server.stub(:shell)
      other_server.stub(:forward)
      other_server.stub(:instrument)
      other_server.start(activity)
    end

    it "should setup the proper port forwarding" do
      server.should_receive(:forward).with("tcp:7777", "tcp:54767")
      server.start(activity)
    end
  end

  context "instrumenting the application" do
    let(:brazenhead_instrumentation) { 'com.leandog.brazenhead.BrazenheadInstrumentation' }
    let(:runner) { "com.example.brazenhead/#{brazenhead_instrumentation}" }

    it "should use the package from the target manifest" do
      manifest.should_receive(:package)
      server.start(activity)
    end

    it "should base our package off of theirs" do
      manifest.stub(:package).and_return('com.their.package')
      server.should_receive(:instrument).with("com.their.package.brazenhead/#{brazenhead_instrumentation}", anything)
      server.start(activity)
    end

    it "should start instrumenting" do
      expected = {:packageName => 'com.example', :fullLauncherName => 'com.example.SomeActivity', :class => 'com.leandog.brazenhead.TheTest'}
      server.should_receive(:instrument).with(runner, expected)
      server.start(activity)
    end

    it "should be able to start activites that are not relative to the base package" do
      expected = {:packageName => 'com.example', :fullLauncherName => 'com.other.SomeActivity', :class => 'com.leandog.brazenhead.TheTest'}
      server.should_receive(:instrument).with(runner, expected)
      server.start(activity, 'com.other')
    end

    it "should be able to stop instrumenting" do
      device = double('brazenhead-device')
      Brazenhead::Device.should_receive(:new).and_return(device)
      device.should_receive(:stop)
      server.stop
    end

  end

end
