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

When /^I call a method with a float$/ do 
  @driver.click_on_screen(100.0, 100.0)
end

When /^I call a method with a boolean$/ do 
  @driver.search_text('Views', true)
end

Then /^I should receive a successful result$/ do
  @driver.last_response.code.should eq '200'
end

When /^I get the first "(.*?)" View I find$/ do |view_type|
  @driver.send "get_#{view_type.downcase}", 0
end

Then /^the view should have some basic information$/ do
  response = JSON.parse(@driver.last_response.body)
  response.should have_key "id"
  response.should have_key "classType"
  response.should have_key "width"
  response.should have_key "height"
  response.should have_key "screenLocation"
  response.should have_key "windowLocation"
  response.should have_key "left"
  response.should have_key "top"
  response.should have_key "right"
  response.should have_key "bottom"
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
  @last_response.body.should eq 'true'
end

