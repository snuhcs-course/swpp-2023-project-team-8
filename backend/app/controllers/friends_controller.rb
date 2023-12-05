class FriendsController < ApplicationController
  before_action :set_friend, only: [:create, :confirm, :destroy]

  def index
    render json: current_user.all_friends
  end

  def create
    if current_user.befriend(@friend)
      render json: { message: 'Friend request sent.' }, status: :created
    else
      render json: { errors: current_user.errors.full_messages }, status: :unprocessable_entity
    end
  end

  def confirm
    if current_user.confirm_friendship(@friend)
      render json: { message: 'Friend request confirmed.' }
    else
      render json: { errors: current_user.errors.full_messages }, status: :unprocessable_entity
    end
  end

  def destroy
    current_user.unfriend(@friend)
    render json: { message: 'Friendship removed.' }
  end

  private

  def set_friend
    @friend = User.find(params[:id])
  end
end
