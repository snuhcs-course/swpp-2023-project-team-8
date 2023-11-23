# frozen_string_literal: true

require "rails_helper"

RSpec.describe CheckInsController, type: :request do
  let!(:user) { User.create!(email: "valid@snu.ac.kr", name: "test", password: "1234") }

  describe "GET /check_ins" do
    context "체크인이 없을 경우" do
      it "빈 배열을 반환한다" do
        get "/check_ins", headers: auth_header(user)

        expect(parsed_body["check_ins"]).to eq []
      end
    end

    context "여러 개가 있을 경우" do
      before do
        user.check_in!(latitude: 126.1, longitude: 38.8)
        user.check_in!(latitude: 126.2, longitude: 38.2)
      end

      it "잘 반환한다" do
        get "/check_ins", headers: auth_header(user)

        expect(parsed_body["check_ins"].size).to eq 2
        expect(parsed_body["check_ins"].first["latitude"]).to eq 126.1
        expect(parsed_body["check_ins"].first["longitude"]).to eq 38.8
      end
    end
  end

  describe "POST /check_ins" do
    context "성공할 경우" do
      it "체크인을 생성한다" do
        post "/check_ins",
          params: {check_in: {latitude: 126.1, longitude: 38.8}},
          headers: auth_header(user)

        expect(response).to have_http_status(:created)
        expect(parsed_body["check_in"]["latitude"]).to eq 126.1
        expect(parsed_body["check_in"]["longitude"]).to eq 38.8
        expect(user.check_ins.size).to eq 1
      end
    end

    context "실패할 경우" do
      it "latitude가 없으면 실패한다" do
        post "/check_ins", params: {check_in: {longitude: 38.8}}, headers: auth_header(user)

        expect(response).to have_http_status(:unprocessable_entity)
        expect(user.check_ins.size).to eq 0
      end

      it "longitude가 없으면 실패한다" do
        post "/check_ins", params: {check_in: {latitude: 126.1}}, headers: auth_header(user)

        expect(response).to have_http_status(:unprocessable_entity)
        expect(user.check_ins.size).to eq 0
      end
    end
  end
end
