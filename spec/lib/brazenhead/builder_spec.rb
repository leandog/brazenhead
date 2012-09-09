require 'brazenhead/builder'

describe Brazenhead::Builder do
  let(:apk) { 'some_apk.apk' }
  let(:activity) { 'SomeActivityToStart' }
  let(:server) { Brazenhead::Builder.new }
  let(:manifest_info) { double('manifest-info').as_null_object }
  let(:tmpdir) { '/some/tmp/dir' }
  let(:driver_apk) { 'brazenhead-release-unsigned.apk' }

  before(:each) do
    File.stub(:exists?).and_return(true)
    Dir.stub(:mktmpdir).and_yield(tmpdir)
    FileUtils.stub(:copy_file)
    File.stub(:read).and_return('')
    File.stub(:write)
    server.stub(:update_manifest)
    server.stub(:sign_default)
    server.stub(:install)
    Brazenhead::ManifestInfo.stub(:new).with(apk).and_return(manifest_info)
  end

  context "building the test server" do
    context "validating the arguments" do
      it "should require that the package exists" do
        File.should_receive(:exists?).and_return(false)
        lambda { server.build_for('some_package.apk') }.should raise_error
      end
    end

    context "setting up the test server sandbox" do
      let(:base_gem_dir) { '/base/gem' }
      let(:base_test_apk) { "#{base_gem_dir}/driver/#{driver_apk}" }
      let(:manifest) { 'AndroidManifest.xml' }
      let(:base_manifest) { "#{base_gem_dir}/driver/#{manifest}" }

      before(:each) do
        File.stub(:expand_path).with("../../../", anything()).and_return(base_gem_dir)
      end

      it "should use a temporary directory" do
        Dir.should_receive(:mktmpdir)
        server.build_for(apk)
      end

      it "should copy the unsigned release package into the directory" do
        FileUtils.should_receive(:copy_file).with(base_test_apk, File.join(tmpdir, driver_apk))
        server.build_for(apk)
      end

      it "should copy the manifest into the directory" do
        FileUtils.should_receive(:copy_file).with(base_manifest, File.join(tmpdir, manifest))
        server.build_for(apk)
      end
    end

    context "updating the manifest" do
      it "should load the contents of the existing manifest" do
        File.should_receive(:read).with("#{tmpdir}/AndroidManifest.xml")
        server.build_for(apk)
      end

      it "should replace the target package" do
        the_target_package = "the.target.package"
        File.should_receive(:read).and_return("android:targetPackage=\"it.does.not.matter\"")
        manifest_info.should_receive(:package).and_return(the_target_package)
        File.should_receive(:write).with("#{tmpdir}/AndroidManifest.xml", "android:targetPackage=\"#{the_target_package}\"")

        server.build_for(apk)
      end

      it "should package the modified manifest back into the test package" do 
        manifest_info.should_receive(:min_sdk).and_return(10)
        server.should_receive(:update_manifest).with("#{tmpdir}/#{driver_apk}", "#{tmpdir}/AndroidManifest.xml", 10)
        server.build_for(apk)
      end

    end

    context "signing the test server" do
      it "should sign the test packge with the default keystore" do
        server.should_receive(:sign_default).with("#{tmpdir}/#{driver_apk}")
        server.build_for(apk)
      end
    end

    context "installing the test server" do
      it "should reinstall the test server to the device" do
        server.should_receive(:install).with("#{tmpdir}/#{driver_apk}", "-r")
        server.build_for(apk)
      end

      it "should reinstall the target package to the device" do
        server.should_receive(:install).with(apk, "-r")
        server.build_for(apk)
      end

    end
  end

  context "sending back informationa about the server" do
    it "should send back information about the target package" do
      server.build_for(apk).should_be manifest_info
    end
  end
end
