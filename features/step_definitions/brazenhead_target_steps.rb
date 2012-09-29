When /^I call "(.*?)" with "(.*?)" on the target "(.*?)"$/ do |method, arg, target|
  @driver.send method, arg, :target => target
end

Then /^I should receive an id value back from the Brazenhead module$/ do
  @driver.last_response.body.to_i.should be > 0
end

Given /^I'm on the controls screen$/ do
  @driver.click_on_text "Views"
  @driver.click_on_text "Controls"
  @driver.click_on_text "Light Theme"
end

When /^I select item "(.*?)" from the spinner with id "(.*?)"$/ do |index, id|
  @driver.chain_calls do |device|
    device.id_from_name id, :variable => '@@the_id@@', :target => 'Brazenhead'
    device.press_spinner_item_by_id '@@the_id@@', index.to_i, :target => 'Brazenhead'
  end
end

When /^I select item "(.*?)" from the spinner view with id "(.*?)"$/ do |index, id|
  @driver.chain_calls do |device|
    device.id_from_name id, :variable => '@@the_id@@', :target => 'Brazenhead'
    device.get_view '@@the_id@@', :variable => '@@the_view@@', :target => 'Robotium'
    device.press_spinner_item '@@the_view@@', index.to_i, :target => 'Brazenhead'
  end
end

Then /^the text "(.*?)" is selected in the spinner$/ do |spinner_text|
  @driver.is_spinner_text_selected(spinner_text).should be_true
end
