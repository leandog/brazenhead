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

When /^I chain together the methods "(.*?)" and "(.*?)" using the target "(.*?)"$/ do |first_method, second_method, target|
  @driver = Driver.new
  @driver.chain_calls do |driver|
    driver.send first_method, :target => target
    driver.send second_method, :target => target
  end
end

Then /^I should receive a successful result from the GametelDriver module$/ do
  @driver.last_response.code.should == '200'
end

Then /^the result from the chained calls should be "(.*?)"$/ do |result|
  @driver.last_response.body.should == result
end

When /^I chain together the methods "(.*?)" and "(.*?)" on the GametelDriver module$/ do |first_method, second_method|
  @driver = Driver.new
  @driver.chain_calls do |driver|
    driver.send first_method
    driver.send second_method
  end
end
