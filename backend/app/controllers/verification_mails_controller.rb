# frozen_string_literal: true

class VerificationMailsController < ApplicationController
  skip_before_action :require_login
  after_action :send_verification_email, only: :create

  def create
    @token = MailVerificationToken.new(email: params[:email])

    if @token.save
      render json: {message: "Verification email sent"}, status: :created
    else
      render json: {errors: token.errors.full_messages}, status: :unprocessable_entity
    end
  end

  private

  # TODO: Send Email Async
  def send_verification_email
    MailVerificationTokenMailer
      .with(mail_verification_token: @token)
      .new_mail
      .deliver_now
  end
end
