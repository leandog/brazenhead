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

When /^I get the first "(.*?)" View I find$/ do |viewType|
  execute({:name => "get#{viewType}", :arguments => [0]})
end

Then /^the view should have some basic information$/ do
  response = JSON.parse(last_response.body)
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

When /^I call "(.*?)" and then I call "(.*?)"$/ do |firstMethod, nextMethod|
  execute({:name => firstMethod}, {:name => nextMethod})
end

Then /^the result should be "(.*?)"$/ do |result|
  last_response.body.should eq result
end

When /^I want to save the view with the text "(.*?)" in the variable "(.*?)"$/ do |text, var_name|
  @commands = []
  @commands << {:name => 'getText', :arguments => [text], :variable => var_name}
end

Then /^I should be able to pass "(.*?)" to "(.*?)" on "(.*?)"$/ do |var_name, method, target|
  @commands << {:name => method, :target => target, :arguments => [var_name]}
  execute(*@commands)
  last_response.code.should eq '200'
end

Then /^I should see "(.*?)"$/ do |text|
  execute({:name => 'searchText', :arguments => [text]})
  last_response.body.should eq 'true'
end

