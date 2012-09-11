# -*- encoding: utf-8 -*-
require File.expand_path('../lib/brazenhead/version', __FILE__)

Gem::Specification.new do |gem|
  gem.authors       = ["Jeffrey S. Morgan", "Levi Wilson"]
  gem.email         = ["jeff.morgan@leandog.com", "levi@leandog.com"]
  gem.description   = %q{Driver that accepts remote json requests and invokes methods inside Android emulator / device.}
  gem.summary       = %q{Driver that accepts remote json requests and invokes methods inside Android emulator / device.}
  gem.homepage      = "http://github.com/leandog/brazenhead"

  gem.files         = `git ls-files`.split($\).grep(%r{^(lib|bin|test|spec|features)|(.*AndroidManifest\.xml)}) << 'driver/brazenhead-release-unsigned.apk'
  gem.executables   = gem.files.grep(%r{^bin/}).map{ |f| File.basename(f) }
  gem.test_files    = gem.files.grep(%r{^(test|spec|features)/})
  gem.name          = "brazenhead"
  gem.require_paths = ["lib"]
  gem.version       = Brazenhead::VERSION
  
  gem.add_dependency 'childprocess', '>= 0.3.5'
  
  gem.add_development_dependency 'rspec', '>= 2.11.0'
  gem.add_development_dependency 'cucumber', '>= 1.2.0'
  gem.add_development_dependency 'ADB', '>= 0.5.1'
end
