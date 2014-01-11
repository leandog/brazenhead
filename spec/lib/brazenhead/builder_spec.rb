require 'brazenhead/builder'

describe Brazenhead::Builder do
  let(:apk) { 'some_apk.apk' }
  let(:expanded_apk) { File.expand_path(apk) }
  let(:activity) { 'SomeActivityToStart' }
  let(:server) { Brazenhead::Builder.new }
  let(:manifest_info) { double('manifest-info').as_null_object }
  let(:tmpdir) { '/some/tmp/dir' }
  let(:driver_apk) { 'brazenhead-release-unsigned.apk' }
  let(:keystore) { {:path => 'default.keystore'} }
  let(:process) { double('brazenhead-process').as_null_object }

  before(:each) do
    File.stub(:expand_path) do |arg|
      "expanded/#{arg}"
    end
    File.stub(:exists?).and_return(true)
    Dir.stub(:mktmpdir).and_yield(tmpdir)
    Dir.stub(:chdir).and_yield(tmpdir)
    Dir.stub(:mkdir)
    Dir.stub(:pwd).and_return(tmpdir)
    FileUtils.stub(:cp)
    File.stub(:read).and_return('')
    File.stub(:write)
    stub_all server, :update_manifest, :sign, :install, :uninstall

    manifest_info.stub(:package).and_return('com.test.package')
    Brazenhead::ManifestInfo.stub(:new).with(expanded_apk).and_return(manifest_info)
    Brazenhead::Process.stub(:new).and_return(process)
  end

  def stub_all(type, *methods)
    methods.each do |method|
      type.stub(method)
    end
  end

  context "building the test server" do
    context "validating the arguments" do
      it "should require that the package exists" do
        File.should_receive(:exists?).and_return(false)
        lambda { server.build_for('some_package.apk', keystore) }.should raise_error(message="Invalid package path:  some_package.apk")
      end
    end

    context "setting up the test server sandbox" do
      let(:base_gem_dir) { '/base/gem' }
      let(:base_test_apk) { "#{base_gem_dir}/driver/#{driver_apk}" }
      let(:manifest) { 'AndroidManifest.xml' }
      let(:base_manifest) { "#{base_gem_dir}/driver/#{manifest}" }

      before(:each) do
        File.stub(:expand_path).with("~/.android/debug.keystore").and_return("expanded/.android/debug.keystore")
        File.stub(:expand_path).with("../../../", anything()).and_return(base_gem_dir)
      end

      it "should use a temporary directory" do
        Dir.should_receive(:mktmpdir)
        server.build_for(apk, keystore)
      end

      it "should copy the unsigned release package into the directory" do
        FileUtils.should_receive(:cp).with(base_test_apk, "#{tmpdir}")
        server.build_for(apk, keystore)
      end

      it "should copy the manifest into the directory" do
        FileUtils.should_receive(:cp).with(base_manifest,  "#{tmpdir}")
        server.build_for(apk, keystore)
      end
    end

    context "updating the manifest" do
      it "should load the contents of the existing manifest" do
        File.should_receive(:read).with("AndroidManifest.xml")
        server.build_for(apk, keystore)
      end

      it "should replace the target package" do
        the_target_package = "the.target.package"
        File.should_receive(:read).and_return("android:targetPackage=\"it.does.not.matter\"")
        manifest_info.should_receive(:package).and_return(the_target_package)
        File.should_receive(:write).with("AndroidManifest.xml", "android:targetPackage=\"#{the_target_package}\"")

        server.build_for(apk, keystore)
      end

      it "should base our package off of theirs" do
        the_target_package = "the.target.package"
        File.should_receive(:read).and_return("ignore the first", "package=\"this.will.be.replaced\"")
        manifest_info.should_receive(:package).and_return(the_target_package)
        File.should_receive(:write).with("AndroidManifest.xml", "ignore the first")

        File.should_receive(:write).with("AndroidManifest.xml", "package=\"#{the_target_package}.brazenhead\"")
        server.build_for(apk, keystore)
      end

      it "should package the modified manifest back into the test package" do 
        manifest_info.should_receive(:target_sdk).and_return(10)
        server.should_receive(:update_manifest).with("#{driver_apk}", "AndroidManifest.xml", 10)
        server.build_for(apk, keystore)
      end

    end

    context "signing the test server" do
      it "should use the provided keystore to sign the package" do
        server.should_receive(:sign).with("#{driver_apk}", {:path => 'expanded/another keystore'})
        server.build_for(apk, :path => 'another keystore')
      end
    end

    context "installing the test server" do
      it "should reinstall the test server to the device" do
        server.should_receive(:uninstall).with('com.test.package.brazenhead', {}, 90)
        server.should_receive(:install).with(driver_apk, nil, {}, 90)
        server.build_for(apk, keystore)
      end

      it "should reinstall the target package to the device" do
        server.should_receive(:uninstall).with('com.test.package', {}, 90)
        server.should_receive(:install).with(expanded_apk, nil, {}, 90)
        server.build_for(apk, keystore)
      end

      it 'silently fails if the package is not already there' do
        server.should_receive(:uninstall).and_raise ADB::ADBError
        expect { server.build_for(apk, keystore) }.to_not raise_error ADB::ADBError
      end

    end
  end

  context "sending back information about the server" do
    it "should send back information about the target package" do
      server.build_for(apk, keystore).should_be manifest_info
    end
  end
end
