require 'spec_helper'
require 'json'

describe GametelDriver::Request do
  let(:request) { GametelDriver::Request.new }

  def json_message(message)
    JSON.parse(message[9..-1])
  end

  it "should place the method name in the json file" do
    message = request.build('call_me', [])
    json_message(message)[0]['name'].should == 'call_me'
  end

  it "should place an argument in the json file" do
    message = request.build('call_me', ['the_argument'])
    json_message(message)[0]['arguments'].should == ['the_argument']
  end

  it "should place multiple arguments in the json file" do
    message = request.build('call_me', ['first', 'second'])
    json_message(message)[0]['arguments'].should == ['first', 'second']
  end

  it "should default to having a target of LastResultOrRobotium" do
    message = request.build('call_me', [])
    json_message(message)[0]['target'].should == 'LastResultOrRobotium'
  end

  it "should use the target when one is provided" do
    message = request.build('call_me', [{:target => 'Robotium'}])
    json_message(message)[0]['target'].should == 'Robotium'
  end

  it "should grab the target from the final parameter" do
    message = request.build('call_me', ['the_argument', {:target => 'Robotium'}])
    json_message(message)[0]['target'].should == 'Robotium'
  end
end
