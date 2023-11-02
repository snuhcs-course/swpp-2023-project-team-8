# frozen_string_literal: true

require "rails_helper"

RSpec.describe User::Friendable do
  let!(:user) { User.create!(email: "valid@snu.ac.kr", name: "test", password: "1234") }
  let!(:user2) { User.create!(email: "valid2@snu.ac.kr", name: "test2", password: "1234") }

  describe "#befriend" do
    context "이미 친구일 경우" do
      it "친구가 될 수 없다" do
        user.befriend(user2)
        expect(Friendship.count).to eq(1)

        user2.befriend(user)
        expect(Friendship.count).to eq(1)
      end
    end
  end

  describe "#confirm_friendship" do
    it "confirm 확인을 할 수 있다" do
      user2.befriend(user)

      expect(user.requested_friendships.first.confirmed?).to be_falsey
    end

    context "confirm이 되어있는 경우" do
      it "confirm이 되어있는지 확인할 수 있다" do
        user2.befriend(user)

        user.confirm_friendship(user2)

        expect(user.requested_friendships.first.confirmed?).to be_truthy
      end
    end
  end
end
