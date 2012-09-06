require 'spec_helper'

describe Brazenhead::CallAccumulator do
  let(:accumulator) { Brazenhead::CallAccumulator.new }

  it "should accumulate a series of calls and build the message" do
    accumulator.first_call
    accumulator.second_call
    accumulator.message.should == "commands=[{\"name\":\"firstCall\"},{\"name\":\"secondCall\"}]"
  end
end
