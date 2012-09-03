class Driver
  include GametelDriver
end

When /^I call the method "(.*?)" on the GametelDriver module$/ do |method_name|
  @driver = Driver.new
  @driver.send method_name
end

When /^I call the method "(.*?)" on the GametelDriver module passing "(.*?)"$/ do |method_name, argument|
  @driver = Driver.new
  @driver.send method_name, argument
end

Then /^I should receive a successful result from the GametelDriver module$/ do
  @driver.last_response.code.should == '200'
end
