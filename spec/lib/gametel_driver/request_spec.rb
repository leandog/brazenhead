require 'spec_helper'
require 'json'

describe GametelDriver::Request do
  let(:request) { GametelDriver::Request.new }

  def json_message(message)
    JSON.parse(message[9..-1])[0]
  end

  it "should place the method name in the json file" do
    message = request.build('call_me', [])
    json_message(message)['name'].should == 'call_me'
  end

  it "should place an argument in the json file" do
    message = request.build('call_me', ['the_argument'])
    json_message(message)['arguments'].should == ['the_argument']
  end

  it "should place multiple arguments in the json file" do
    message = request.build('call_me', ['first', 'second'])
    json_message(message)['arguments'].should == ['first', 'second']
  end

  it "should use the target when one is provided" do
    message = request.build('call_me', [{:target => 'Robotium'}])
    json_message(message)['target'].should == 'Robotium'
  end

  it "should grab the target from the final parameter" do
    message = request.build('call_me', ['the_argument', {:target => 'Robotium'}])
    json_message(message)['target'].should == 'Robotium'
  end

  it "should use a variable when one is provided" do
    message = request.build('call_me', [{:variable => '@@var@@'}])
    json_message(message)['variable'].should == "@@var@@"
  end

  it "should grab the variable from the final parameter" do
    message = request.build('call_me', ['the_argument', {:variable => '@@var@@'}])
    json_message(message)['variable'].should == '@@var@@'
  end

  it "should find both the target and variable in the final hash parameter" do
    message = request.build('call_me', ['the_argument', {:target => 'Robotium', :variable => '@@var@@'}])
    json_message(message)['variable'].should == '@@var@@'
    json_message(message)['target'].should == 'Robotium'
  end
end
