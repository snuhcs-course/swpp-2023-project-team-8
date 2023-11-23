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
end
