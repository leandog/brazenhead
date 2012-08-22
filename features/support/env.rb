$LOAD_PATH.unshift(File.join(File.dirname(__FILE__), '../../', 'lib'))
$LOAD_PATH.unshift(File.join(File.dirname(__FILE__), '../../', 'bin'))

require 'aruba/cucumber'
require 'gametel_driver'
require 'ADB'

World(ADB)

app = nil

AfterConfiguration do
  app = DemoApp.new
  app.setup
end

Before do
  connect
end

After do
  kill
end

at_exit do
  app.teardown
end
