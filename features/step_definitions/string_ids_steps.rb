When /^I call "(.*?)" with "(.*?)" on the target "(.*?)"$/ do |method, arg, target|
  @driver.send method, arg, :target => target
end

Then /^I should receive an id value back from the Brazenhead module$/ do
  @driver.last_response.body.to_i.should be > 0
end

