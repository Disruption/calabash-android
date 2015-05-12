Then /^I enter "([^\"]*)" as "([^\"]*)"$/ do |text, content_description|
  perform_action('enter_text_for_description', text, content_description)
end

Then /^I enter "([^\"]*)" into "([^\"]*)"$/ do |text, content_description|
  perform_action('enter_text_for_description', text, content_description)
end

Then /^I enter "([^\"]*)" into input field number (\d+)$/ do |text, index|
  perform_action('enter_text_for_index', text, index)
end

Then /^I enter text "([^\"]*)" into field with id "([^\"]*)"$/ do |text, id|
  perform_action('enter_text_for_id', text, id)
end

Then /^I clear "([^\"]*)"$/ do |identifier|
  perform_action('clear_text_for_marked', text, identifier)
end

Then /^I clear input field number (\d+)$/ do |index|
  perform_action('clear_text_for_index', index)
end

Then /^I clear input field with id "([^\"]*)"$/ do |id|
  perform_action('clear_text_for_id', id)
end
