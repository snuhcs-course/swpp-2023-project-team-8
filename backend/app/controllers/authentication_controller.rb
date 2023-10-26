class AuthenticationController < ApplicationController
  skip_before_action :require_login, only: [:create]

  def create
    user = User.find_by_email(auth_params[:email])

    if user&.authenticate(auth_params[:password])
      render json: { token: user.auth_token }, status: :ok
    else
      render json: { error: 'unauthorized' }, status: :unauthorized
    end
  end

  private

  def auth_params
    params.permit(:email, :password)
  end
end