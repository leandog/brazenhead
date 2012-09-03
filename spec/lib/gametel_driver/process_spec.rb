require 'spec_helper'

describe Process do
  before(:each) do
    @running_process = double('process')
    @running_process.stub(:wait)

    Time.stub(:now) { 'this-time' }
    Tempfile.stub(:new) { nil }

    @io = double('io')
    @io.stub(:stdout=)
    @io.stub(:stderr=)
    @running_process.stub(:io) { @io }

    ChildProcess.stub(:build) { @running_process }
    @process = GametelDriver::Process.new
  end

  it "it runs the command with arguments" do
    ChildProcess.should_receive(:build).with('command', 'some', 'argument')
    @process.run('command', 'some', 'argument')
  end

  it "should wait for the command to complete" do
    @running_process.should_receive(:wait)
    @process.run('anything')
  end

  it "should capture stdout" do
    out = double('stdout')
    Tempfile.should_receive(:new).with('gametel-proc-out-this-time').and_return(out)
    @process.run('anything')
  end

  it "should capture stderr" do
    err = double('stderr')

    Tempfile.should_receive(:new).with('gametel-proc-err-this-time').and_return(err)
    @process.run('anything')
  end
end
