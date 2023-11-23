class CheckIn < ApplicationRecord
  belongs_to :user

  scope :order_by_distance, -> (latitude, longitude) do
    order(Arel.sql("ABS(latitude - #{latitude}) + ABS(longitude - #{longitude})"))
  end
end
