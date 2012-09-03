require 'spec_helper'

describe Process do
  before(:each) do
    @running_process = double('process')
    @running_process.stub(:wait)

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

  it "it redirects stdout and stderr" do
    out = double('stdout')
    err = double('stderr')
    Time.should_receive(:now).and_return('this-time', 'next-time')
    Tempfile.should_receive(:new).with('gametel-proc-out-this-time').and_return(out)
    Tempfile.should_receive(:new).with('gametel-proc-err-next-time').and_return(err)
    @io.should_receive(:stdout=).with(out)
    @io.should_receive(:stderr=).with(err)
    @process.run('anything')
  end
end
