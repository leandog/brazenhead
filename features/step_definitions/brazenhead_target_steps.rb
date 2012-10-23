When /^I call "(.*?)" with "(.*?)" on the target "(.*?)"$/ do |method, arg, target|
  @driver.send method, arg, :target => target
end

Then /^I should receive an id value back from the Brazenhead module$/ do
  @driver.last_response.body.to_i.should be > 0
end

Given /^I'm on the controls screen$/ do
  @navigation.controls_screen
end

Given /^I'm on the lists screen$/ do
  @navigation.lists_screen
end

Given /^I'm on the custom lists screen$/ do
  @navigation.custom_lists_screen
end

Given /^I'm on the views list$/ do
  @navigation.view_list
end

Given /^I'm on the arrays list$/ do
  @navigation.arrays_list
end

Given /^I'm on the image view screen$/ do
  @navigation.image_view_screen
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

When /^I select the list item that contains "(.*?)"$/ do |item_text|
  @driver.list_item_by_text item_text, :target => 'Brazenhead'
end

Then /^the found list item should be a "(.*?)"$/ do |class_type|
  @driver.last_json["classType"].should eq(class_type)
end

Then /^I can select list item "(.*?)" even if it is off of the screen$/ do |index|
  @driver.press_list_item_by_index index.to_i, :target => 'Brazenhead'
  @driver.last_response.code.should eq('200')
end

Then /^I should be on the "(.*?)" activity$/ do |activity|
  @driver.chain_calls do |device|
    device.get_current_activity
    device.get_class
    device.to_string
  end
  @driver.last_json.should match(activity)
end

When /^I select the list item at index "(.*?)"$/ do |index|
  @driver.list_item_by_index index.to_i, :target => 'Brazenhead'
end

Then /^the text of the found list item should be "(.*?)"$/ do |text|
  @driver.last_json['text'].should eq(text)
end

