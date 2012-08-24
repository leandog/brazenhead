When /^I do nothing but "(.*?)"$/ do |method|
  execute({:name => method})
end

Then /^I should receive "(.*?)"$/ do |json|
  last_response.body.should match json
end

When /^I call a method with an integer$/ do
  execute({:name => 'scrollUpList', :arguments => [0]})
end

When /^I call a method with a string$/ do 
  execute({:name => 'searchButton', :arguments => ['will not find']})
end

When /^I call a method with a float$/ do 
  execute({:name => 'clickOnScreen', :arguments => [100.0, 100.5]})
end

When /^I call a method with a boolean$/ do 
  execute({:name => 'searchText', :arguments => ['Views', true]})
end

Then /^I should receive a successful result$/ do
  last_response.code.should eq '200'
end
