Then /^I see the text "([^\"]*)"$/ do |text|
  perform_action('view_with_text_is_displayed', text, timeout: 10)
end

Then /^I see "([^\"]*)"$/ do |text|
  perform_action('view_with_text_is_displayed', text, timeout: 10)
end

Then /^I should see "([^\"]*)"$/ do |text|
  perform_action('view_with_text_is_displayed', text, timeout: 10)
end

Then /^I should see text containing "([^\"]*)"$/ do |text|
  perform_action('view_with_text_is_displayed', text, timeout: 10)
end



Then /^I should not see "([^\"]*)"$/ do |text|
  perform_action('view_with_text_is_not_displayed', text, timeout: 10)
end

Then /^I don't see the text "([^\"]*)"$/ do |text|
  perform_action('view_with_text_is_not_displayed', text, timeout: 10)
end

Then /^I don't see "([^\"]*)"$/ do |text|
  perform_action('view_with_text_is_not_displayed', text, timeout: 10)
end
