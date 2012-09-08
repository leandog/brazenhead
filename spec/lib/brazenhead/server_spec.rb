require 'spec_helper'

describe Brazenhead::Server do
  let(:server) { Brazenhead::Server.new(apk) }
  let(:builder) { double('brazenhead-builder') }
  let(:apk) { 'someapk.apk' }
  let(:activity) { 'SomeActivity' }

  before(:each) do
    Brazenhead::ServerBuilder.stub(:new).and_return(builder)
  end

  it "should install the server" do
    builder.should_receive(:build_for).with(apk)
    server.start(activity)
  end

  it "should only install it the first time" do
    builder.should_receive(:build_for).once.and_return(double)
    server.start(activity)
    server.start(activity)
  end

end
