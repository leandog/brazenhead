class DemoApp
  include ADB

  def initialize
    @app = {:apk => 'features/support/ApiDemos.apk', :package => 'com.example.android.apis' }
    @test_app = {:apk => 'driver/bin/gametel_driver-debug.apk', :package => 'com.leandog.gametel.driver' }
  end

  def setup
    install_app
    start
    port_forward
  end

  def teardown
    [@app, @test_app].each do |app|
      puts "Removing #{app[:package]}..."
      uninstall app[:package]
    end
  end

  private
  def install_app
    [@app, @test_app].each do |app|
      puts "Installing #{app[:apk]}..."
      install app[:apk]
    end
  end

  def port_forward
    local = 'tcp:7777'
    host = 'tcp:54767'
    puts "Forwarding #{local} to #{host}..."
    forward local, host
  end

  def start
    shell "am instrument -e packageName #{@app[:package]} -e fullLauncherName #{@app[:package]}.ApiDemos -e class com.leandog.gametel.driver.TheTest #{@test_app[:package]}/com.leandog.gametel.driver.GametelInstrumentation"
    sleep 2
  end
end
