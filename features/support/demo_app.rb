class DemoApp
  include ADB

  def initialize
    @app = {:apk => 'features/support/ApiDemos.apk', :package => 'com.example.android.apis' }
    @test_app = {:apk => 'driver/bin/brazenhead-debug.apk', :package => 'com.leandog.brazenhead' }
  end

  def setup
    install_app
    port_forward
  end

  def teardown
    all_apks do |app|
      puts "Removing #{app[:package]}..."
      uninstall app[:package]
    end
  end

  def start_and_wait(wait=2)
    `adb shell am instrument -e packageName #{@app[:package]} -e fullLauncherName #{@app[:package]}.ApiDemos -e class com.leandog.brazenhead.TheTest #{@test_app[:package]}/com.leandog.brazenhead.BrazenheadInstrumentation`
    sleep wait
  end

  private
  def install_app
    all_apks do |app|
      puts "Installing #{app[:apk]}..."
      install app[:apk]
    end
  end

  def port_forward
    local = 'tcp:7777'
    host = 'tcp:54767'
    forward local, host
    puts "Forwarding #{local} to #{host}..."
  end

  def all_apks(&block)
    [@app, @test_app].each { |app| block.call app }
  end
end
