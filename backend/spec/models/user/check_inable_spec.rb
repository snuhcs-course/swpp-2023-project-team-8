# frozen_string_literal: true

require "rails_helper"

RSpec.describe User::CheckInable do
  let!(:user) { User.create!(email: "valid@snu.ac.kr", name: "test", password: "1234") }
  described_class = User

  describe "#check_in!" do
    context "위, 경도가 주어지면" do
      it "할 수 있다" do
        user.check_in!(latitude: 126.1, longitude: 37.1)

        expect(user.check_ins.count).to eq(1)
      end
    end
  end
end
