When /^I attempt to call a method that does not exist$/ do
  execute({:name => 'shouldNotExistAtAll' })
end

Then /^I should receive an internal server error$/ do
  last_response.code.should eq '500'
end

Then /^the exception should have detailed information$/ do
  json = JSON.parse(last_response.body)
  json["exception"].should match ".*CommandNotFoundException"
  json["errorMessage"].should match "The \\w+\\(.*\\) method was not found on .*Solo\\."
end
