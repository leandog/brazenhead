class DemoApp
  include ADB

  def initialize
    @app = {:apk => 'features/support/ApiDemos.apk', :package => 'com.example.android.apis' }
    @test_app = {:apk => 'driver/bin/gametel_driver-debug.apk', :package => 'com.leandog.gametel.driver' }
  end

  def setup
    install_app
    port_forward
  end

  def teardown
    all_apks do |app|
      puts "Removing #{app[:package]}..."
      `adb uninstall #{app[:package]}`
    end
  end

  def start_and_wait(wait=1)
    `adb shell am instrument -e packageName #{@app[:package]} -e fullLauncherName #{@app[:package]}.ApiDemos -e class com.leandog.gametel.driver.TheTest #{@test_app[:package]}/com.leandog.gametel.driver.GametelInstrumentation`
    sleep wait
  end

  private
  def install_app
    all_apks do |app|
      puts "Installing #{app[:apk]}..."
      `adb install #{app[:apk]}`
    end
  end

  def port_forward
    local = 'tcp:7777'
    host = 'tcp:54767'
    `adb forward #{local} #{host}`
    puts "Forwarding #{local} to #{host}..."
  end

  def all_apks(&block)
    [@app, @test_app].each do |app|
      block.call app
    end
  end
end
