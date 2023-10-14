# frozen_string_literal: true

module User::Authenticatable
  extend ActiveSupport::Concern

  included do
    before_create :generate_auth_token
  end

  def generate_auth_token
    self.auth_token = SecureRandom.hex(16)
  end
end
