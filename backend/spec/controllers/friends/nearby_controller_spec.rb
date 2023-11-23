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
        expect(parsed_body).to eq []
      end
    end

    context "when there are nearby friends" do
      let!(:friend) { User.create!(email: "friend@snu.ac.kr", name: "friend", password: "1234") }
      let!(:friend_check_in) { CheckIn.create!(user: friend, latitude: 37.449, longitude: 126.953) }
      let!(:friendship) { Friendship.create!(user: user, friend: friend, confirmed: true) }

      it "returns the nearby friends" do
        get "/friends/nearby", headers: auth_header(user)

        expect(response).to have_http_status(:ok)
        expect(parsed_body[0]["latitude"]).to eq(37.449)
        expect(parsed_body[0]["longitude"]).to eq(126.953)
      end
    end

    context "when there are user but they are not friend" do
      let!(:far_friend) { User.create!(email: "far_friend@snu.ac.kr", name: "far_friend", password: "1234") }
      let!(:far_friend_check_in) { CheckIn.create!(user: far_friend, latitude: 38.449, longitude: 127.953) }

      it "returns an empty array" do
        get "/friends/nearby", headers: auth_header(user)

        expect(response).to have_http_status(:ok)
        expect(parsed_body).to eq []
      end
    end
  end
end
