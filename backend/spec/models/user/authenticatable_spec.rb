# frozen_string_literal: true

require "rails_helper"

RSpec.describe User::Authenticatable do
  describe "before_create" do
    it "generates an auth_token" do
      user = User.new(
        email: "a@snu.ac.kr",
        password:"12345678",
        name:"홍길동",
      )
      expect(user.auth_token).to be_nil
      user.save!
      expect(user.auth_token).to be_present
    end
  end
end
