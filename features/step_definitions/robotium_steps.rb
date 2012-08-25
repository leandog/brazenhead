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
  expected = {
    "id"=>16908310, 
    "classType"=>"android.widget.TextView", 
    "width"=>540, 
    "height"=>38, 
    "screenLocation"=>[0, 38], 
    "windowLocation"=>[0, 38], 
    "left"=>0, 
    "top"=>0, 
    "right"=>540, 
    "bottom"=>38
  }

  JSON.parse(last_response.body).should eq expected
end
    
