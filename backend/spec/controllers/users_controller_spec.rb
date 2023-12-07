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

  describe "PUT /users/:id/change_image_id" do
    let(:user) { User.create!(name: "test", email: mail, password: "1234") }
    let(:new_image_id) { 1 }

    context "when the request is valid" do
      it "updates the user's image id" do
        put "/users/#{user.id}/change_image_id",
          params: {image_id: new_image_id},
          headers: auth_header(user)

        expect(response).to have_http_status(:ok)
        expect(user.reload.image_id).to eq(new_image_id)
      end
    end

    context "when the request is invalid" do
      it "returns 404 when user is not found" do
        put "/users/non_existent_id/change_image_id",
          params: {image_id: new_image_id},
          headers: auth_header(user)

        expect(response).to have_http_status(:not_found)
      end
    end
  end
end
