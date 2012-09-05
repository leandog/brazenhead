require 'spec_helper'

describe GametelDriver::Server do
  let(:server) { GametelDriver::Server.new }
  let(:tmpdir) { '/some/tmp/dir' }

  before(:each) do
    File.stub(:exists?).and_return(true)
    Dir.stub(:mktmpdir).and_yield(tmpdir)
    File.stub(:copy_file)
  end

  context "validating the arguments" do
    it "should require a package argument" do
      lambda { server.generate({}) }.should raise_error
    end

    it "should require that the package exists" do
      File.should_receive(:exists?).and_return(false)
      lambda { server.generate({:package => 'some_package.apk'}) }.should raise_error
    end
  end

  context "setting up the test server sandbox" do
    let(:info) { {:package => 'some_apk.apk'} }

    it "should use a temporary directory" do
      Dir.should_receive(:mktmpdir)
      server.generate(info)
    end

    it "should copy the unsigned release package into the directory" do
      File.should_receive(:copy_file).with('../../gametel_driver-release-unsigned.apk', tmpdir)
      server.generate(info)
    end

    it "should copy the manifest into the directory" do
      File.should_receive(:copy_file).with('../../AndroidManifest.xml', tmpdir)
      server.generate(info)
    end
  end
end
