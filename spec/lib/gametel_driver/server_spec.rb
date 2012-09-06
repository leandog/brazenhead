require 'spec_helper'

describe GametelDriver::Server do
  let(:server) { GametelDriver::Server.new }
  let(:manifest_info) { double('manifest-info').as_null_object }
  let(:tmpdir) { '/some/tmp/dir' }
  let(:apk) { 'some_apk.apk' }

  before(:each) do
    File.stub(:exists?).and_return(true)
    Dir.stub(:mktmpdir).and_yield(tmpdir)
    FileUtils.stub(:copy_file)
    File.stub(:read).and_return('')
    File.stub(:write)
    GametelDriver::ManifestInfo.stub(:new).with(apk).and_return(manifest_info)
  end

  context "validating the arguments" do
    it "should require that the package exists" do
      File.should_receive(:exists?).and_return(false)
      lambda { server.generate({:package => 'some_package.apk'}) }.should raise_error
    end
  end

  context "setting up the test server sandbox" do
    before(:each) do
      File.stub(:expand_path).with("../../../", anything()).and_return("/base/gem")
    end
    
    it "should use a temporary directory" do
      Dir.should_receive(:mktmpdir)
      server.generate(apk)
    end

    it "should copy the unsigned release package into the directory" do
      FileUtils.should_receive(:copy_file).with('/base/gem/driver/gametel_driver-release-unsigned.apk', tmpdir + '/gametel_driver-release-unsigned.apk')
      server.generate(apk)
    end

    it "should copy the manifest into the directory" do
      FileUtils.should_receive(:copy_file).with('/base/gem/driver/AndroidManifest.xml', tmpdir + '/AndroidManifest.xml')
      server.generate(apk)
    end
  end

  context "updating the manifest" do
    it "should load the contents of the existing manifest" do
      File.should_receive(:read).with("#{tmpdir}/AndroidManifest.xml")
      server.generate(apk)
    end

    it "should replace the target package" do
      the_target_package = "the.target.package"
      File.should_receive(:read).and_return("android:targetPackage=\"it.does.not.matter\"")
      manifest_info.should_receive(:package).and_return(the_target_package)
      File.should_receive(:write).with("#{tmpdir}/AndroidManifest.xml", "android:targetPackage=\"#{the_target_package}\"")

      server.generate(apk)
    end

  end
end
