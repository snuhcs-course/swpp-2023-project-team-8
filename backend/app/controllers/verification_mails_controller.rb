# frozen_string_literal: true

class VerificationMailsController < ApplicationController
  skip_before_action :require_login

  def create
    token = MailVerificationToken.new(email: params[:email])
    # TODO: Send Email using ActiveJob

    if token.save
      render json: {message: "Verification email sent"}, status: :created
    else
      render json: {errors: token.errors.full_messages}, status: :unprocessable_entity
    end
  end
end
