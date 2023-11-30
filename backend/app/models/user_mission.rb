class UserMission < ApplicationRecord
  belongs_to :user
  belongs_to :mission

  def update_progress(amount)
    self.progress += amount
    self.completed = true if progress >= mission.target
    save
  end
end
