require 'spec_helper'

describe GametelDriver::CallAccumulator do
  let(:accumulator) { GametelDriver::CallAccumulator.new }

  it "should accumulate a series of calls and build the message" do
    accumulator.first_call
    accumulator.second_call
    accumulator.message.should == "commands=[{\"name\":\"firstCall\",\"target\":\"LastResultOrRobotium\"},{\"name\":\"secondCall\",\"target\":\"LastResultOrRobotium\"}]"
  end
end
