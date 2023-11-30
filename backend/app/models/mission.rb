class Mission < ApplicationRecord
  has_many :user_missions
  has_many :users, through: :user_missions

  def completed_by?(user)
    user_mission = user_missions.find_by(user: user)
    user_mission&.completed
  end
end
