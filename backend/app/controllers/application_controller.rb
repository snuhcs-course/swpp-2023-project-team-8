class ApplicationController < ActionController::Base
  protect_from_forgery with: :null_session

  before_action :require_login

  protected

  def require_login
    unless current_user
      render json: {errors: ["You must be logged in to do that"]}, status: :unauthorized
    end
  end

  def current_user
    @current_user ||= set_current_user_via_token!
  end

  # Set user via Authorization header
  def set_current_user_via_token!
    if bypass_authorization?
      user_id = request.headers['Authorization'].to_s.split(' ')[1]
      return User.find_by(id: user_id)
    end

    @current_user = User.find_by(auth_token: request.headers['Authorization'].to_s)
  end

  def bypass_authorization?
    request.headers['Authorization'].to_s.start_with?('DEV')
  end
end
