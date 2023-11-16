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
        expect(parsed_body["check_ins"].first["latitude"]).to eq  126.1
        expect(parsed_body["check_ins"].first["longitude"]).to eq 38.8
      end
    end
  end
end
