require 'spec_helper'

describe Brazenhead::Device do
  let(:device) { Brazenhead::Device.new }
  let(:http_mock) { double("http_mock") }

  before(:each) do
    Net::HTTP.stub(:new).and_return(http_mock)
  end

  it "should retry the http call if it fails the first time" do
    http_mock.should_receive(:post).and_raise("error")
    http_mock.should_receive(:post).with('/', "blah")
    device.send "blah"
  end

  it "should retry the http call a maximum of 20 times" do
    device.should_receive(:sleep).exactly(20).times
    http_mock.should_receive(:post).exactly(20).times.and_raise("error")
    expect { device.send "blah" }.to raise_error
  end
end
