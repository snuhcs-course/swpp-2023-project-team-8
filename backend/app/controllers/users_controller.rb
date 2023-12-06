class UsersController < ApplicationController
  skip_before_action :require_login, only: [:create]
  before_action :set_user, only: [:show]

  def index
    @users = User.where.not(id: current_user.id)
    render json: @users
  end

  def show
    render json: @user
  end

  def create
    unless MailVerificationToken.valid_code?(params[:email_verification_code])
      return render json: { errors: ["Invalid email verification code"] }, status: :unprocessable_entity
    end

    @user = User.new(user_params.merge(password_confirmation: user_params[:password]))

    if @user.save
      render json: @user, status: :created
    else
      render json: { errors: @user.errors.full_messages }, status: :unprocessable_entity
    end
  end

  def destroy
    current_user.destroy!
  end

  def search
    users = User.search_by_email_local_part(params[:email])

    if users.any?
      render json: users
    else
      render json: { error: 'No users found with the provided email keyword' }, status: :not_found
    end
  end

  private

  def set_user
    @user = User.find(params[:id])
  end

  def user_params
    params.permit(:name, :email, :password)
  end
end
