When /^I attempt to call a method that does not exist$/ do
  begin
    @driver.should_not_exist_at_all
  rescue Exception => e
    @last_exception = e
  end
end

Then /^I should receive an internal server error$/ do
  @last_exception.should_not be_nil
end

Then /^the exception should have detailed information$/ do
  json = JSON.parse(@last_exception.message)
  json["exception"].should match ".*CommandNotFoundException"
  json["errorMessage"].should match "The \\w+\\(.*\\) method was not found on .*Solo\\."
end
