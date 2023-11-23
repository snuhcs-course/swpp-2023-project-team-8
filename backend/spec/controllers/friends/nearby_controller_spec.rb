# frozen_string_literal: true

require "rails_helper"

RSpec.describe Friends::NearbyController, type: :request do
  let!(:user) { User.create!(email: "valid@snu.ac.kr", name: "test", password: "1234") }
  let!(:check_in) { CheckIn.create!(user: user, latitude: 37.448, longitude: 126.952) }

  describe "GET /friends/nearby" do
    context "친구가 없을 경우" do
      it "빈 배열을 반환한다" do
        get "/friends/nearby", headers: auth_header(user)

        expect(response).to have_http_status(:ok)
        expect(parsed_body["friends"]).to eq []
      end
    end
  end
end
