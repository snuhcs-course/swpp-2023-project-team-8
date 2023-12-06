class MissionsController < ApplicationController

  def index
    @missions = Mission.left_joins(:user_missions)
                       .select('missions.*, user_missions.completed as user_completed')
                       .where('user_missions.user_id = ? OR user_missions.user_id IS NULL', current_user.id)

    render json: @missions, each_serializer: MissionSerializer
  end

end
