# frozen_string_literal: true

require "rails_helper"

RSpec.describe VerificationMailsController do
  let(:mail) { "heka1024@snu.ac.kr" }

  describe "POST create" do
    context "with valid params" do
      it "sends verification email" do
        post :create, params: {email: mail}

        expect(response).to have_http_status(:created)
        expect(MailVerificationToken.last.email).to eq(mail)
      end
    end

    context "with invalid snumail" do
      it "does not send verification email" do
        post :create, params: {email: "heka1024@baddomain.com"}

        expect(response).to have_http_status(:unprocessable_entity)
        expect(MailVerificationToken.last).to be_nil
      end
    end
  end
end