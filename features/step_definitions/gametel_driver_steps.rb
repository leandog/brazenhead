class Driver
  include GametelDriver
end

When /^I call the method "(.*?)" on the GametelDriver module$/ do |method_name|
  @driver = Driver.new
  @driver.send method_name
end

Then /^I should receive a successful result from the GametelDriver module$/ do
  @driver.last_response.code.should == '200'
end
