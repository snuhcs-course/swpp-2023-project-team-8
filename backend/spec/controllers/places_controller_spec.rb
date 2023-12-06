# frozen_string_literal: true

require "rails_helper"

RSpec.describe PlacesController, type: :request do
  let!(:user) { User.create!(email: "valid@snu.ac.kr", name: "test", password: "1234") }
  let!(:user2) { User.create!(email: "valid2@snu.ac.kr", name: "test2", password: "1234") }
  let!(:user3) { User.create!(email: "valid3@snu.ac.kr", name: "test3", password: "1234") }

  before do
    allow_any_instance_of(Swpp::V1::PlaceService::Stub).to receive(:list_places).and_return(
      Swpp::V1::ListPlacesResponse.new(place_ids: [1, 2, 3])
    )
  end

  describe "GET places/recommend" do
    context "유저 id 목록을 넘겨주면" do
      it "그들의 중점으로 요청한다" do

        user.check_in!(latitude: 126.1, longitude: 37.1)
        user2.check_in!(latitude: 127.1, longitude: 38.1)
        user3.check_in!(latitude: 128.1, longitude: 39.1)

        get "/places/recommend",
          headers: auth_header(user),
          params: {user_ids: [user2.id, user3.id]}

        expect(response).to have_http_status(:ok)
      end
    end
  end
end
