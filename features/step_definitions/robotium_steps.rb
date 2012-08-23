When /^I do nothing but "(.*?)"$/ do |method|
  execute({:name => method})
end

Then /^I should receive "(.*?)"$/ do |json|
  last_response.body.should match json
end
