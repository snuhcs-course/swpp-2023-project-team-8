class Mission < ApplicationRecord
  has_many :user_missions
  has_many :users, through: :user_missions

  def completed_by?(user)
    user_mission = user_missions.find_by(user: user)
    user_mission&.completed
  end

  CREATE_3_MEET_UPS = 1
  CREATE_MEET_UP_WITH_3_FRIENDS = 2
  ADD_3_FRIENDS = 3
  ACCESS_APP_3_TIMES = 4
end
