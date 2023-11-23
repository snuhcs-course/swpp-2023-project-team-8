require 'rails_helper'

RSpec.describe User, type: :model do
  describe "#email" do
    context "제대로 된 이메일 포맷이 아니면" do
      it "유효하지 않다" do
        user = User.new(email: "invalid_email_format", password: "1234", name: "test")
        expect(user.valid?).to be_falsey
      end
    end

    context "제대로 된 이메일 포맷일 경우" do
      context "스누메일이 아닐 경우" do
        it "유효하지 않다" do
          user = User.new(email: "test@gmail.com", password: "1234", name: "test")

          expect(user.valid?).to be_falsey
        end
      end

      context "스누메일일 경우" do
        it "유효하다" do
          user = User.new(email: "test@snu.ac.kr", password: "1234", name: "test")

          expect(user.valid?).to be_truthy
        end
      end
    end
  end

  describe "#password" do
    let(:password) { "1234" }

    context "평문으로 Insert 시도하면" do
      it "암호화된 비밀번호가 저장된다" do
        user = User.new(email: "valid@mail.com", password: password, name: "test")

        expect(user.password_digest).not_to eq(password)
      end
    end
  end

  describe "#nearby_friends" do
    let!(:user1) { User.create!(email: "user1@snu.ac.kr", name: "user1", password: "1234") }
    let!(:user2) { User.create!(email: "user2@snu.ac.kr", name: "user2", password: "1234") }
    let!(:user3) { User.create!(email: "user3@snu.ac.kr", name: "user3", password: "1234") }
    let!(:user4) { User.create!(email: "user4@snu.ac.kr", name: "user4", password: "1234") }
    let!(:check_in1) { CheckIn.create!(user: user1, latitude: 126.1, longitude: 37.1) }
    let!(:check_in2) { CheckIn.create!(user: user2, latitude: 120.1, longitude: 37.1) }
    let!(:check_in3) { CheckIn.create!(user: user3, latitude: 130.1, longitude: 40.1) }
    let!(:check_in4) { CheckIn.create!(user: user3, latitude: 126.1, longitude: 37.1) }
    let!(:friendship1) { Friendship.create!(user: user1, friend: user2, confirmed: true) }
    let!(:friendship2) { Friendship.create!(user: user1, friend: user3, confirmed: true) }

    it "returns nearby friends of a user" do
      expect(user1.nearby_friends).to include(user2)
    end

    it "does not return users that are not friend" do
      expect(user1.nearby_friends).not_to include(user4)
    end
  end
end
