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
