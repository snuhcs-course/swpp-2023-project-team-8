# frozen_string_literal: true

class SnuMailValidator < ActiveModel::EachValidator
  VALID_EMAIL_REGEX = /\A[\w+\-.]+@snu.ac.kr\z/i

  def validate_each(record, attribute, value)
    unless VALID_EMAIL_REGEX.match?(value)
      record.errors.add attribute, (options[:message] || "is not an email")
    end
  end
end
