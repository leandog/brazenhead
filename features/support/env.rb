$LOAD_PATH.unshift(File.join(File.dirname(__FILE__), '../../', 'lib'))

require 'brazenhead'
require 'brazenhead/server'
require 'ADB'
require 'childprocess'

World(ADB)

keystore = {
  :path => 'features/support/debug.keystore',
  :alias => 'androiddebugkey',
  :password => 'android',
  :keystore_password => 'android'
}

server = Brazenhead::Server.new('features/support/ApiDemos.apk', keystore)

class Driver
  include Brazenhead
end

Before do
  @driver = Driver.new
  server.start("ApiDemos")
end

After do
  server.stop
end
