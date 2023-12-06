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
      user_mission1 = UserMission.find_or_create_by(user_id: current_user.id, mission_id: 3)
      user_mission1.update_progress(1)
      user_mission2 = UserMission.find_or_create_by(user_id: @friend.id, mission_id: 3)
      user_mission2.update_progress(1)
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
