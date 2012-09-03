require 'spec_helper'

class Driver
  include GametelDriver
end

describe GametelDriver do

  let(:driver) { Driver.new }
  let(:http_mock) { double("http_mock") }
  
  context "when calling a method directly on the module" do
    before(:each) do
      Net::HTTP.stub(:new).and_return(http_mock)
    end
    
    it "should convert method name to camel case with first letter lowercase" do
      driver.should_receive(:call_method_on_driver).with("fooBar", [])
      driver.foo_bar
    end

    it "should make an http call passing the method name as the name" do
      Net::HTTP.should_receive(:new).and_return(http_mock)
      http_mock.should_receive(:post).with('/', "commands=[{\"name\":\"fooBar\"}]")
      driver.foo_bar
    end

    it "should make the result of the call available for inspection" do
      http_mock.should_receive(:post).with('/', "commands=[{\"name\":\"fooBar\"}]").and_return("Success")
      result = driver.foo_bar
      driver.last_response.should == result
    end
  end
end
