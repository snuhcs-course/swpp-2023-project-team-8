# frozen_string_literal: true

require "rails_helper"

RSpec.describe UsersController, type: :request do
  let(:mail) { "heka1024@snu.ac.kr" }

  describe "POST /users" do
    context "when the request is valid" do
      it "creates a user" do
        token = MailVerificationToken.create!(
          email: mail,
        )

        post "/users", params: {
          name: "test",
          email: mail,
          email_verification_code: token.code,
          password: "1234",
        }

        expect(response).to have_http_status(:created)
        expect(User.first.name).to eq("test")
      end
    end

    context "when request with invalid email verification code" do
      it "returns 422" do
        post "/users", params: {
          name: "test",
          email: mail,
          email_verification_code: "1234",
          password: "1234",
        }

        expect(response).to have_http_status(:unprocessable_entity)
        expect(User.count).to eq(0)
      end
    end


    context "when request with too old code" do
      it "returns 422" do
        token = MailVerificationToken.create!(
          email: mail,
          created_at: 10.minutes.ago,
        )

        post "/users", params: {
          name: "test",
          email: mail,
          email_verification_code: token.code,
          password: "1234",
        }

        expect(response).to have_http_status(:unprocessable_entity)
        expect(User.count).to eq(0)
      end
    end
  end
end
