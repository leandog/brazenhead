When /^I call a method that returns a view$/ do
  @driver.chain_calls do |driver|
    driver.get_views
    driver.get 0
  end
end

Then /^we should have basic information about a view$/ do
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

