describe Process do
  before(:each) do
    @running_process = double('process')
    @running_process.stub(:wait)
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
end
