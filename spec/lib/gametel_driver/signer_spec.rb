class SignerTest
  include GametelDriver::Signer
end

describe GametelDriver::Signer do
  let(:signer) { SignerTest.new }
  let(:process) { double('gametel-process') }

  before(:each) do
    GametelDriver::Process.stub(:new).and_return(process)
    signer.stub(:default_key_path).and_return('/path/to/debug.keystore')
  end

  it "should give back the default keystore information" do
    expected = {:path => '/path/to/debug.keystore', :alias => 'androiddebugkey', :password => 'android', :keystore_password => 'android'}
    signer.default_keystore.should eq expected
  end

  it "should be able to sign a package" do
    process.should_receive(:run).with('jarsigner', '-verbose', '-storepass', 'android', '-keypass', 'android', '-keystore', '/path/to/debug.keystore', '/some_apk.apk', 'androiddebugkey')
    signer.sign('/some_apk.apk', signer.default_keystore)
  end

end
