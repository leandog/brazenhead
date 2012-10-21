When /^I call a method that returns a view$/ do
  @driver.chain_calls do |driver|
    driver.get_views
    driver.get 0
  end
end

Then /^we should have basic information about a view$/ do
  @driver.should have_succeeded

  response = @driver.json_response
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

When /^I call a method that returns a text view$/ do
  @driver.get_text 0
end

Then /^the text for the view should be returned$/ do
  @driver.json_response.should have_key "text"
end

When /^I call a method that returns an image view$/ do
  @driver.get_image 0
end

Then /^information about the image should be returned$/ do
  response = @driver.json_response
  response.should have_key "hasDrawable"
  response.should have_key "drawableRect"
end

