class UsersController < ApplicationController
  skip_before_action :require_login, only: [:create]
  before_action :set_user, only: [:show]

  def show
    render json: @user
  end

  def create
    unless MailVerificationToken.valid_code?(params[:email_verification_code])
      return render json: {errors: ["Invalid email verification code"]}, status: :unprocessable_entity
    end

    @user = User.new(user_params.merge(password_confirmation: user_params[:password]))

    if @user.save
      render json: @user, status: :created
    else
      render json: {errors: @user.errors.full_messages}, status: :unprocessable_entity
    end
  end

  def destroy
    current_user.destroy!
  end

  private

  def set_user
    @user = User.find(params[:id])
  end

  def user_params
    params.permit(:name, :email, :password)
  end
end
