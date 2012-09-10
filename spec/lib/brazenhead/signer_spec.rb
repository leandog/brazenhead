class SignerTest
  include Brazenhead::Signer
end

describe Brazenhead::Signer do
  let(:signer) { SignerTest.new }
  let(:process) { double('brazenhead-process').as_null_object }
  let(:keypath) { '/path/to/debug.keystore' }

  before(:each) do
    Brazenhead::Process.stub(:new).and_return(process)
    process.stub(:last_stdout).and_return("")
    signer.stub(:default_key_path).and_return(keypath)
  end

  it "should give back the default keystore information" do
    expected = {:path => '/path/to/debug.keystore', :alias => 'androiddebugkey', :password => 'android', :keystore_password => 'android'}
    signer.default_keystore.should eq expected
  end

  it "should be able to sign a package" do
    expanded_keypath = "/expanded/#{keypath}"
    File.should_receive(:expand_path).with(keypath).and_return(expanded_keypath)
    process.should_receive(:run).with('jarsigner', '-sigalg', 'MD5withRSA', '-digestalg', 'SHA1', '-storepass', 'android', '-keypass', 'android', '-keystore', expanded_keypath, '/some_apk.apk', 'androiddebugkey')
    process.should_receive(:run).with('zipalign', '-v', '4', '/some_apk.apk', '/some_apk-signed.apk')
    signer.sign('/some_apk.apk', signer.default_keystore)
  end

  it "should raise if signing fails to verify" do
    process.stub(:last_stdout).and_return("some error")
    lambda { signer.sign('/some_apk.apk', signer.default_keystore) }.should raise_error(message="error signing /some_apk.apk (some error)")
  end

end
