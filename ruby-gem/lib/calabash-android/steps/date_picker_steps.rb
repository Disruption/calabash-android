
Given /^I set the date to "(\d\d-\d\d-\d\d\d\d)" on DatePicker with index ([^\"]*)$/ do |date, index|
  perform_action('set_date_with_index', index.to_i-1, date)
end

Given /^I set the "([^\"]*)" date to "(\d\d-\d\d-\d\d\d\d)"$/ do |content_description, date|
  perform_action('set_date_with_description', content_description, date)
end