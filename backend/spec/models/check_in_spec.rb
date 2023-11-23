require 'rails_helper'

RSpec.describe CheckIn, type: :model do
  describe ".order_by_distance" do
    let!(:user) { User.create!(email: "valid@snu.ac.kr", name: "test", password: "1234") }
    let!(:check_in1) { CheckIn.create!(user: user, latitude: 126.1, longitude: 37.1) }
    let!(:check_in2) { CheckIn.create!(user: user, latitude: 120.1, longitude: 37.1) }

    it "returns the nearest check-in based on latitude and longitude" do
      expect(described_class.order_by_distance(126.0, 37.0).first).to eq(check_in1)
    end
  end

  describe ".last_check_in_by_user" do
    let!(:user1) { User.create!(email: "user1@snu.ac.kr", name: "user1", password: "1234") }
    let!(:user2) { User.create!(email: "user2@snu.ac.kr", name: "user2", password: "1234") }
    let!(:check_in1) { CheckIn.create!(user: user1, latitude: 126.1, longitude: 37.1, created_at: 1.day.ago) }
    let!(:check_in2) { CheckIn.create!(user: user1, latitude: 120.1, longitude: 37.1, created_at: 2.days.ago) }
    let!(:check_in3) { CheckIn.create!(user: user2, latitude: 120.1, longitude: 37.1, created_at: 1.day.ago) }
    let!(:check_in4) { CheckIn.create!(user: user2, latitude: 126.1, longitude: 37.1, created_at: 2.days.ago) }

    it "returns the last check-in for each user" do
      expect(described_class.last_check_in_by_user).to match_array([check_in1, check_in3])
    end
  end
end
