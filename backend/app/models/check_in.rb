class CheckIn < ApplicationRecord
  belongs_to :user

  scope :order_by_distance, -> (latitude, longitude) {
    order(Arel.sql("ABS(latitude - #{latitude}) + ABS(longitude - #{longitude})"))
  }

  scope :last_check_in_by_user, -> { select('DISTINCT ON (user_id) *').order('user_id, created_at DESC') }
end
