# frozen_string_literal: true

class AccessController < ApplicationController

  def create
    user_mission = UserMission.find_or_create_by(user_id: current_user.id, mission_id: 4)
    user_mission.update_progress(3)
  end
end