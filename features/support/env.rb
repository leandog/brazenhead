$LOAD_PATH.unshift(File.join(File.dirname(__FILE__), '../../', 'lib'))

require 'brazenhead'
require 'brazenhead/server'
require 'ADB'
require 'childprocess'

World(ADB)

server = Brazenhead::Server.new('features/support/ApiDemos.apk')

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
