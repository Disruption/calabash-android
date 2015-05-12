Then /^I select "([^\"]*)" from "([^\"]*)"$/ do |item_identifier, spinner_identifier|
  perform_action('select_spinner_item', item_identifier, spinner_identifier)
end