$LOAD_PATH.unshift(File.join(File.dirname(__FILE__), '../../', 'lib'))
$LOAD_PATH.unshift(File.join(File.dirname(__FILE__), '../../', 'bin'))

require 'aruba/cucumber'
require 'gametel_driver'
require 'ADB'
require 'childprocess'
require_relative 'demo_app'

World(ADB)

app = DemoApp.new
app.setup

Before do
  app.start_and_wait
  connect unless connected?
end

After do
  kill
end

at_exit do
  app.teardown
end


