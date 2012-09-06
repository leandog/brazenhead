# -*- encoding: utf-8 -*-
require File.expand_path('../lib/gametel_driver/version', __FILE__)

Gem::Specification.new do |gem|
  gem.authors       = ["Levi Wilson", "Jeffrey S. Morgan"]
  gem.email         = ["levi@leandog.com", "jeff.morgan@leandog.com"]
  gem.description   = %q{Driver that accepts remote json requests and invokes methods inside Android emulator / device.}
  gem.summary       = %q{Driver that accepts remote json requests and invokes methods inside Android emulator / device.}
  gem.homepage      = "http://github.com/leandog/gametel-driver"

  gem.files         = `git ls-files`.split($\).grep(%r{^(lib|bin|test|spec|features)|(.*AndroidManifest\.xml)}) << 'driver/gametel_driver-release-unsigned.apk'
  gem.executables   = gem.files.grep(%r{^bin/}).map{ |f| File.basename(f) }
  gem.test_files    = gem.files.grep(%r{^(test|spec|features)/})
  gem.name          = "gametel_driver"
  gem.require_paths = ["lib"]
  gem.version       = GametelDriver::VERSION
  
  gem.add_dependency 'childprocess', '>= 0.3.5'
  
  gem.add_development_dependency 'rspec', '>= 2.11.0'
  gem.add_development_dependency 'cucumber', '>= 1.2.0'
  gem.add_development_dependency 'ADB'
end
