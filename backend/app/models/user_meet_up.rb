class UserMeetUp < ApplicationRecord
  belongs_to :user
  belongs_to :meet_up
end
