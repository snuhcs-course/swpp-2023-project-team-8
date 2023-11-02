# frozen_string_literal: true

require "rails_helper"

RSpec.describe User::Friendable do
  describe "#befriend" do
    let!(:user) { User.create!(email: "valid@snu.ac.kr", name: "test", password: "1234") }
    let!(:user2) { User.create!(email: "valid2@snu.ac.kr", name: "test2", password: "1234") }

    context "이미 친구일 경우" do
      it "친구가 될 수 없다" do
        user.befriend(user2)
        expect(Friendship.count).to eq(1)

        user2.befriend(user)
        expect(Friendship.count).to eq(1)
      end
    end
  end
end
