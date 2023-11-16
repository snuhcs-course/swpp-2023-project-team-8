# frozen_string_literal: true

require "rails_helper"

RSpec.describe User::Friendable do
  let!(:user) { User.create!(email: "valid@snu.ac.kr", name: "test", password: "1234") }
  let!(:user2) { User.create!(email: "valid2@snu.ac.kr", name: "test2", password: "1234") }
  let!(:user3) { User.create!(email: "valid3@snu.ac.kr", name: "test3", password: "1234") }

  describe "#befriend" do
    context "이미 친구일 경우" do
      it "친구가 될 수 없다" do
        user.befriend(user2)
        expect(Friendship.count).to eq(1)

        user2.befriend(user)
        expect(Friendship.count).to eq(1)
      end
    end

    it "스스로에게 친구 요청을 보낼 수 없다" do
      expect(user.befriend(user)).to be_nil
    end

    context "이미 친구 요청을 보냈으면" do
      it "다시 보낼 수 없다" do
        user.befriend(user2)
        expect(user.befriend(user2)).to be_nil
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

  describe "#unfriend" do
    it "친구 삭제를 할 수 있다" do
      user.befriend(user2)
      user2.confirm_friendship(user)
      expect(user.confirmed_friends).to include(user2)
      expect(user2.inverse_confirmed_friends).to include(user)

      user.unfriend(user2)
      expect(user.confirmed_friends).not_to include(user2)
      expect(user2.inverse_confirmed_friends).not_to include(user)
    end
  end

  describe "#all_friends" do
    it "모든 친구를 확인할 수 있다" do
      user.befriend(user2)
      user2.confirm_friendship(user)

      user3.befriend(user)
      user.confirm_friendship(user3)

      expect(user.all_friends).to include(user2, user3)
    end
  end

  describe "#pending_friendships" do
    context "친구 요청이 왔을때" do
      it "확인할 수 있다" do
        user2.befriend(user)
        expect(user.pending_friendships).to include(user2.friendships.first)
      end
    end
  end
end
