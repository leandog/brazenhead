require 'brazenhead/builder'

describe Brazenhead::Builder do
  let(:apk) { 'some_apk.apk' }
  let(:activity) { 'SomeActivityToStart' }
  let(:server) { Brazenhead::Builder.new }
  let(:manifest_info) { double('manifest-info').as_null_object }
  let(:tmpdir) { '/some/tmp/dir' }
  let(:driver_apk) { 'brazenhead-release-unsigned.apk' }
  let(:keystore) { {:path => 'default.keystore'} }
  let(:process) { double('brazenhead-process').as_null_object }

  before(:each) do
    File.stub(:expand_path) do |arg|
      arg
    end
    File.stub(:exists?).and_return(true)
    Dir.stub(:mktmpdir).and_yield(tmpdir)
    Dir.stub(:mkdir)
    Dir.stub(:chdir).and_yield(tmpdir)
    FileUtils.stub(:copy_file)
    File.stub(:read).and_return('')
    File.stub(:write)
    server.stub(:update_manifest)
    server.stub(:sign)
    server.stub(:install)
    Brazenhead::ManifestInfo.stub(:new).with(apk).and_return(manifest_info)
    Brazenhead::Process.stub(:new).and_return(process)
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
        FileUtils.should_receive(:copy_file).with(base_test_apk, "#{tmpdir}/#{driver_apk}")
        server.build_for(apk, keystore)
      end

      it "should copy the manifest into the directory" do
        FileUtils.should_receive(:copy_file).with(base_manifest,  "#{tmpdir}/#{manifest}")
        server.build_for(apk, keystore)
      end
    end

    context "grabbing resource information" do
      it "should retrieve resource information from the target package" do
        process.should_receive(:run).with(*"aapt dump resources #{apk}".split)
        server.build_for apk, keystore
      end

      it "should create an assets directory for the resource information" do
        Dir.should_receive(:mkdir).with("assets")
        server.build_for apk, keystore
      end

      it "should write the resource information to the assets directory" do
        process.should_receive(:last_stdout).and_return("resource info")
        resources = "assets/resources.txt"
        File.should_receive(:write).with(resources, "resource info")
        server.build_for apk, keystore
      end

      it "should store the resources in the test server" do
        process.should_receive(:run).with(*"aapt add #{driver_apk} assets/resources.txt".split)
        server.build_for apk, keystore
      end
    end

    context "updating the manifest" do
      it "should load the contents of the existing manifest" do
        File.should_receive(:read).with("#{tmpdir}/AndroidManifest.xml")
        server.build_for(apk, keystore)
      end

      it "should replace the target package" do
        the_target_package = "the.target.package"
        File.should_receive(:read).and_return("android:targetPackage=\"it.does.not.matter\"")
        manifest_info.should_receive(:package).and_return(the_target_package)
        File.should_receive(:write).with("#{tmpdir}/AndroidManifest.xml", "android:targetPackage=\"#{the_target_package}\"")

        server.build_for(apk, keystore)
      end

      it "should package the modified manifest back into the test package" do 
        manifest_info.should_receive(:target_sdk).and_return(10)
        server.should_receive(:update_manifest).with("#{driver_apk}", "#{tmpdir}/AndroidManifest.xml", 10)
        server.build_for(apk, keystore)
      end

    end

    context "signing the test server" do
      it "should use the provided keystore to sign the package" do
        server.should_receive(:sign).with("#{driver_apk}", {:path => 'another keystore'})
        server.build_for(apk, :path => 'another keystore')
      end
    end

    context "installing the test server" do
      it "should reinstall the test server to the device" do
        server.should_receive(:install).with("#{driver_apk}", "-r", {}, 90)
        server.build_for(apk, keystore)
      end

      it "should reinstall the target package to the device" do
        server.should_receive(:install).with(apk, "-r", {}, 90)
        server.build_for(apk, keystore)
      end

    end
  end

  context "sending back informationa about the server" do
    it "should send back information about the target package" do
      server.build_for(apk, keystore).should_be manifest_info
    end
  end
end
