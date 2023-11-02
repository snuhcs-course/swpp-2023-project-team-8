# frozen_string_literal: true

class Users::PendingRequestsController < ApplicationController
  before_action :set_user

  def index
    @pending_requests = current_user.pending_friend_requests
    render json: @pending_requests
  end

  private

  def set_user
    if params[:user_id] != "me" || params[:user_id] != current_user.id
      raise ActiveRecord::RecordNotFound
    end

    @user = current_user
  end
end
