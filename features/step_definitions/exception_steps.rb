When /^I attempt to call a method that does not exist$/ do
  @driver.should_not_exist_at_all
end

Then /^I should receive an internal server error$/ do
  @driver.last_response.code.should eq '500'
end

Then /^the exception should have detailed information$/ do
  json = JSON.parse(@driver.last_response.body)
  json["exception"].should match ".*CommandNotFoundException"
  json["errorMessage"].should match "The \\w+\\(.*\\) method was not found on .*Solo\\."
end
