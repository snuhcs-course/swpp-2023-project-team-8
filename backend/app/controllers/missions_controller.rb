class MissionsController < ApplicationController

  def index
    missions = Mission.order(:id)
    @missions = missions.map do |mission|
      user_mission = mission.user_missions.find_by(user_id: current_user.id)
      {
        id: mission.id,
        name: mission.name,
        description: mission.description,
        user_completed: user_mission&.completed,
        user_progress: user_mission&.progress
      }
    end

    render json: @missions
  end

end
