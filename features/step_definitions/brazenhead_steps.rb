When /^I call the method "(.*?)" on the Brazenhead module$/ do |method_name|
  @driver.send method_name
end

When /^I call the method "(.*?)" on the Brazenhead module passing "(.*?)"$/ do |method_name, argument|
  @driver.send method_name, argument
end

When /^I chain together the methods "(.*?)" and "(.*?)" using the target "(.*?)"$/ do |first_method, second_method, target|
  @driver.chain_calls do |driver|
    driver.send first_method, :target => target
    driver.send second_method, :target => target
  end
end

Then /^I should receive a successful result from the Brazenhead module$/ do
  @driver.last_response.code.should == '200'
end

Then /^the result from the chained calls should match "(.*?)"$/ do |result|
  @driver.last_response.body.should match Regexp.new(result)
end

When /^I chain together the methods "(.*?)" and "(.*?)" on the Brazenhead module$/ do |first_method, second_method|
  @driver.chain_calls do |driver|
    driver.send first_method
    driver.send second_method
  end
end

When /^I call "(.*?)" passing the argument "(.*?)" and saving it into the variable "(.*?)"$/ do |method, argument, variable|
  @first_call = {:name => method, :arguments => argument, :variable => variable}
end

When /^then I call "(.*?)" using the variable "(.*?)" using the target "(.*?)"$/ do |method, argument, target|
  @driver.chain_calls do |driver|
    driver.send @first_call[:name], @first_call[:arguments], {:variable => @first_call[:variable]}
    driver.send method, argument, {:target => target}
  end
end

When /^then I call "(.*?)" using the variable "(.*?)" using the target "(.*?)" on the same driver$/ do |method, argument, target|
  @driver.chain_calls do |driver|
    driver.send @first_call[:name], @first_call[:arguments], {:variable => @first_call[:variable]}
    driver.send method, argument, {:target => target}
  end
end

Then /^I should see "(.*?)" from the Brazenhead module$/ do |value|
  @driver.search_text value
  @driver.last_response.body.should == 'true'
end
