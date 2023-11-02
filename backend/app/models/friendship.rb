class Friendship < ApplicationRecord
  belongs_to :user
  belongs_to :friend, class_name: 'User'

  validates :user_id, uniqueness: { scope: :friend_id }

  scope :confirmed , -> { where(confirmed: true) }
  scope :pending, -> { where(confirmed: false) }

  def confirm
    update(confirmed: true)
  end
end
