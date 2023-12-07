class MeetUpsController < ApplicationController
  skip_before_action :verify_authenticity_token

  def index
    @meet_ups = current_user.meet_ups.where('meet_at > ?', Time.now)
                            .order(meet_at: :asc)
    render json: @meet_ups
  end

  def show
    @meet_up = MeetUp.includes(:users, :place).find(params[:id])
    render json: @meet_up
  end

  def create
    @meet_up = MeetUp.new(meet_up_params.except(:user_ids))
    if @meet_up.save
      UserMeetUp.create(meet_up: @meet_up, user_id: current_user.id)

      meet_up_params[:user_ids].each do |user_id|
        UserMeetUp.create(meet_up: @meet_up, user_id: user_id) unless user_id == current_user.id
      end

      user_mission1 = UserMission.find_or_create_by(user_id: current_user.id, mission_id: Mission::CREATE_3_MEET_UPS)
      user_mission1.update_progress(1)

      if meet_up_params[:user_ids].length >= 3
        user_mission2 = UserMission.find_or_create_by(user_id: current_user.id, mission_id: Mission::CREATE_MEET_UP_WITH_3_FRIENDS)
        user_mission2.update_progress(1)
      end

      render json: @meet_up, status: :created
    else
      render json: @meet_up.errors, status: :unprocessable_entity
    end
  end

  private

  def meet_up_params
    params.permit(:title, :description, :meet_at, :is_public, :place_id, user_ids: [])
  end
end
