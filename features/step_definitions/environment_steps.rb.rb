When(/^the environment is missing ANDROID_HOME$/) do
  ENV.delete 'ANDROID_HOME'
end

Then(/^a Brazenhed::Environment error is raised about "([^"]*)"$/) do |expected_text|
  expect { @server.start :ApiDemos }.to raise_error(Brazenhead::Environment, /#{expected_text}/)
end