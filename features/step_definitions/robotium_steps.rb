When /^I do nothing but "(.*?)"$/ do |method|
  @driver.send method
end

Then /^I should receive "(.*?)"$/ do |json|
  @driver.last_response.body.should match json
end

When /^I call a method with an integer$/ do
  @driver.scroll_up_list(0)
end

When /^I call a method with a string$/ do 
  @driver.search_button('will not find')
end

When /^I call a method with a long$/ do
  @driver.wait_for_text('Views', 1, 5000)
end

When /^I call a method with a float$/ do 
  @driver.click_on_screen(100.0, 100.0)
end

When /^I call a method with a boolean$/ do 
  @driver.search_text('Views', true)
end

Then /^I should receive a successful result$/ do
  @driver.last_response.code.should eq '200'
end

When /^I call "(.*?)" and then I call "(.*?)"$/ do |first_method, next_method|
  @driver.chain_calls do |driver|
    driver.send first_method
    driver.send next_method
  end
end

Then /^the result should be "(.*?)"$/ do |result|
  @driver.last_response.body.should eq result
end

Then /^I should see "(.*?)"$/ do |text|
  @driver.search_text text
  @driver.last_json.should be_true
end

