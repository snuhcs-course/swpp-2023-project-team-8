# frozen_string_literal: true

require "simplecov"
require "simplecov-markdown"

SimpleCov.start "rails" do
  # Ignore channel, helpers, job

  add_filter "serializers"
  add_filter "channels"
  add_filter "helpers"
  add_filter "jobs"
  add_filter "lib/swpp" # Ignore proto gen files
end

SimpleCov.formatter = SimpleCov::Formatter::MarkdownFormatter
