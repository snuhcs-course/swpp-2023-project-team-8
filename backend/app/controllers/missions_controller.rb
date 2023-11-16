class MissionsController < ApplicationController

  def index
    @missions = Mission.all
    missions_with_completion = @missions.map do |mission|
      {
        id: mission.id,
        name: mission.name,
        description: mission.description,
        completed: mission.completed_by?(current_user)
      }
    end

    render json: missions_with_completion
  end
end
