Then /^I toggle checkbox number (\d+)$/ do |index|
	perform_action('toggle_checkbox_with_index', index.to_i-1)
end
