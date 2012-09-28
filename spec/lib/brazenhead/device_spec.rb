require 'spec_helper'

describe Brazenhead::Device do
  let(:device) { Brazenhead::Device.new }
  let(:http_mock) { double("http_mock") }
  let(:http_response) { double("http_response") }

  before(:each) do
    Net::HTTP.stub(:new).and_return(http_mock)
    device.stub(:sleep)
    http_mock.stub(:post).and_return(http_response)
    http_response.stub(:code).and_return('200')
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

  it "should be able to stop the server" do
    http_mock.should_receive(:post).with('/kill', '')
    device.stop
  end

  it "should raise if the status code is not ok" do
    http_response.should_receive(:code).and_return('500')
    http_response.stub(:body).and_return("the error message")
    expect { device.send "blah" }.to raise_error(Exception, "the error message")
  end

  context "json responses" do
    it "should be able to be returned" do
      http_response.should_receive(:body).and_return("{\"some_actual\": \"json object\"}")
      device.send ""
      device.last_json.should eq({"some_actual" => "json object"})
    end

    it "should be able to send back strings as normal strings" do
      http_response.should_receive(:body).and_return("\"string only\"")
      device.send ""
      device.last_json.should eq("string only")
    end

    it "should be able to return numbers only" do
      http_response.should_receive(:body).and_return("123")
      device.send ""
      device.last_json.should eq(123)
    end

    it "should be able to return boolean values" do
      http_response.should_receive(:body).and_return("false")
      device.send ""
      device.last_json.should be_false
    end
  end

end
